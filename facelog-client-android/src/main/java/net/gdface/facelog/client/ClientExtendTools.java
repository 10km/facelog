package net.gdface.facelog.client;

import static com.google.common.base.Preconditions.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import gu.dtalk.MenuItem;
import gu.dtalk.CommonConstant.ReqCmdType;
import gu.dtalk.client.CmdManager;
import gu.dtalk.engine.BaseDispatcher;
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
	private final ServiceHeartbeatListener tokenRefreshListener = new BaseServiceHeartbeatListener(){
		@Override
		public boolean doServiceOnline(ServiceHeartbeatPackage heartbeatPackage){
			// 执行令牌刷新，更新redis参数
			return tokenRefresh !=null && tokenRefresh.refresh();
		}};

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
					getRedisParameters(token);
					return 	true;
				} catch (Exception e) {
					// DO NOTHING
				}
			}
			return false;
		}
	}
	ClientExtendTools(IFaceLogThriftClient syncInstance) {
		super();
		this.syncInstance = checkNotNull(syncInstance,"syncInstance is null");
		this.asyncInstance = null;
		this.factory = syncInstance.getFactory();
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
     * @see ${esc.hash}deviceGroupIdGetter
     * @throws ServiceRuntimeException
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
     * @see ${esc.hash}personGroupBelonsGetter
     * @throws ServiceRuntimeException
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
     * 使用默认REDIS连接池,参见 {@link gu.simplemq.redis.JedisPoolLazy${esc.hash}getDefaultInstance()}
     * @param token 访问令牌(person Token or root Token)
     * @return
     * @throws ServiceRuntimeException
     */
    public CmdManager makeCmdManager(Token token){
        try{
            checkArgument(checkNotNull(token,"token is null").getType() == TokenType.PERSON 
                || token.getType() == TokenType.ROOT,"person or root token required");
            checkArgument(tokenRank.apply(token)>=2,"person or root token required");
            Map<MQParam, String> redisParameters = getRedisParameters(token);
            return new CmdManager(
                    gu.simplemq.redis.JedisPoolLazy.getDefaultInstance(),
                    redisParameters.get(MQParam.CMD_CHANNEL));
        } catch(Exception e){
            Throwables.throwIfUnchecked(e);
            throw new RuntimeException(e);
        }
    }
    /**
     * (设备端)创建设备命令分发器<br>
     * @param token 设备令牌
     * @return
     */
    public BaseDispatcher makeCmdDispatcher(Token token){
    	try{
    		checkArgument(checkNotNull(token,"token is null").getType() == TokenType.DEVICE,"device token required");
    		int deviceId = token.getId();
    		Map<MQParam, String> pameters = syncInstance != null 
    				? syncInstance.getRedisParameters(token) 
    				: asyncInstance.getRedisParameters(token).get();
    				return new BaseDispatcher(deviceId, ReqCmdType.TASKQUEUE, null)
    						.setGroupIdSupplier(this.getDeviceGroupIdSupplier(deviceId))
    						.setCmdSnValidator(cmdSnValidator)
    						.register();
    	} catch (ExecutionException e) {
    		Throwables.throwIfUnchecked(e.getCause());
    		throw new RuntimeException(e.getCause());
    	} catch(Exception e){
    		Throwables.throwIfUnchecked(e);
    		throw new RuntimeException(e);
    	}
    }
    /**
     * 返回一个申请命令响应通道的{@link Supplier}实例
     * @param token 访问令牌
     * @param duration 命令通道有效时间(秒) 大于0有效,否则使用默认的有效期
     * @return
     */
    public Supplier<String> 
    getAckChannelSupplier(final Token token,final long duration){
        return new Supplier<String>(){
            @Override
            public String get() {
                try{
                    return syncInstance != null 
                    		? syncInstance.applyAckChannel(token,duration) 
                    		: asyncInstance.applyAckChannel(token, duration).get();
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
        return getAckChannelSupplier(token,0L);
    }
    /**
     * 返回一个申请命令序号的{@code Supplier}实例
     * @param token
     * @return 访问令牌
     */
    public Supplier<Long> 
    getCmdSnSupplier(final Token token){
        return new Supplier<Long>(){
            @Override
            public Long get() {
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
    /** 设备命令序列号验证器 */
    public final Predicate<Long> cmdSnValidator = 
        new Predicate<Long>(){
            @Override
            public boolean apply(Long input) {
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
	private Map<MQParam, String> getRedisParameters(Token token){
		checkArgument(token != null,"token is null");
		// 获取redis连接参数
		try {
			Map<MQParam, String> param = syncInstance != null 
					? syncInstance.getRedisParameters(token)
					: asyncInstance.getRedisParameters(token).get();
			return insteadHostOfMQParamIfLocalhost(param);
	    } catch (ExecutionException e) {
	        Throwables.throwIfUnchecked(e.getCause());
	        throw new RuntimeException(e.getCause());
		} catch(Exception e){
	        Throwables.throwIfUnchecked(e);
	        throw new RuntimeException(e);
	    }
	}
	/**
	 * @param token 调用 {@link #getRedisParameters(Token)}所需要的令牌
	 * @return 返回一个获取redis参数的{@link Supplier}实例
	 */
	public Supplier<Map<MQParam, String>> getRedisParametersSupplier(final Token token){
		checkArgument(token != null,"token is null");
		return new Supplier<Map<MQParam, String>>(){

			@Override
			public Map<MQParam, String> get() {
				return getRedisParameters(token);
			}};
	}
	/**
	 * @param token 调用 {@link #getRedisParameters(Token)}所需要的令牌
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
		Map<MQParam, String> redisParam = getRedisParameters(token);
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
		Map<MQParam, String> redisParam = getRedisParameters(token);
		URI uri = URI.create(redisParam.get(MQParam.REDIS_URI));
		JedisPoolLazy.getInstance(uri).asDefaultInstance();
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
			Map<MQParam, String> redisParam = getRedisParameters(token);
			URI uri = URI.create(redisParam.get(MQParam.REDIS_URI));
			subscriber = RedisFactory.getSubscriber(JedisPoolLazy.getInstance(uri));
		}
		subscriber.register(ServiceHeartbeatAdapter.SERVICE_HB_CHANNEL);
		addServiceEventListener(tokenRefreshListener);
		return this;
	}
}
