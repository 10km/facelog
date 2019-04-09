package net.gdface.facelog.client;

import java.util.List;

import com.google.common.base.Supplier;

import gu.dtalk.MenuItem;
import net.gdface.facelog.ServiceSecurityException;
import net.gdface.facelog.Token;
import net.gdface.facelog.client.dtalk.DtalkEngineForFacelog;
import net.gdface.facelog.thrift.IFaceLogThriftClientAsync;
import net.gdface.thrift.ClientFactory;

public class IFaceLogClientAsync extends IFaceLogThriftClientAsync {
	private final ClientExtendTools clientTools;
	public IFaceLogClientAsync(ClientFactory factory) {
		super(factory);
		clientTools = new ClientExtendTools(this);
	}
	/**
	 * @param deviceId
	 * @return
	 * @see net.gdface.facelog.client.ClientExtendTools#getDeviceGroupIdSupplier(int)
	 */
	public Supplier<Integer> getDeviceGroupIdSupplier(int deviceId) {
		return clientTools.getDeviceGroupIdSupplier(deviceId);
	}
	/**
	 * @param personId
	 * @return
	 * @see net.gdface.facelog.client.ClientExtendTools#getPersonGroupBelonsSupplier(int)
	 */
	public Supplier<List<Integer>> getPersonGroupBelonsSupplier(int personId) {
		return clientTools.getPersonGroupBelonsSupplier(personId);
	}
	/**
	 * @param token
	 * @return
	 * @see net.gdface.facelog.client.ClientExtendTools#makeCmdManager(net.gdface.facelog.Token)
	 */
	public CmdManager makeCmdManager(Token token) {
		return clientTools.makeCmdManager(token);
	}
	/**
	 * @param token
	 * @return
	 * @see net.gdface.facelog.client.ClientExtendTools#makeCmdDispatcher(net.gdface.facelog.Token)
	 */
	public CmdDispatcher makeCmdDispatcher(Token token) {
		return clientTools.makeCmdDispatcher(token);
	}
	/**
	 * @param token
	 * @param duration
	 * @return
	 * @see net.gdface.facelog.client.ClientExtendTools#getAckChannelSupplier(net.gdface.facelog.Token, long)
	 */
	public Supplier<String> getAckChannelSupplier(Token token, long duration) {
		return clientTools.getAckChannelSupplier(token, duration);
	}
	/**
	 * @param token
	 * @return
	 * @see net.gdface.facelog.client.ClientExtendTools#getAckChannelSupplier(net.gdface.facelog.Token)
	 */
	public Supplier<String> getAckChannelSupplier(Token token) {
		return clientTools.getAckChannelSupplier(token);
	}
	/**
	 * @param token
	 * @return
	 * @see net.gdface.facelog.client.ClientExtendTools#getCmdSnSupplier(net.gdface.facelog.Token)
	 */
	public Supplier<Long> getCmdSnSupplier(Token token) {
		return clientTools.getCmdSnSupplier(token);
	}
	/**
	 * @param userid
	 * @param password
	 * @param isMd5
	 * @return
	 * @throws ServiceSecurityException
	 * @see net.gdface.facelog.client.ClientExtendTools#applyUserToken(int, java.lang.String, boolean)
	 */
	public Token applyUserToken(int userid, String password, boolean isMd5) throws ServiceSecurityException {
		return clientTools.applyUserToken(userid, password, isMd5);
	}
	/**
	 * @param deviceToken
	 * @param rootMenu
	 * @return
	 * @see net.gdface.facelog.client.ClientExtendTools#initDtalkEngine(net.gdface.facelog.Token, gu.dtalk.MenuItem)
	 */
	public DtalkEngineForFacelog initDtalkEngine(Token deviceToken, MenuItem rootMenu) {
		return clientTools.initDtalkEngine(deviceToken, rootMenu);
	}
	/**
	 * @param token
	 * @see net.gdface.facelog.client.ClientExtendTools#initDtalkRedisLocation(net.gdface.facelog.Token)
	 */
	public void initDtalkRedisLocation(Token token) {
		clientTools.initDtalkRedisLocation(token);
	}
	/**
	 * @param token
	 * @see net.gdface.facelog.client.ClientExtendTools#initRedisDefaultInstance(net.gdface.facelog.Token)
	 */
	public void initRedisDefaultInstance(Token token) {
		clientTools.initRedisDefaultInstance(token);
	}


}
