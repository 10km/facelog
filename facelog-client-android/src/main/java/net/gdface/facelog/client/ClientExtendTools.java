package net.gdface.facelog.client;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;

import net.gdface.facelog.IFaceLog;
import net.gdface.facelog.MQParam;
import net.gdface.facelog.Token;
import net.gdface.facelog.Token.TokenType;
import net.gdface.facelog.db.DeviceBean;
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
}
