package net.gdface.facelog.service;

import java.util.List;
import java.util.Map;

/**
 * 设备指令
 * @author guyadong
 *
 */
public class DeviceInstruction implements CommonConstant {
	public static enum Cmd{
		reset,message,update;
	}	
	private Cmd cmd;
	private List<Integer> target;
	private boolean group;
	private String ackChannel;
	private Map<String,Object> parameters;
	public DeviceInstruction() {
	}
	public Cmd getCmd() {
		return cmd;
	}
	public void setCmd(Cmd cmd) {
		this.cmd = cmd;
	}
	public List<Integer> getTarget() {
		return target;
	}
	public void setTarget(List<Integer> target) {
		this.target = target;
	}
	public boolean isGroup() {
		return group;
	}
	public void setGroup(boolean group) {
		this.group = group;
	}
	public String getAckChannel() {
		return ackChannel;
	}
	public void setAckChannel(String ackChannel) {
		this.ackChannel = ackChannel;
	}
	public Map<String, Object> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

}
