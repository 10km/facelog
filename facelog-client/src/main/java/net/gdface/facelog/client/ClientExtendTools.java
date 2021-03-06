package net.gdface.facelog.client;

import static com.google.common.base.Preconditions.*;

import java.io.Closeable;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import gu.dtalk.MenuItem;
import gu.dtalk.client.CmdManager;
import gu.dtalk.client.TaskManager;
import gu.dtalk.engine.BaseDispatcher;
import gu.dtalk.engine.CmdDispatcher;
import gu.dtalk.engine.TaskDispatcher;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisSubscriber;
import net.gdface.facelog.IFaceLog;
import net.gdface.facelog.MQParam;
import net.gdface.facelog.ServiceHeartbeatPackage;
import net.gdface.facelog.ServiceSecurityException;
import net.gdface.facelog.Token;
import net.gdface.facelog.Token.TokenType;
import net.gdface.facelog.client.dtalk.DtalkEngineForFacelog;
import net.gdface.facelog.client.dtalk.FacelogRedisConfigProvider;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.hb.BaseServiceHeartbeatListener;
import net.gdface.facelog.hb.DeviceHeartbeatListener;
import net.gdface.facelog.hb.DeviceHeartbeat;
import net.gdface.facelog.hb.HeartbeatMonitor;
import net.gdface.facelog.hb.ServiceHeartbeatAdapter;
import net.gdface.facelog.hb.ServiceHeartbeatListener;
import net.gdface.facelog.thrift.IFaceLogThriftClient;
import net.gdface.facelog.thrift.IFaceLogThriftClientAsync;
import net.gdface.thrift.ClientFactory;
import net.gdface.utils.Delegator;
import net.gdface.utils.NetworkUtil;

/**
 * client端便利性扩展工具类
 * @author guyadong
 *
 */
public class ClientExtendTools{
	private final IFaceLog syncInstance;
	private final IFaceLogThriftClientAsync asyncInstance;
	private final ClientFactory factory;
	private TokenHelper tokenHelper;
	private Map<MQParam, String> redisParameters = null;
	private final ServiceHeartbeatListener tokenRefreshListener = new BaseServiceHeartbeatListener(){
		@Override
		public boolean doServiceOnline(ServiceHeartbeatPackage heartbeatPackage){
			// 执行令牌刷新，更新redis参数
			return tokenRefresh !=null && tokenRefresh.refresh();
		}};

	private final DispatcherListener dispatcherListener = new DispatcherListener();
	private TokenRefresh tokenRefresh;
	private class TokenRefresh implements Supplier<Token>{
		private Token token;
		private final Object lock = new Object();
		private volatile boolean relead = true;
		private final TokenHelper helper;
		private TokenRefresh(TokenHelper helper,Token token) {
			this.helper = checkNotNull(helper,"helper is null");
			this.token = checkNotNull(token,"token is null");
		}
		@Override
		public Token get() {
			// double check
			if(relead){
				synchronized (lock) {
					if(relead){
						Token t = refreshToken(helper, token);
						if(null != t){
							relead = false;
							token.assignFrom(t);
						}
						return t;
					}					
				}				
			}
			return token;
		}
		/**
		 * 刷新令牌和redis参数
		 * @return 刷新成功返回{@code true}，否则返回{@code false}
		 */
		boolean refresh(){
			relead = true;
			Token token = get();
			if(null != token){
				try {
					redisParameters = null;
					getRedisParametersLazy(token);
					return 	true;
				} catch (Exception e) {
					// DO NOTHING
				}
			}
			return false;
		}
	}
	private class DispatcherListener extends BaseServiceHeartbeatListener{
		private final Set<BaseDispatcher> dispatchers = Sets.newLinkedHashSet();
		@Override
		protected boolean doServiceOnline(ServiceHeartbeatPackage heartbeatPackage) {
			synchronized (dispatchers) {
				for(BaseDispatcher dispatcher: dispatchers){
					if(dispatcher.isEnable()){
						dispatcher.unregister();
						dispatcher.register();
					}
				}
			}
			return true;
		}
		boolean addDispatcher(BaseDispatcher dispatcher){
			synchronized (dispatchers) {
				return dispatchers.add(dispatcher);
			}
		}
/*		boolean removeDispatcher(BaseDispatcher dispatcher){
			synchronized (dispatchers) {
				return dispatchers.remove(dispatcher);
			}
		}*/
	}
	public static interface ParameterSupplier<T> extends Supplier<T>,Closeable{
		public Object key();
	}
	private class RedisParameterSupplier implements ParameterSupplier<String>{
		private final MQParam mqParam;
		private final Token token;
		private RedisParameterSupplier(MQParam mqParam,Token token) {
			this.mqParam = checkNotNull(mqParam,"mqParam is null");
			this.token = checkNotNull(token,"token is null");
		}
		@Override
		public String get() {
			return getRedisParametersLazy(token).get(mqParam);
		}
		@Override
		public Object key(){
			return mqParam;
		}
		@Override
		public void close() {
		}
	}
	private class TaskQueueSupplier extends BaseServiceHeartbeatListener implements ParameterSupplier<String>{
		private final String task;
		private final Token token;
		protected String taskQueue = null;
		private TaskQueueSupplier(String task,Token token) {
			this.task = checkNotNull(task,"task is null");
			this.token = checkNotNull(token,"token is null");
			addServiceEventListener(this);
		}
		@Override
		public String get() {
			if(taskQueue == null){
				doServiceOnline(null);
			}
			return taskQueue;
		}
		@Override
		public Object key(){
			return task;
		}

		@Override
		protected boolean doServiceOnline(ServiceHeartbeatPackage heartbeatPackage) {
			taskQueue = taskQueueOf(task,token);
			return true;
		}
		/**
		 * 当对象不再被需要时，执行此方法将其从服务心跳侦听器列表中删除
		 * @see java.io.Closeable#close()
		 */
		@Override
		public void close() {
			removeServiceEventListener(this);			
		}
	}
	private static <T>T unwrap(Object value,Class<T> clazz){
		if(Proxy.isProxyClass(value.getClass())){
			return unwrap(Proxy.getInvocationHandler(value),clazz);
		}else if(value instanceof Delegator){
			return unwrap(((Delegator<?>)value).delegate(),clazz);
		}
		return clazz.cast(value);
	}
	ClientExtendTools(IFaceLog syncInstance) {
		super();
		this.syncInstance = checkNotNull(syncInstance,"syncInstance is null");
		this.asyncInstance = null;
		this.factory = unwrap(syncInstance,IFaceLogThriftClient.class).getFactory();
	}
	ClientExtendTools(IFaceLogThriftClientAsync asyncInstance) {
		super();
		this.syncInstance = null;
		this.asyncInstance = checkNotNull(asyncInstance,"asyncInstance is null");
		this.factory = asyncInstance.getFactory();
	}
	/**
	 * 如果{@code host}是本机地址则用facelog服务主机名替换
	 * @param host
	 * @return {@code host} or host in {@link #factory}
	 */
	public String insteadHostIfLocalhost(String host){
		if(NetworkUtil.isLoopbackAddress(host)){
			return factory.getHostAndPort().getHost();
		}
		return host;
	}
	/**
	 * 如果{@code uri}的主机名是本机地址则用facelog服务主机名替换
	 * @param uri
	 * @return {@code uri} or new URI instead with host of facelog
	 */
	public URI insteadHostIfLocalhost(URI uri){
		if(null != uri && NetworkUtil.isLoopbackAddress(uri.getHost())){
			try {
				return new URI(uri.getScheme(),
				           uri.getUserInfo(), 
				           factory.getHostAndPort().getHost(), 
				           uri.getPort(),
				           uri.getPath(), 
				           uri.getQuery(), 
				           uri.getFragment());
			} catch (URISyntaxException e) {
				// DO NOTHING
			}
		}
		return uri;
	}
	/**
	 * 如果{@code url}的主机名是本机地址则用facelog服务主机名替换
	 * @param url
	 * @return {@code url} or new URI instead with host of facelog
	 */
	public URL insteadHostIfLocalhost(URL url){
		if(null != url && NetworkUtil.isLoopbackAddress(url.getHost())){
			try {
				return new URL(url.getProtocol(),
				           factory.getHostAndPort().getHost(), 
				           url.getPort(),
				           url.getFile());
			} catch (MalformedURLException e) {
				// DO NOTHING
			}
		}
		return url;
	}
	/**
	 *  如果{@code uri}的主机名是本机地址则用facelog服务主机名替换
	 * @param uri
	 * @return {@code uri} or new URI string instead with host of facelog
	 */
	public String insteadHostOfURIIfLocalhost(String uri){
		try {
			return Strings.isNullOrEmpty(uri) ? uri : insteadHostIfLocalhost(new URI(uri)).toString();
		} catch (URISyntaxException e) {
			// DO NOTHING
		}
		return uri;
	}
	/**
	 *  如果{@code url}的主机名是本机地址则用facelog服务主机名替换
	 * @param url 
	 * @return {@code url} or new URL string instead with host of facelog
	 */
	public String insteadHostOfURLIfLocalhost(String url){
		try {
			return Strings.isNullOrEmpty(url) ? url : insteadHostIfLocalhost(new URL(url)).toString();
		} catch (MalformedURLException e) {
			// DO NOTHING
		}
		return url;
	}
	/**
	 * 如果{@code parameters}的参数({@link MQParam#REDIS_URI},{@link MQParam#WEBREDIS_URL})是本机地址则用facelog服务主机名替换
	 * @param parameters
	 * @return 替换主机名参数后的新对象 {@link java.util.HashMap}
	 * @see #insteadHostOfURIIfLocalhost(String)
	 * @see #insteadHostOfURLIfLocalhost(String)
	 */
	public Map<MQParam, String> insteadHostOfMQParamIfLocalhost(Map<MQParam, String> parameters) {
		if(parameters != null){
			Map<MQParam, String> out = Maps.newHashMap();
			for (Entry<MQParam, String> entry : parameters.entrySet()) {
				MQParam key = entry.getKey();
				String value = entry.getValue();
				if(MQParam.REDIS_URI.equals(key)){
					String insteaded = insteadHostOfURIIfLocalhost(value);
					out.put(key, insteaded);
				}else	if(MQParam.WEBREDIS_URL.equals(key)){
					String insteaded = insteadHostOfURLIfLocalhost(value);
					out.put(key, insteaded);
				}else{
					out.put(key,value);
				}
			}
			return out;
		}
		return parameters;
	}
    /**
     * 根据设备ID返回设备所属的设备组ID的{@code Function}实例,
     * 设备ID无效则返回{@code null}
     */
    public final Function<Integer,Integer> deviceGroupIdGetter = 
        new Function<Integer,Integer>(){
        @Override
        public Integer apply(Integer input) {
            try{
                DeviceBean device = syncInstance != null 
                		? syncInstance.getDevice(input) 
                		: asyncInstance.getDevice(input).get();
                return null == device ? null : device.getGroupId();
            } catch (ExecutionException e) {
    	        Throwables.throwIfUnchecked(e.getCause());
    	        throw new RuntimeException(e.getCause());
    		} catch(Exception e){
                Throwables.throwIfUnchecked(e);
                throw new RuntimeException(e);
            }
        }};
    /**
     * 根据设备ID返回一个获取设备组ID的{@code Supplier}实例
     * @param deviceId
     * @return 对应的groupId,如果{@code deviceId}无效则返回{@code null}
     * @see #deviceGroupIdGetter
     */
    public Supplier<Integer> getDeviceGroupIdSupplier(final int deviceId){
        return new Supplier<Integer>(){
            @Override
            public Integer get() {
                return deviceGroupIdGetter.apply(deviceId);
            }        
        };
    }
    /**
     * 根据人员ID返回人员所属的所有组ID的{@code Function}实例
     * 如果人员ID无效则返回空表
     */
    public final Function<Integer,List<Integer>> personGroupBelonsGetter = 
        new Function<Integer,List<Integer>>(){
        @Override
        public List<Integer> apply(Integer personId) {
            try{
                return syncInstance != null 
                		? syncInstance.getPersonGroupsBelongs(personId) 
                		: asyncInstance.getPersonGroupsBelongs(personId).get();
            } catch (ExecutionException e) {
    	        Throwables.throwIfUnchecked(e.getCause());
    	        throw new RuntimeException(e.getCause());
    		} catch(Exception e){
                Throwables.throwIfUnchecked(e);
                throw new RuntimeException(e);
            }
        }};
    /**
     * 根据人员ID返回一个获取所属组ID列表的{@code Supplier}实例
     * @param personId
     * @return 人员组ID列表,如果{@code personId}无效则返回空表
     * @see #personGroupBelonsGetter
     */
    public Supplier<List<Integer>> getPersonGroupBelonsSupplier(final int personId){
        return new Supplier<List<Integer>>(){
            @Override
            public List<Integer> get() {
                return personGroupBelonsGetter.apply(personId);
            }        
        };
    }
    /**
     * (管理端)创建{@link CmdManager}实例<br>
     * @param token 访问令牌(person Token or root Token)
     * @return
     */
    public CmdManager makeCmdManager(Token token){
        try{
            checkArgument(checkNotNull(token,"token is null").getType() == TokenType.PERSON 
                || token.getType() == TokenType.ROOT,"person or root token required");
            checkArgument(tokenRank.apply(token)>=2,"person or root token required");
            Map<MQParam, String> redisParameters = getRedisParametersLazy(token);
            return new CmdManager(
            		JedisPoolLazy.getInstanceByURI(redisParameters.get(MQParam.REDIS_URI)),
            		getCmdChannelSupplier(token))
            		/** 设置命令序列号 */
            		.setCmdSn(getCmdSnSupplier(token))
            		/** 设置命令响应通道 */
            		.setAckChannel(getAckChannelSupplier(token))
            		.self();
        } catch(Exception e){
            Throwables.throwIfUnchecked(e);
            throw new RuntimeException(e);
        }
    }
    /**
     * (管理端)创建{@link TaskManager}实例<br>
     * @param token 访问令牌(person Token or root Token)
     * @param cmdpath 设备(菜单)命令路径
     * @param taskQueueSupplier 任务队列
     * @return
     */
    public TaskManager makeTaskManager(Token token, String cmdpath, Supplier<String> taskQueueSupplier){
        try{
            checkArgument(checkNotNull(token,"token is null").getType() == TokenType.PERSON 
                || token.getType() == TokenType.ROOT,"person or root token required");
            checkArgument(tokenRank.apply(token)>=2,"person or root token required");
            Map<MQParam, String> redisParameters = getRedisParametersLazy(token);
            return new TaskManager(
            		JedisPoolLazy.getInstanceByURI(redisParameters.get(MQParam.REDIS_URI)),
            		cmdpath, taskQueueSupplier)
            		/** 设置命令序列号 */
            		.setCmdSn(getCmdSnSupplier(token))
            		/** 设置命令响应通道 */
            		.setAckChannel(getAckChannelSupplier(token))
            		.self();
        } catch(Exception e){
            Throwables.throwIfUnchecked(e);
            throw new RuntimeException(e);
        }
    }
    /**
     * (设备端)创建设备多目标命令分发器<br>
     * @param token 设备令牌
     * @return
     */
    public CmdDispatcher makeCmdDispatcher(Token token){
    	try{
    		checkArgument(checkNotNull(token,"token is null").getType() == TokenType.DEVICE,"device token required");
    		int deviceId = token.getId();
    		Map<MQParam, String> redisParameters = getRedisParametersLazy(token);    		
    		CmdDispatcher dispatcher = new CmdDispatcher(deviceId, 
					JedisPoolLazy.getInstanceByURI(redisParameters.get(MQParam.REDIS_URI)))
					.setGroupIdSupplier(this.getDeviceGroupIdSupplier(deviceId))
					.setCmdSnValidator(cmdSnValidator)
					.setChannelSupplier(new RedisParameterSupplier(MQParam.CMD_CHANNEL,token))
					.register()
					.self();
    		dispatcherListener.addDispatcher(dispatcher);
			return dispatcher;
    	} catch(Exception e){
    		Throwables.throwIfUnchecked(e);
    		throw new RuntimeException(e);
    	}
    }
    /**
     * (设备端)创建设备任务分发器<br>
     * @param token 设备令牌
     * @param taskQueueSupplier 任务队列名,创建的分发器对象注册到任务队列，可为{@code null}
     * @return
     */
    public TaskDispatcher makeTaskDispatcher(Token token,Supplier<String> taskQueueSupplier){
    	try{
    		checkArgument(checkNotNull(token,"token is null").getType() == TokenType.DEVICE,"device token required");
    		int deviceId = token.getId();
    		Map<MQParam, String> redisParameters = getRedisParametersLazy(token);
    		TaskDispatcher dispatcher = new TaskDispatcher(deviceId, 
    				JedisPoolLazy.getInstanceByURI(redisParameters.get(MQParam.REDIS_URI)))
    				.setCmdSnValidator(cmdSnValidator)
					.setChannelSupplier(taskQueueSupplier)
					.register()
					.self();
    		dispatcherListener.addDispatcher(dispatcher);
    		return dispatcher;
    	} catch(Exception e){
    		Throwables.throwIfUnchecked(e);
    		throw new RuntimeException(e);
    	}
    }
    /**
     * 返回一个申请命令响应通道的{@link Supplier}实例
     * @param duration 命令通道有效时间(秒) 大于0有效,否则使用默认的有效期
     * @param token 访问令牌
     * @return
     */
    public Supplier<String> 
    getAckChannelSupplier(final int duration,final Token token){
        return new Supplier<String>(){
            @Override
            public String get() {
                try{
                    return syncInstance != null 
                    		? syncInstance.applyAckChannel(duration,token) 
                    		: asyncInstance.applyAckChannel(duration,token).get();
                } catch (ExecutionException e) {
        	        Throwables.throwIfUnchecked(e.getCause());
        	        throw new RuntimeException(e.getCause());
        		} catch(Exception e){
                    Throwables.throwIfUnchecked(e);
                    throw new RuntimeException(e);
                }
            }
        }; 
    }
    /**
     * 返回一个申请命令响应通道的{@link Supplier}实例
     * @param token 访问令牌
     * @return
     */
    public Supplier<String> 
    getAckChannelSupplier(final Token token){
        return getAckChannelSupplier(0,token);
    }
    /**
     * 返回一个申请命令序号的{@code Supplier}实例
     * @param token
     * @return 访问令牌
     */
    public Supplier<Integer> 
    getCmdSnSupplier(final Token token){
        return new Supplier<Integer>(){
            @Override
            public Integer get() {
                try{
                    return syncInstance != null 
                    		? syncInstance.applyCmdSn(token) 
                    		: asyncInstance.applyCmdSn(token).get();
                } catch (ExecutionException e) {
        	        Throwables.throwIfUnchecked(e.getCause());
        	        throw new RuntimeException(e.getCause());
        		} catch(Exception e){
                    Throwables.throwIfUnchecked(e);
                    throw new RuntimeException(e);
                }
            }
        }; 
    }
    /**
     * 返回一个获取设备命令通道名的{@code Supplier}实例
     * @param token
     * @return 访问令牌
     */
    public Supplier<String> 
    getCmdChannelSupplier(final Token token){
        return new Supplier<String>(){
            @Override
            public String get() {
                try{
                    return getRedisParametersLazy(token).get(MQParam.CMD_CHANNEL);
                } catch(Exception e){
                    Throwables.throwIfUnchecked(e);
                    throw new RuntimeException(e);
                }
            }
        }; 
    }
    /** 设备命令序列号验证器 */
    public final Predicate<Integer> cmdSnValidator = 
        new Predicate<Integer>(){
            @Override
            public boolean apply(Integer input) {
                try{
                    return null == input ? false 
                    		: (syncInstance != null 
                    				? syncInstance.isValidCmdSn(input) 
                    				: asyncInstance.isValidCmdSn(input).get());
                } catch (ExecutionException e) {
        	        Throwables.throwIfUnchecked(e.getCause());
        	        throw new RuntimeException(e.getCause());
        		} catch(Exception e){
                    Throwables.throwIfUnchecked(e);
                    throw new RuntimeException(e);
                }
            }};
    /** 设备命令响应通道验证器 */
    public final Predicate<String> ackChannelValidator =
        new Predicate<String>(){
            @Override
            public boolean apply(String input) {
                try{
                    return null == input || input.isEmpty() ? false 
                    		: (syncInstance != null 
                    				? syncInstance.isValidAckChannel(input) 
                    				: asyncInstance.isValidAckChannel(input).get());
                } catch (ExecutionException e) {
        	        Throwables.throwIfUnchecked(e.getCause());
        	        throw new RuntimeException(e.getCause());
        		} catch(Exception e){
                    Throwables.throwIfUnchecked(e);
                    throw new RuntimeException(e);
                }
            }};
    /** 设备令牌验证器 */
    public final Predicate<Token> deviceTokenValidator =
        new Predicate<Token>(){
            @Override
            public boolean apply(Token input) {
                try{
                    return null == input ? false 
                    		: (syncInstance != null 
                    				? syncInstance.isValidDeviceToken(input) 
                    				: asyncInstance.isValidDeviceToken(input).get());
                } catch (ExecutionException e) {
        	        Throwables.throwIfUnchecked(e.getCause());
        	        throw new RuntimeException(e.getCause());
        		} catch(Exception e){
                    Throwables.throwIfUnchecked(e);
                    throw new RuntimeException(e);
                }
            }};
    /** 人员令牌验证器 */
	public final Predicate<Token> personTokenValidator =
	        new Predicate<Token>(){
	            @Override
	            public boolean apply(Token input) {
	                try{
	                    return null == input ? false 
	                    		: (syncInstance != null 
	                    				? syncInstance.isValidPersonToken(input) 
	                    				: asyncInstance.isValidPersonToken(input).get());
	                } catch (ExecutionException e) {
	        	        Throwables.throwIfUnchecked(e.getCause());
	        	        throw new RuntimeException(e.getCause());
	        		} catch(Exception e){
	                    Throwables.throwIfUnchecked(e);
	                    throw new RuntimeException(e);
	                }
	            }};
    /** root令牌验证器 */
	public final Predicate<Token> rootTokenValidator =
	        new Predicate<Token>(){
	            @Override
	            public boolean apply(Token input) {
	                try{
	                    return null == input ? false 
	                    		: (syncInstance != null 
	                    				? syncInstance.isValidRootToken(input) 
	                    				: asyncInstance.isValidRootToken(input).get());
	                } catch (ExecutionException e) {
	        	        Throwables.throwIfUnchecked(e.getCause());
	        	        throw new RuntimeException(e.getCause());
	        		} catch(Exception e){
	                    Throwables.throwIfUnchecked(e);
	                    throw new RuntimeException(e);
	                }
	            }};
	/** 
	 * 返回令牌的管理等级,输入为{@code null}或设备令牌返回-1
	 * <li> 4---root</li>
	 * <li> 3---管理员</li>
	 * <li> 2---操作员</li>
	 * <li> 0---普通用户</li>
	 * <li>-1---未定义</li>
	 */
	public final Function<Token,Integer> tokenRank =
	        new Function<Token,Integer>(){
	            @Override
	            public Integer apply(Token input) {
	                try{
	                	if(null != input){
	                		switch (input.getType()) {
	                		case ROOT:
	                			if(rootTokenValidator.apply(input)){
	                				return 4;
	                			}
	                			break;
	                		case PERSON:{
	                			if(personTokenValidator.apply(input)){ 
	                				PersonBean bean = (syncInstance != null 
	                						? syncInstance.getPerson(input.getId()) 
	                								: asyncInstance.getPerson(input.getId()).get());
	                				Integer rank = bean.getRank();
	                				return rank != null ? rank : 0;
	                			}
	                			break;
	                		}	                		
	                		default:
	                			break;
	                		}
	                	}
						return -1;
	                } catch (ExecutionException e) {
	        	        Throwables.throwIfUnchecked(e.getCause());
	        	        throw new RuntimeException(e.getCause());
	        		} catch(Exception e){
	                    Throwables.throwIfUnchecked(e);
	                    throw new RuntimeException(e);
	                }
	            }};
    /**
     * 申请用户令牌
     * @param userid 用户id，为-1代表root
     * @param password 用户密码
     * @param isMd5 密码是否为md5校验码
     * @return
     * @throws ServiceSecurityException
     */
    private Token applyUserToken(int userid,String password,boolean isMd5) throws ServiceSecurityException{
    	Token token;
    	try {
			token = syncInstance != null 
					? syncInstance.applyUserToken(userid, password, isMd5)
					: asyncInstance.applyUserToken(userid, password, isMd5).get();
    		return token;
    	} catch (ExecutionException e) {
    		Throwables.propagateIfPossible(e.getCause(), ServiceSecurityException.class);
	        throw new RuntimeException(e.getCause());
		} catch(Exception e){
    		Throwables.propagateIfPossible(e, ServiceSecurityException.class);
            throw new RuntimeException(e);
        }
    }
	/**
	 *  获取redis连接参数
	 * @param token
	 * @return
	 */
	private Map<MQParam, String> getRedisParametersLazy(Token token){
		if(redisParameters == null){
			checkArgument(token != null,"token is null");
			// 获取redis连接参数
			try {
				Map<MQParam, String> param = syncInstance != null 
						? syncInstance.getRedisParameters(token)
						: asyncInstance.getRedisParameters(token).get();
				redisParameters =  insteadHostOfMQParamIfLocalhost(param);
			} catch (ExecutionException e) {
				Throwables.throwIfUnchecked(e.getCause());
				throw new RuntimeException(e.getCause());
			} catch(Exception e){
				Throwables.throwIfUnchecked(e);
				throw new RuntimeException(e);
			}
		}
		return redisParameters;
	}
	/**
	 * @param token 调用 {@link #getRedisParametersLazy(Token)}所需要的令牌
	 * @return 返回一个获取redis参数的{@link Supplier}实例
	 */
	public Supplier<Map<MQParam, String>> getRedisParametersSupplier(final Token token){
		checkArgument(token != null,"token is null");
		return new Supplier<Map<MQParam, String>>(){

			@Override
			public Map<MQParam, String> get() {
				return getRedisParametersLazy(token);
			}};
	}
	/**
	 * @param token 调用 {@link #getRedisParametersLazy(Token)}所需要的令牌
	 * @return 返回一个获取设备心跳实时监控通道名的{@link Supplier}实例
	 */
	public Supplier<String> getMonitorChannelSupplier(Token token){
		return Suppliers.compose(new Function<Map<MQParam, String>,String>(){
			@Override
			public String apply(Map<MQParam, String> input) {
				return input.get(MQParam.HB_MONITOR_CHANNEL);
			}
		}, getRedisParametersSupplier(token));
	}
	/**
	 * 创建dtalk引擎
	 * @param deviceToken 设备令牌，不可为{@code null}
	 * @param rootMenu 包括所有菜单的根菜单对象，不可为{@code null}
	 * @return {@link DtalkEngineForFacelog}实例
	 */
	public DtalkEngineForFacelog initDtalkEngine(Token deviceToken, MenuItem rootMenu){
		// 设备端才能调用此方法
		checkArgument(deviceTokenValidator.apply(deviceToken),"device token REQUIRED");
		initDtalkRedisLocation(deviceToken);
		return new DtalkEngineForFacelog(checkNotNull(rootMenu,"rootMenu is null"), tokenRank);		
	}

	/**
	 * 初始化dtalk的redis的连接参数
	 * @param token
	 * @see FacelogRedisConfigProvider#setRedisLocation(URI)
	 */
	public void initDtalkRedisLocation(Token token){
		Map<MQParam, String> redisParam = getRedisParametersLazy(token);
		URI uri = URI.create(redisParam.get(MQParam.REDIS_URI));
		FacelogRedisConfigProvider.setRedisLocation(uri);
	}
	/**
	 * 从facelog获取redis连接参数，初始化为{@link JedisPoolLazy}的默认实例<br>
	 * 该方法只能在应用启动时调用一次
	 * @param token
	 * @see JedisPoolLazy#getInstance(URI)
	 * @see JedisPoolLazy#asDefaultInstance()
	 */
	public void initRedisDefaultInstance(Token token){
		Map<MQParam, String> redisParam = getRedisParametersLazy(token);
		JedisPoolLazy.getInstanceByURI(redisParam.get(MQParam.REDIS_URI)).asDefaultInstance();
	}
	private Token online(DeviceBean deviceBean) throws ServiceSecurityException{
		try{
			return syncInstance != null 
					? syncInstance.online(deviceBean) 
					: asyncInstance.online(deviceBean).get();
    	} catch (ExecutionException e) {
    		Throwables.propagateIfPossible(e.getCause(), ServiceSecurityException.class);
	        throw new RuntimeException(e.getCause());
		} catch(Exception e){
    		Throwables.propagateIfPossible(e, ServiceSecurityException.class);
            throw new RuntimeException(e);
        }
	}

	/**
	 * 令牌刷新
	 * @param helper
	 * @param token
	 * @return 刷新的令牌,刷新失败返回{@code null}
	 */
	public Token refreshToken(TokenHelper helper,Token token){
		// 最后一个参数为token
		Token freshToken = null;
		try{
			// 重新申请令牌
			switch(token.getType()){
			case DEVICE:{
				if(helper.deviceBean() != null){
					freshToken = online(helper.deviceBean());
				}
				break;
			}
			case ROOT:
			case PERSON:{
				String pwd = helper.passwordOf(token.getId());
				if(pwd != null){
					freshToken = applyUserToken(token.getId(),pwd,helper.isHashedPwd());
				}
				break;
			}
			default:
				break;
			}
			// 如果申请令牌成功则更新令牌参数，重新执行方法调用
			if(freshToken != null){
				// 用申请的新令牌更新参数
				helper.saveFreshedToken(freshToken);
			}
		}catch (Exception er) {
			// DO NOTHING
		}
		return freshToken;
	}

	/**
	 * 返回有效令牌的{@link Supplier}实例<br>
	 * @return {@link Supplier}实例
	 */
	public Supplier<Token> getTokenSupplier(){
		return checkNotNull(this.tokenRefresh,"tokenRefresh field must be initialized by startServiceHeartbeatListener firstly");
	}
	public ClientExtendTools addServiceEventListener(ServiceHeartbeatListener listener){
		ServiceHeartbeatAdapter.INSTANCE.addServiceEventListener(listener);	
		return this;
	}
	public ClientExtendTools removeServiceEventListener(ServiceHeartbeatListener listener){
		ServiceHeartbeatAdapter.INSTANCE.removeServiceEventListener(listener);
		return this;
	}
	/**
	 * 创建设备心跳包侦听对象
	 * @param listener
	 * @param token
	 * @param jedisPoolLazy jedis连接池对象，为{@code null}使用默认实例
	 * @return 返回{@link HeartbeatMonitor}实例
	 */
	public HeartbeatMonitor makeHeartbeatMonitor(DeviceHeartbeatListener listener,Token token, JedisPoolLazy jedisPoolLazy) {
		HeartbeatMonitor monitor = new HeartbeatMonitor(
				listener,
				getMonitorChannelSupplier(token),
				MoreObjects.firstNonNull(jedisPoolLazy,JedisPoolLazy.getDefaultInstance()));
		addServiceEventListener(monitor);
		return monitor;
	}
	/**
	 * 创建设备心跳包发送对象<br>
	 * {@link DeviceHeartbeat}为单实例,该方法只能调用一次
	 * @param deviceID 设备ID
	 * @param token 设备令牌
	 * @param jedisPoolLazy jedis连接池对象，为{@code null}使用默认实例
	 * @return {@link DeviceHeartbeat}实例
	 */
	public DeviceHeartbeat makeHeartbeat(int deviceID,Token token,JedisPoolLazy jedisPoolLazy){
		DeviceHeartbeat heartbeat = DeviceHeartbeat.makeHeartbeat(
				deviceID,
				MoreObjects.firstNonNull(jedisPoolLazy,JedisPoolLazy.getDefaultInstance()))
				/** 将设备心跳包数据发送到指定的设备心跳监控通道名,否则监控端无法收到设备心跳包 */
				.setMonitorChannelSupplier(getMonitorChannelSupplier(token));
		addServiceEventListener(heartbeat);
		return heartbeat;
	}
	/**
	 * @param tokenHelper 要设置的 tokenHelper
	 * @return 当前{@link ClientExtendTools}实例
	 */
	public ClientExtendTools setTokenHelper(TokenHelper tokenHelper) {
		this.tokenHelper = tokenHelper;
		return this;
	}
	/**
	 * 启动服务心跳侦听器<br>
	 * 启动侦听器后CLIENT端才能感知服务端断线，并执行相应动作。
	 * 调用前必须先执行{@link #setTokenHelper(TokenHelper)}初始化
	 * @param token 令牌
	 * @param initJedisPoolLazyDefaultInstance 是否初始化 {@link JedisPoolLazy}默认实例
	 * @return 返回当前{@link ClientExtendTools}实例
	 */
	public ClientExtendTools startServiceHeartbeatListener(Token token,boolean initJedisPoolLazyDefaultInstance){
		checkState(tokenHelper != null,"tokenHelper field must be initialized by setTokenHelper firstly");
		this.tokenRefresh = new TokenRefresh(tokenHelper, token);
		RedisSubscriber subscriber;
		if(initJedisPoolLazyDefaultInstance){
			initRedisDefaultInstance(token);
			subscriber = RedisFactory.getSubscriber();
		}else{
			Map<MQParam, String> redisParam = getRedisParametersLazy(token);
			subscriber = RedisFactory.getSubscriber(JedisPoolLazy.getInstanceByURI(redisParam.get(MQParam.REDIS_URI)));
		}
		subscriber.register(ServiceHeartbeatAdapter.SERVICE_HB_CHANNEL);
		addServiceEventListener(tokenRefreshListener);
		addServiceEventListener(dispatcherListener);
		return this;
	}
	private String taskQueueOf(String task,Token token) {	
		checkArgument(token != null,"token is null");
		try {
			return syncInstance != null 
					? syncInstance.taskQueueOf(task, token)
					: asyncInstance.taskQueueOf(task,token).get();
	    } catch (ExecutionException e) {
	        Throwables.throwIfUnchecked(e.getCause());
	        throw new RuntimeException(e.getCause());
		} catch(Exception e){
	        Throwables.throwIfUnchecked(e);
	        throw new RuntimeException(e);
	    }
	}
	public ParameterSupplier<String> getTaskQueueSupplier(String task,Token token){
		return new TaskQueueSupplier(task,token);
	}
	public ParameterSupplier<String> getSdkTaskQueueSupplier(String task,String sdkVersion,Token token){
		return new TaskQueueSupplier(checkNotNull(task,"task is null") + checkNotNull(sdkVersion,"sdkVersion is null"),token);
	}
}
