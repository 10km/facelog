package net.gdface.facelog.service;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * 设备指令
 * @author guyadong
 *
 */
public class DeviceInstruction implements CommonConstant {
	private Cmd cmd;
	private long cmdSn;
	private List<Integer> target;
	private boolean group;
	private String ackChannel;
	private Map<String, ? extends Object> parameters;
	public DeviceInstruction() {
	}
	public Cmd getCmd() {
		return cmd;
	}
	/**
	 * 设置要执行的设备命令类型
	 * @param cmd
	 * @return
	 */
	public DeviceInstruction setCmd(Cmd cmd) {
		this.cmd = checkNotNull(cmd,"cmd is null");
		return this;
	}
	public long getCmdSn() {
		return cmdSn;
	}
	/**
	 * 设置唯一的命令序列号,每一次设备命令执行都要求一个唯一的序列号,
	 * 以便于命令响应端区分命令响应对象{@link net.gdface.facelog.service.Ack}来源
	 * @param cmdSn
	 * @return 
	 * @see {@link RedisManagement#applyCmdSn()}
	 */
	public DeviceInstruction setCmdSn(long cmdSn) {
		this.cmdSn = cmdSn;
		return this;
	}
	public List<Integer> getTarget() {
		return target;
	}
	
	/**
	 * 设置执行命令的设备组或设备ID,类型由{@link #setGroup(boolean)}确定
	 * @param target
	 * @return
	 */
	public DeviceInstruction setTarget(List<Integer> target) {
		checkArgument(null!=target && !target.isEmpty(),"target must not be null or empty");
		this.target = target;
		return this;
	}
	/**
	 * @param target
	 * @param group
	 * @return
	 * @see #setTarget(List)
	 * @see #setGroup(boolean)
	 */
	public DeviceInstruction setTarget(List<Integer> target,boolean group) {
		setTarget(target);
		setGroup(group);
		return this;
	}
	public boolean isGroup() {
		return group;
	}
	/**
	 * 指定目标类型为设备组或设备{@link #setTarget(List)}
	 * @param group 目标类型:{@code true}:设备组,{@code false}:设备
	 * @return
	 */
	public DeviceInstruction setGroup(boolean group) {
		this.group = group;
		return this;
	}
	public String getAckChannel() {
		return ackChannel;
	}
	/**
	 * 设置用于接收设备命令响应{@link net.gdface.facelog.service.Ack}的通道
	 * @param ackChannel
	 * @return
	 * @see {@link RedisManagement#applyAckChannel()}
	 */
	public DeviceInstruction setAckChannel(String ackChannel) {
		this.ackChannel = ackChannel;
		return this;
	}
	public Map<String, ? extends Object> getParameters() {
		return parameters;
	}
	/**
	 * 设置设备命令参数
	 * @param parameters
	 * @return
	 * @see {@link CommandAdapter}
	 */
	public DeviceInstruction setParameters(Map<String,? extends Object> parameters) {
		this.parameters = parameters;
		return this;
	}

}
