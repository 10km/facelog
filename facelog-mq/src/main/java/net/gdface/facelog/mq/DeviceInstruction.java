package net.gdface.facelog.mq;

import java.util.List;
import java.util.Map;

/**
 * 设备命令数据<br>
 * 此类对象将做为设备命令经redis服务器发送到设备端,
 * 数据发到redis服务器以及设备端从redis服务器收到数据的过程要经过JSON序列化和反序列化<br>
 * @author guyadong
 *
 */
public class DeviceInstruction{
	private String cmdpath;
	private long cmdSn;
	private List<Integer> target;
	private boolean group;
	private String ackChannel;
	private Map<String, ?> parameters;
	public DeviceInstruction() {
	}

	public String getCmdpath() {
		return cmdpath;
	}
	public DeviceInstruction setCmdpath(String cmdpath) {
		this.cmdpath = cmdpath;
		return this;
	}
	/** 设置要执行的设备命令类型 */
	public long getCmdSn() {
		return cmdSn;
	}
	/**
	 * 设置唯一的命令序列号,每一次设备命令执行都应该要求一个唯一的序列号,
	 * 以便于命令响应端区分命令响应对象{@link net.gdface.facelog.mq.Ack}来源
	 * @param cmdSn
	 * @return 
	 * @see net.gdface.facelog.client.IFaceLogClient#applyCmdSn(net.gdface.facelog.client.thrift.Token)
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
		this.target = target;
		return this;
	}
	/**
	 * 指定设备命令的目标ID及目标类型(设备/设备组)
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
	 * 设置用于接收设备命令响应{@link net.gdface.facelog.mq.Ack}的通道,
	 * 如果不指定命令响应通道,则命令发送方法无法知道命令执行状态,
	 * 每一次设备命令发送都应该有一个唯一的命令响应接受通道,以便于命令发送方区命令响应来源
	 * @param ackChannel
	 * @return
	 * @see net.gdface.facelog.client.IFaceLogClient#applyAckChannel(net.gdface.facelog.client.thrift.Token)
	 */
	public DeviceInstruction setAckChannel(String ackChannel) {
		this.ackChannel = ackChannel;
		return this;
	}
	public Map<String, ?> getParameters() {
		return parameters;
	}
	/**
	 * 设置设备命令参数,参数个数,类型由{@link Cmd}定义
	 * @param parameters
	 * @return
	 * @see CommandAdapter
	 */
	public DeviceInstruction setParameters(Map<String,?> parameters) {
		this.parameters = parameters;
		return this;
	}

}
