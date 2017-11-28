package net.gdface.facelog.client;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.List;

import com.google.common.collect.Iterators;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;

import gu.simplemq.Channel;
import gu.simplemq.IMessageAdapter;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.RedisPublisher;
import gu.simplemq.redis.JedisPoolLazy;

/**
 * 设备命令执行对象,实现{@link IMessageAdapter}接口<br>
 * 从订阅频道得到设备指令{@link DeviceInstruction},并将交给{@link CommandAdapter}执行<br>
 * 如果是与当前设备无关的命令则跳过
 * @author guyadong
 *
 */
public class CmdChannelAdapter implements IMessageAdapter<DeviceInstruction>{
	private final CommandAdapter cmdAdapter;
	private final int deviceId;
	private List<Integer> groupsBelongs;
	private RedisPublisher redisPublisher = new RedisPublisher(JedisPoolLazy.getDefaultInstance());
	/**
	 * @param cmdAdapter 应用程序执行设备命令的对象
	 * @param deviceId 当前设备ID
	 */
	public CmdChannelAdapter(CommandAdapter cmdAdapter,
			int deviceId) {
		this.cmdAdapter = checkNotNull(cmdAdapter,"cmdAdapter is null");
		this.deviceId= deviceId;
	}
	/** 判断target列表是否包括当前设备 */
	private boolean selfIncluded(boolean group,List<Integer> idList){
		final List<Integer> g = getGroupsBelongs();
		if(null == g){
			return false;
		}
		if(group){
			return Iterators.tryFind(idList.iterator(), new Predicate<Integer>(){
				@Override
				public boolean apply(Integer input) {					
					return g.contains(input);
				}}).isPresent();
		}else{
			return idList.contains(this.deviceId);
		}
	}
	/**
	 * 执行指定的设备命令并向命令响应频道返回命令结果
	 */
	@Override
	public void onSubscribe(DeviceInstruction t) throws SmqUnsubscribeException {
		if(null != t.getTarget() && selfIncluded(t.isGroup(),t.getTarget())){
			Ack<?> ack = t.getCmd().run(cmdAdapter, t.getParameters()).setCmdSn(t.getCmdSn());
			// 如果指定了响应频道则向指定的频道发送响应消息
			if(!Strings.isNullOrEmpty(t.getAckChannel())){
				Channel<Ack<?>> ackChannel = new Channel<Ack<?>>(t.getAckChannel()){};
				redisPublisher.publish(ackChannel, ack);
			}
		}
	}
	/** 
	 * 返回当前设备所属的设备组ID列表<br>
	 * 对于前端设备来说，基所属的设备组是可以动态变化的,所以应用项目可以通过继承此类,
	 * 在子类中重写此方法的途径,提供实时变化的设备组信息
	 *  */
	protected List<Integer> getGroupsBelongs() {
		return groupsBelongs;
	}
	/**
	 * 设置当前设备所属的设备组ID列表,
	 * 创建{@link CmdChannelAdapter}对象时如果不调用本方法,设备不会响应任何设备组命令
	 * @param groupIdList 当前设备ID所属的所有设备组ID,
	 * @return
	 * @see  {@link IFaceLogClient#getDeviceGroupsBelongs(int)}
	 */
	public CmdChannelAdapter groupsBelongs(List<Integer> groupIdList) {
		this.groupsBelongs = groupIdList;
		return this;
	}

}