package net.gdface.facelog.client;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import gu.dtalk.MenuItem;
import gu.simplemq.redis.JedisPoolLazy;
import net.gdface.facelog.IFaceLog;
import net.gdface.facelog.MQParam;
import net.gdface.facelog.ServiceSecurityException;
import net.gdface.facelog.Token;
import net.gdface.facelog.Token.TokenType;
import net.gdface.facelog.client.dtalk.DtalkEngineForFacelog;
import net.gdface.facelog.client.dtalk.FacelogRedisConfigProvider;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.thrift.IFaceLogThriftClient;
import net.gdface.facelog.thrift.IFaceLogThriftClientAsync;
import net.gdface.thrift.ClientFactory;
import net.gdface.utils.NetworkUtil;

/**
 * client端便利性扩展工具类
 * @author guyadong
 *
 */
public class ClientExtendTools {
	private final IFaceLog syncInstance;
	private final IFaceLogThriftClientAsync asyncInstance;
	private final ClientFactory factory;
	ClientExtendTools(IFaceLogThriftClient syncInstance) {
		super();
		this.syncInstance = checkNotNull(syncInstance,"syncInstance is null");
		this.asyncInstance = null;
		factory = syncInstance.getFactory();
	}
	ClientExtendTools(IFaceLogThriftClientAsync asyncInstance) {
		super();
		this.syncInstance = null;
		this.asyncInstance = checkNotNull(asyncInstance,"asyncInstance is null");
		factory = asyncInstance.getFactory();

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
	 * @return 替换主机名参数后的{@code parameters}
	 * @see #insteadHostOfURIIfLocalhost(String)
	 * @see #insteadHostOfURLIfLocalhost(String)
	 */
	public Map<MQParam, String> insteadHostOfMQParamIfLocalhost(Map<MQParam, String> parameters) {
		if(parameters != null){
			if(parameters.containsKey(MQParam.REDIS_URI)){
				String insteaded = insteadHostOfURIIfLocalhost(parameters.get(MQParam.REDIS_URI));
				parameters.put(MQParam.REDIS_URI, insteaded);
			}
			if(parameters.containsKey(MQParam.WEBREDIS_URL)){
				String insteaded = insteadHostOfURLIfLocalhost(parameters.get(MQParam.WEBREDIS_URL));
				parameters.put(MQParam.WEBREDIS_URL, insteaded);
			}
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
            return new CmdManager(
                    gu.simplemq.redis.JedisPoolLazy.getDefaultInstance(),
                    syncInstance != null 
                    	? syncInstance.getRedisParameters(token) 
                    	: asyncInstance.getRedisParameters(token).get());
        } catch (ExecutionException e) {
	        Throwables.throwIfUnchecked(e.getCause());
	        throw new RuntimeException(e.getCause());
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
    public CmdDispatcher makeCmdDispatcher(Token token){
    	try{
    		checkArgument(checkNotNull(token,"token is null").getType() == TokenType.DEVICE,"device token required");
    		int deviceId = token.getId();
    		Map<MQParam, String> pameters = syncInstance != null 
    				? syncInstance.getRedisParameters(token) 
    				: asyncInstance.getRedisParameters(token).get();
    				return new CmdDispatcher(deviceId,
    						this.getDeviceGroupIdSupplier(deviceId))
    						.setCmdSnValidator(cmdSnValidator)
    						.setAckChannelValidator(ackChannelValidator)
    						.registerChannel(pameters.get(MQParam.CMD_CHANNEL));
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
    public Token applyUserToken(int userid,String password,boolean isMd5) throws ServiceSecurityException{
    	Token token;
    	try {
    		if(userid == -1){

    			token = syncInstance != null 
    					? syncInstance.applyRootToken(password, isMd5)
    							: asyncInstance.applyRootToken(password, isMd5).get();

    		}else{
    			token = syncInstance != null 
    					? syncInstance.applyPersonToken(userid, password, isMd5)
    							: asyncInstance.applyPersonToken(userid, password, isMd5).get();
    		}
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
	 * 创建dtalk引擎
	 * @param deviceToken 设备令牌，不可为{@code null}
	 * @param rootMenu 包括所有菜单的根菜单对象，不可为{@code null}
	 * @return
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
}
