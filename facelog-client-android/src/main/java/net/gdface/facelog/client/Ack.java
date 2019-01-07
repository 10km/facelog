package net.gdface.facelog.client;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * 设备命令响应对象<br>
 * 此类对象将做为设备命令响应经redis服务器发送到命令发送端,
 * 数据发到redis服务器以及设备端从redis服务器收到数据的过程要经过JSON序列化和反序列化<br>
 * @author guyadong
 *
 * @param <T> 设备命令执行返回结果类型
 */
public class Ack<T> {
	private long cmdSn;
	private int deviceId;
	private T value;
	private Status status;
	private String errorMessage;
	/** 设备命令执行状态 */
	public enum Status{
		/** 调用正常返回 */
		OK,
		/** 设备端不支持的操作 */
		UNSUPPORTED,
		/** 调用出错 */
		ERROR,
		/** 
		 * 响应超时,此错误不是由设备端发送,
		 * 是由命令发送端(本机)在指定的时间内没有收到任何响应而取消频道订阅时产生
		 */ 
		TIMEOUT
	}
	public Ack() {
	}
	/**
	 * 返回状态信息
	 * @return
	 */
	public String message(){
		Preconditions.checkArgument(null != status,"status field is null");
		StringBuffer buffer = new StringBuffer(String.format("device%d@%d:%s", deviceId,cmdSn,status.name()));
		switch(status){
		case ERROR:
			if(!Strings.isNullOrEmpty(errorMessage)){
				buffer.append(":").append(errorMessage);
			}
			break;
		case TIMEOUT:
		case UNSUPPORTED:
		case OK:
		default:
			break;
		}
		return buffer.toString();
	}
	/** 返回设备命令序列号 */
	public long getCmdSn() {
		return cmdSn;
	}
	public Ack<T> setCmdSn(long cmdSn) {
		this.cmdSn = cmdSn;
		return this;
	}
	/** 返回执行设备命令的设备ID */
	public int getDeviceId() {
		return deviceId;
	}

	public Ack<T> setDeviceId(int deviceId) {
		this.deviceId = deviceId;
		return this;
	}
	/** 返回设备命令执行结果对象 */
	public T getValue() {
		return value;
	}
	public Ack<T> setValue(T value) {
		this.value = value;
		return this;
	}
	/** 返回设备命令执行状态 */
	public Status getStatus() {
		return status;
	}
	public Ack<T> setStatus(Status status) {
		this.status = status;
		return this;
	}
	/** 返回设备命令执行错误信息 */
	public String getErrorMessage() {
		return errorMessage;
	}
	public Ack<T> setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return this;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ack [cmdSn=");
		builder.append(cmdSn);
		builder.append(", deviceId=");
		builder.append(deviceId);
		builder.append(", value=");
		builder.append(value);
		builder.append(", status=");
		builder.append(status);
		builder.append(", errorMessage=");
		builder.append(errorMessage);
		builder.append("]");
		return builder.toString();
	}
}
