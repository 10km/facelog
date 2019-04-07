package net.gdface.facelog.client;

import java.util.List;

import com.google.common.base.Supplier;

import gu.dtalk.MenuItem;
import net.gdface.facelog.IFaceLog;
import net.gdface.facelog.IFaceLogDecorator;
import net.gdface.facelog.ServiceSecurityException;
import net.gdface.facelog.Token;
import net.gdface.facelog.client.dtalk.DtalkEngineForFacelog;

public class IFaceLogClient extends IFaceLogDecorator {
	public final ClientExtendTools clientTools;
	public IFaceLogClient(IFaceLog delegate) {
		super(delegate);
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
	public Token applyUserToken(int userid, String password, boolean isMd5)
			throws ServiceSecurityException {
		return clientTools.applyUserToken(userid, password, isMd5);
	}
	public DtalkEngineForFacelog initDtalkEngine(Token deviceToken, MenuItem rootMenu) {
		return clientTools.initDtalkEngine(deviceToken, rootMenu);
	}
	public void initDtalkRedisLocation(Token token) {
		clientTools.initDtalkRedisLocation(token);
	}

}
