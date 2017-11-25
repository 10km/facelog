package net.gdface.facelog.service;

import java.util.List;
import java.util.Map;

import net.gdface.facelog.service.BaseCommandAdapter.Cmd;

/**
 * 设备指令
 * @author guyadong
 *
 */
public class DeviceInstruction implements CommonConstant {
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
	public DeviceInstruction setCmd(Cmd cmd) {
		this.cmd = cmd;
		return this;
	}
	public List<Integer> getTarget() {
		return target;
	}
	public DeviceInstruction setTarget(List<Integer> target) {
		this.target = target;
		return this;
	}
	public boolean isGroup() {
		return group;
	}
	public DeviceInstruction setGroup(boolean group) {
		this.group = group;
		return this;
	}
	public String getAckChannel() {
		return ackChannel;
	}
	public DeviceInstruction setAckChannel(String ackChannel) {
		this.ackChannel = ackChannel;
		return this;
	}
	public Map<String, Object> getParameters() {
		return parameters;
	}
	public DeviceInstruction setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
		return this;
	}

}
