package net.gdface.facelog.client;

import java.util.List;

import com.google.common.base.Supplier;

import net.gdface.facelog.Token;
import net.gdface.facelog.thrift.IFaceLogThriftClientAsync;
import net.gdface.thrift.ClientFactory;

public class IFaceLogClientAsync extends IFaceLogThriftClientAsync {
	private final ClientExtendTools clientTools;
	public IFaceLogClientAsync(ClientFactory factory) {
		super(factory);
		clientTools = new ClientExtendTools(this);
	}
	public Supplier<Integer> getDeviceGroupIdSupplier(int deviceId) {
		return clientTools.getDeviceGroupIdSupplier(deviceId);
	}
	public Supplier<List<Integer>> getPersonGroupBelonsSupplier(int personId) {
		return clientTools.getPersonGroupBelonsSupplier(personId);
	}
	public CmdManager makeCmdManager(Token token) {
		return clientTools.makeCmdManager(token);
	}
	public CmdDispatcher makeCmdDispatcher(Token token) {
		return clientTools.makeCmdDispatcher(token);
	}
	public Supplier<String> getAckChannelSupplier(Token token, long duration) {
		return clientTools.getAckChannelSupplier(token, duration);
	}
	public Supplier<String> getAckChannelSupplier(Token token) {
		return clientTools.getAckChannelSupplier(token);
	}
	public Supplier<Long> getCmdSnSupplier(Token token) {
		return clientTools.getCmdSnSupplier(token);
	}

}
