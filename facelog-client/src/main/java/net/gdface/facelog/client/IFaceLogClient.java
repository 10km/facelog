package net.gdface.facelog.client;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.google.common.base.Supplier;

import gu.dtalk.MenuItem;
import gu.simplemq.redis.JedisPoolLazy;
import net.gdface.facelog.IFaceLog;
import net.gdface.facelog.IFaceLogDecorator;
import net.gdface.facelog.MQParam;
import net.gdface.facelog.Token;
import net.gdface.facelog.client.dtalk.DtalkEngineForFacelog;
import net.gdface.facelog.hb.DeviceHeartbeatListener;
import net.gdface.facelog.hb.DeviceHeartbeat;
import net.gdface.facelog.hb.HeartbeatMonitor;
import net.gdface.facelog.hb.ServiceHeartbeatListener;
import net.gdface.facelog.mq.CmdDispatcher;
import net.gdface.facelog.mq.CmdManager;

public class IFaceLogClient extends IFaceLogDecorator {
	public final ClientExtendTools clientTools;
	public IFaceLogClient(IFaceLog delegate) {
		super(delegate);
		clientTools = new ClientExtendTools(delegate);
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
	 * @see net.gdface.facelog.IFaceLogDecorator#getRedisParameters(net.gdface.facelog.Token)
	 * @see ClientExtendTools#insteadHostOfMQParamIfLocalhost(Map)
	 */
	@Override
	public Map<MQParam, String> getRedisParameters(Token token) {
		Map<MQParam, String> parameters = super.getRedisParameters(token);
		return clientTools.insteadHostOfMQParamIfLocalhost(parameters);
	}
	/**
	 * @param token
	 * @return 返回一个获取redis参数的{@link Supplier}实例
	 * @see net.gdface.facelog.client.ClientExtendTools#getRedisParametersSupplier(net.gdface.facelog.Token)
	 */
	public Supplier<Map<MQParam, String>> getRedisParametersSupplier(Token token) {
		return clientTools.getRedisParametersSupplier(token);
	}
	/**
	 * @param token
	 * @return 返回一个获取设备心跳实时监控通道名的{@link Supplier}实例
	 * @see net.gdface.facelog.client.ClientExtendTools#getMonitorChannelSupplier(net.gdface.facelog.Token)
	 */
	public Supplier<String> getMonitorChannelSupplier(Token token) {
		return clientTools.getMonitorChannelSupplier(token);
	}
	/**
	 * 返回有效令牌的{@link Supplier}实例<br>
	 * @return {@link Supplier}实例
	 */
	public Supplier<Token> getTokenSupplier() {
		return clientTools.getTokenSupplier();
	}
	/**
	 * 添加服务心跳侦听器
	 * @param listener
	 * @return 当前{@link IFaceLogClient}对象
	 */
	public IFaceLogClient addServiceEventListener(ServiceHeartbeatListener listener){
		clientTools.addServiceEventListener(listener);	
		return this;
	}
	/**
	 * 删除服务心跳侦听器
	 * @param listener
	 * @return 当前{@link IFaceLogClient}对象
	 */
	public IFaceLogClient removeServiceEventListener(ServiceHeartbeatListener listener){
		clientTools.removeServiceEventListener(listener);
		return this;
	}
	/**
	 * 创建设备心跳包侦听对象
	 * @param listener 设备心跳侦听器实例
	 * @param token 令牌
	 * @param jedisPoolLazy jedis连接池对象，为{@code null}使用默认实例
	 * @return 返回{@link HeartbeatMonitor}实例
	 */
	public HeartbeatMonitor makeHeartbeatMonitor(DeviceHeartbeatListener listener, Token token,JedisPoolLazy jedisPoolLazy) {
		return clientTools.makeHeartbeatMonitor(listener, token, jedisPoolLazy);
	}
	/**
	 * 创建设备心跳包发送对象<br>
	 * {@link DeviceHeartbeat}为单实例,该方法只能调用一次
	 * @param deviceID 设备ID
	 * @param token 设备令牌
	 * @param jedisPoolLazy jedis连接池对象，为{@code null}使用默认实例
	 * @return {@link DeviceHeartbeat}实例
	 */
	public DeviceHeartbeat makeHeartbeat(int deviceID, Token token, JedisPoolLazy jedisPoolLazy) {
		return clientTools.makeHeartbeat(deviceID, token, jedisPoolLazy);
	}
	/**
	 * @param tokenHelper 要设置的 tokenHelper
	 * @return 当前{@link IFaceLogClient}实例
	 */
	public IFaceLogClient setTokenHelper(TokenHelper tokenHelper) {
		clientTools.setTokenHelper(tokenHelper);
		return this;
	}
	/**
	 * 启动服务心跳侦听器<br>
	 * 启动侦听器后CLIENT端才能感知服务端断线，并执行相应动作。
	 * 调用前必须先执行{@link #setTokenHelper(TokenHelper)}初始化
	 * @param token 令牌
	 * @param initJedisPoolLazyDefaultInstance 是否初始化 {@link JedisPoolLazy}默认实例
	 * @return 返回当前{@link IFaceLogClient}实例
	 */
	public IFaceLogClient startServiceHeartbeatListener(Token token, boolean initJedisPoolLazyDefaultInstance) {
		clientTools.startServiceHeartbeatListener(token, initJedisPoolLazyDefaultInstance);
		return this;
	}
}
