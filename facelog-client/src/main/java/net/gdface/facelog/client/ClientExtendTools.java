package net.gdface.facelog.client;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;

import gu.dtalk.MenuItem;
import net.gdface.facelog.IFaceLog;
import net.gdface.facelog.MQParam;
import net.gdface.facelog.ServiceSecurityException;
import net.gdface.facelog.Token;
import net.gdface.facelog.Token.TokenType;
import net.gdface.facelog.client.dtalk.DtalkEngineForFacelog;
import net.gdface.facelog.client.dtalk.FacelogRedisConfigProvider;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.thrift.IFaceLogThriftClientAsync;

/**
 * client端便利性扩展工具类
 * @author guyadong
 *
 */
public class ClientExtendTools {
	private IFaceLog syncInstance;
	private IFaceLogThriftClientAsync asyncInstance;
	ClientExtendTools(IFaceLog syncInstance) {
		super();
		this.syncInstance = checkNotNull(syncInstance,"syncInstance is null");
		this.asyncInstance = null;
	}
	ClientExtendTools(IFaceLogThriftClientAsync asyncInstance) {
		super();
		this.syncInstance = null;
		this.asyncInstance = checkNotNull(asyncInstance,"asyncInstance is null");
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
            }catch(Exception e){
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
            }catch(Exception e){
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
            checkArgument(checkNotNull(token).getType() == TokenType.PERSON 
                || token.getType() == TokenType.ROOT,"person or root token required");
            
            return new CmdManager(
                    gu.simplemq.redis.JedisPoolLazy.getDefaultInstance(),
                    syncInstance != null 
                    	? syncInstance.getRedisParameters(token) 
                    	: asyncInstance.getRedisParameters(token).get());
        }catch(Exception e){
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
            checkArgument(checkNotNull(token).getType() == TokenType.DEVICE,"device token required");
            int deviceId = token.getId();
            Map<MQParam, String> pameters = syncInstance != null 
            		? syncInstance.getRedisParameters(token) 
            		: asyncInstance.getRedisParameters(token).get();
            return new CmdDispatcher(deviceId,
                    this.getDeviceGroupIdSupplier(deviceId))
                .setCmdSnValidator(cmdSnValidator)
                .setAckChannelValidator(ackChannelValidator)
                .setCmdAdapter(new CommandAdapterContainer())
                .registerChannel(pameters.get(MQParam.CMD_CHANNEL));
          }catch(Exception e){
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
                }catch(Exception e){
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
                }catch(Exception e){
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
                }catch(Exception e){
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
                }catch(Exception e){
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
                }catch(Exception e){
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
	                }catch(Exception e){
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
	                }catch(Exception e){
	                    Throwables.throwIfUnchecked(e);
	                    throw new RuntimeException(e);
	                }
	            }};
	/** 
	 * 管理令牌验证器,返回令牌的管理等级
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
	                	if(rootTokenValidator.apply(input)){
	                		return 4;
	                	}else if(personTokenValidator.apply(input)){ 
	                		PersonBean bean = (syncInstance != null 
                    				? syncInstance.getPerson(input.getId()) 
                    				: asyncInstance.getPerson(input.getId()).get());
	                		Integer rank = bean.getRank();
	                		return rank != null ? rank : 0;
	                	}
	                    return -1;
	                }catch(Exception e){
	                    Throwables.throwIfUnchecked(e);
	                    throw new RuntimeException(e);
	                }
	            }};
    public Token applyUserToken(int userid,String password,boolean isMd5) throws ServiceSecurityException, InterruptedException, ExecutionException{
    	Token token;
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
		Map<MQParam, String> redisParam;
		try {
			redisParam = syncInstance != null 
				? syncInstance.getRedisParameters(deviceToken)
				: asyncInstance.getRedisParameters(deviceToken).get();
        }catch(Exception e){
            Throwables.throwIfUnchecked(e);
            throw new RuntimeException(e);
        }
		FacelogRedisConfigProvider.setRedisLocation(URI.create(redisParam.get(MQParam.REDIS_URI)));
		return new DtalkEngineForFacelog(checkNotNull(rootMenu,"rootMenu is null"), tokenRank);		
	}
}
