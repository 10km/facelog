package net.gdface.facelog.client;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import gu.simplemq.Channel;
import gu.simplemq.redis.JedisPoolLazy;
import net.gdface.facelog.client.thrift.RedisParam;
import net.gdface.facelog.client.thrift.Token;

import gu.simplemq.redis.RedisPublisher;

/**
 * 
 * client 端 redis管理模块
 * @author guyadong
 *
 */
class ClientRedisManager {	
	private final Channel<DeviceInstruction> cmdChannel;
	private final IFaceLogClient client;
	private final Token token;
	private final RedisPublisher redisPublisher ;
	private final Map<RedisParam, String> redisParameters;
	protected ClientRedisManager(IFaceLogClient client,JedisPoolLazy poolLazy,Token token) {
		this.client = checkNotNull(client);
		this.token = checkNotNull(token);
		this.redisPublisher = new RedisPublisher(checkNotNull(poolLazy));
		this.redisParameters = client.getRedisParameters(token);
		this.cmdChannel = new Channel<DeviceInstruction>(this.redisParameters.get(RedisParam.CMD_CHANNEL)){};
		
	}
	/** 返回redis服务器地址 */
	public String getRedisURI() {
		return redisParameters.get(RedisParam.REDIS_URI);
	}
	/** 返回redis发布者实例 */
	public RedisPublisher getRedisPublisher() {
		return redisPublisher;
	}
	/** 申请一个唯一的命令响应通道 */
	public String applyAckChannel(){
		return client.applyAckChannel(token);
	}
	/** 返回设备命令通道对象 */
	public Channel<DeviceInstruction> getCmdChannel() {
		return cmdChannel.clone();
	}
	/**
	 * 发送设备命令
	 * @param cmd
	 */
	public void sendDeviceCmd(DeviceInstruction cmd){
		if(null != cmd){
			checkArgument(null != cmd.getCmd(),"cmd field  of DeviceInstruction must not be null");
			Map<String,String>params;
			if(null == cmd.getParameters()){
					params = ImmutableMap.of();
			}else{
					params = Maps.transformValues(cmd.getParameters(), new Function<Object,String>(){
						@Override
						public String apply(Object input) {
							return JSON.toJSONString(input);
						}});
			}
			cmd.setParameters(params);
			getRedisPublisher().publish(this.cmdChannel, cmd);
		}
	}
	/**
	 * 发送设备命令
	 * @param cmd
	 * @param target 执行命令的目标(设备/设备组)
	 * @param group 为@{@code true}时{@code target}为设备组
	 * @param ackChannel 命令响应通道
	 * @param parameters 命令参数
	 * @see {@link DeviceInstruction}
	 */
	public void sendDeviceCmd(Cmd cmd,
							List<Integer> target,
							boolean group,
							String ackChannel,
							Map<String, String> parameters){
		DeviceInstruction deviceInstruction = new DeviceInstruction()
				.setCmd(checkNotNull(cmd))
				.setCmdSn(client.applyCmdSn(token))
				.setTarget(target, group)
				.setAckChannel(ackChannel)
				.setParameters(parameters);
		sendDeviceCmd(deviceInstruction);
	}
	/**
	 * 发送设备命令
	 * @param cmd
	 * @param target 
	 * @param group 
	 * @param ackChannel 
	 * @param parameters
	 */
	public void sendDeviceCmd(Cmd cmd,
			int target,
			boolean group,
			String ackChannel,
			Map<String, String> parameters){
		sendDeviceCmd(cmd,Lists.newArrayList(target),group,ackChannel,parameters);
	}
	/**
	 * 向指定设备({@code deviceId})发送设备命令
	 * @param cmd
	 * @param deviceId
	 * @param ackChannel
	 * @param parameters
	 */
	public void sendDeviceCmd(Cmd cmd,
			int deviceId,
			String ackChannel,
			Map<String, String> parameters){
		sendDeviceCmd(cmd,Lists.newArrayList(deviceId),false,ackChannel,parameters);
	}
}
