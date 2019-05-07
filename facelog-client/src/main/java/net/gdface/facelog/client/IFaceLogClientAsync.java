package net.gdface.facelog.client;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import gu.dtalk.MenuItem;
import net.gdface.facelog.MQParam;
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
	 * 如果{@code host}是本机地址则用facelog服务主机名替换
	 * @param host
	 * @return {@code host} or host in {@link #factory}
	 */
	public String insteadHostIfLocalhost(String host) {
		return clientTools.insteadHostIfLocalhost(host);
	}
	/**
	 * 如果{@code uri}的主机名是本机地址则用facelog服务主机名替换
	 * @param uri
	 * @return {@code uri} or new URI instead with host of facelog
	 */
	public URI insteadHostIfLocalhost(URI uri) {
		return clientTools.insteadHostIfLocalhost(uri);
	}
	/**
	 * 如果{@code url}的主机名是本机地址则用facelog服务主机名替换
	 * @param url
	 * @return {@code url} or new URI instead with host of facelog
	 */
	public URL insteadHostIfLocalhost(URL url) {
		return clientTools.insteadHostIfLocalhost(url);
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
	/**
	 * 转换参数中的主机名
	 * @see net.gdface.facelog.thrift.IFaceLogThriftClientAsync#getRedisParameters(net.gdface.facelog.Token)
	 * @see ClientExtendTools#insteadHostOfMQParamIfLocalhost(Map)
	 */
	@Override
	public ListenableFuture<Map<MQParam, String>> getRedisParameters(Token token) {
		ListenableFuture<Map<MQParam, String>> feature = super.getRedisParameters(token);
		return Futures.transform(feature, 
			new Function<Map<MQParam, String>, Map<MQParam, String>>() {

			@Override
			public Map<MQParam, String> apply(Map<MQParam, String> input) {
				return clientTools.insteadHostOfMQParamIfLocalhost(input);
			}
		});
	}
	/**
	 * @param token
	 * @return
	 * @see net.gdface.facelog.client.ClientExtendTools#getRedisParametersSupplier(net.gdface.facelog.Token)
	 */
	public Supplier<Map<MQParam, String>> getRedisParametersSupplier(Token token) {
		return clientTools.getRedisParametersSupplier(token);
	}
	/**
	 * @param token
	 * @return
	 * @see net.gdface.facelog.client.ClientExtendTools#getMonitorChannelSupplier(net.gdface.facelog.Token)
	 */
	public Supplier<String> getMonitorChannelSupplier(Token token) {
		return clientTools.getMonitorChannelSupplier(token);
	}

}
