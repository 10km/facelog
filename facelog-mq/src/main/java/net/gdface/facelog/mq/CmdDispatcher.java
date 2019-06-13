package net.gdface.facelog.mq;

import static com.google.common.base.Preconditions.*;
import static gu.dtalk.CommonConstant.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicates;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;

import gu.dtalk.BaseItem;
import gu.dtalk.BaseOption;
import gu.dtalk.CmdItem;
import gu.dtalk.MenuItem;
import gu.dtalk.engine.ItemAdapter;
import gu.simplemq.Channel;
import gu.simplemq.IMessageAdapter;
import net.gdface.facelog.CommonConstant;
import gu.simplemq.redis.RedisFactory;

/**
 * 设备命令分发器,实现{@link IMessageAdapter}接口,将redis操作与业务逻辑隔离<br>
 * 从订阅频道或任务队列得到设备指令{@link DeviceInstruction},并将交给{@link ItemAdapter}执行<br>
 * 如果是与当前设备无关的命令则跳过<br>
 * 收到的设备命令将按收到命令的在线程池中顺序执行
 * @author guyadong
 *
 */
public class CmdDispatcher implements IMessageAdapter<DeviceInstruction>,CommonConstant{
    public static final Logger logger = LoggerFactory.getLogger(CmdDispatcher.class);

	private final int deviceId;
	private Supplier<Integer> groupIdSupplier;
	/**  是否自动注销标志 */
	private final AtomicBoolean autoUnregisterCmdChannel = new AtomicBoolean(false);
	/** 设备命令通道 */
	private volatile Channel<DeviceInstruction> cmdChannel;
	 /** 设备命令序列号验证器 */
	private Predicate<Long> cmdSnValidator = Predicates.alwaysTrue();
	/** 设备命令响应通道验证器 */
	private Predicate<String> ackChannelValidator = Predicates.alwaysTrue();
	private ItemAdapter itemAdapter ;
	private ReqType reqType;
	/**
	 * 构造方法<br>
	 *  设备所属的组可能是可以变化的,所以这里需要用{@code Supplier} 接口来动态获取当前设备的设备组
	 * @param deviceId 当前设备ID,应用项目应确保ID是有效的
	 */
	public CmdDispatcher(int deviceId) {
		this.deviceId= deviceId;
	}
	/** 判断target列表是否包括当前设备 */
	private boolean selfIncluded(boolean group,List<Integer> target){
		if(group){
			if(null == groupIdSupplier){
				return false;
			}
			Integer groupId = groupIdSupplier.get();
			return null == groupId ? false : target.contains(groupId); 
		}
		else {
			return target.contains(this.deviceId);
		}
	}
	private JSONObject makeItemJSON(DeviceInstruction deviceInstruction,ReqType reqType){		
		String path = deviceInstruction.getCmdpath();
		checkArgument(!Strings.isNullOrEmpty(path ));
		JSONObject json = new JSONObject();

		BaseItem item = getRootMenu().findOptionChecked(path);
		json.fluentPut(ITEM_FIELD_PATH,path)
			.fluentPut(REQ_FIELD_CMDSN,deviceInstruction.getCmdSn())
			.fluentPut(REQ_FIELD_ACKCHANNEL, deviceInstruction.getAckChannel())
			.fluentPut(REQ_FIELD_REQTYPE,  checkNotNull(reqType,"reqType is null"));
		if(item instanceof BaseOption<?>){
			json.put(OPTION_FIELD_VALUE, deviceInstruction.getParameters().get(OPTION_FIELD_VALUE));
		}else if(item instanceof CmdItem){
			json.put(REQ_FIELD_PARAMETERS, deviceInstruction.getParameters());
		}
		if(ackChannelValidator.apply(deviceInstruction.getAckChannel())){
			json.put(REQ_FIELD_ACKCHANNEL, deviceInstruction.getAckChannel());
		}
		return json;
	}
	private boolean validate(DeviceInstruction deviceInstruction){
		Long cmdSn = deviceInstruction.getCmdSn();
		// 设备命令序列号有效才执行设备命令
		if(!cmdSnValidator.apply(cmdSn)){
			logger.warn("INVALID cmd serial number: {}",cmdSn);
			return false;
		}
		switch(checkNotNull(reqType,"reqType is not initialized")){
		case MULTI:
			return null != deviceInstruction.getTarget() && selfIncluded(deviceInstruction.isGroup(),deviceInstruction.getTarget());
		case TASKQUEUE:
			return true;
		default:
			return false;
		}
	}
	/**
	 * 执行指定的设备命令并向命令响应频道返回命令结果
	 */
	@Override
	public void onSubscribe(DeviceInstruction deviceInstruction) {
		try {
			if(validate(deviceInstruction)){
				JSONObject itemJson = makeItemJSON(deviceInstruction,reqType);
				checkNotNull(itemAdapter,"itemAdapter is not initialized").onSubscribe(itemJson);
			}			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}
	/**
	 * 当前对象注册到指定的频道,重复注册无效
	 * @param channel 设备命令通道名
	 * @return
	 */
	public CmdDispatcher registerChannel(String channel){
		checkArgument(!Strings.isNullOrEmpty(channel),"channel is null or empty");
		// double checked lock
		if(null == cmdChannel){
			synchronized(this){
				if(null == cmdChannel){
					cmdChannel = new Channel<DeviceInstruction>(channel,this){};
					switch (checkNotNull(reqType,"reqType is not initialized")) {
					case MULTI:
						RedisFactory.getSubscriber().register(cmdChannel);
						break;
					case TASKQUEUE:
						RedisFactory.getConsumer().register(cmdChannel);
						break;
					default:
						throw new IllegalStateException("UNSUPPORTED reqType: " + reqType.name());
					}
				}
			}
		}
		return this;
	}
	/**
	 * 当前对象注销设备命令频道
	 * @return
	 */
	public CmdDispatcher unregisterChannel(){
		// double check
		if(null != cmdChannel){
			synchronized(this){
				if(null != cmdChannel){
					switch (reqType) {
					case MULTI:
						RedisFactory.getSubscriber().unregister(cmdChannel);
						break;
					case TASKQUEUE:
						RedisFactory.getConsumer().unregister(cmdChannel);
						break;
					default:
						throw new IllegalStateException("UNSUPPORTED reqType: " + reqType.name());
					}
					logger.debug("unregister cmd channel {}",cmdChannel);
					cmdChannel = null;
				}
			}
		}
		return this;
	}
	/**
	 * 设置命令序列号验证器
	 * @param cmdSnValidator 为{@code null}无效
	 * @return
	 */
	public CmdDispatcher setCmdSnValidator(Predicate<Long> cmdSnValidator) {
		if(null != cmdSnValidator){
			this.cmdSnValidator = cmdSnValidator;
		}
		return this;
	}
	/**
	 * 设备命令响应通道验证器
	 * @param ackChannelValidator 为{@code null}无效
	 * @return
	 */
	public CmdDispatcher setAckChannelValidator(Predicate<String> ackChannelValidator) {
		if(null !=ackChannelValidator){
			this.ackChannelValidator = ackChannelValidator;
		}
		return this;
	}
	/**
	 * 设置程序退出时自动执行{@link #unregisterChannel()}
	 * @return
	 */
	public CmdDispatcher autoUnregisterChannel(){
		if(this.autoUnregisterCmdChannel.compareAndSet(false, true)){
			Runtime.getRuntime().addShutdownHook(new Thread(){
				@Override
				public void run() {
					unregisterChannel();
				}
			});
		}
		return this;
	}
	private MenuItem getRootMenu() {
		return itemAdapter.getRoot();
	}
	public ItemAdapter getItemAdapter() {
		return itemAdapter;
	}
	public CmdDispatcher setItemAdapter(ItemAdapter itemAdapter) {
		this.itemAdapter = checkNotNull(itemAdapter,"itemAdapter is null");
		return this;
	}
	public ReqType getReqType() {
		return reqType;
	}
	public CmdDispatcher setReqType(ReqType reqType) {
		checkArgument(reqType == ReqType.MULTI || reqType == ReqType.TASKQUEUE,"INVALID reqType %s",reqType);
		this.reqType = reqType;
		return this;
	}
	public Supplier<Integer> getGroupIdSupplier() {
		return groupIdSupplier;
	}
	public CmdDispatcher setGroupIdSupplier(Supplier<Integer> groupIdSupplier) {
		this.groupIdSupplier = groupIdSupplier;
		return this;
	}
}