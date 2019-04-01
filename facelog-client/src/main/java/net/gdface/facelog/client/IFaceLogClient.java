package net.gdface.facelog.client;

import java.net.URI;
import java.util.List;
import java.util.Map;

import com.google.common.base.Supplier;

import gu.dtalk.MenuItem;
import gu.dtalk.exception.DtalkException;
import gu.dtalk.redis.RedisConfigType;
import net.gdface.facelog.IFaceLog;
import net.gdface.facelog.IFaceLogDecorator;
import net.gdface.facelog.MQParam;
import net.gdface.facelog.Token;
import net.gdface.facelog.client.dtalk.DtalkEngineForFacelog;
import net.gdface.facelog.client.dtalk.FacelogRedisConfigProvider;

public class IFaceLogClient extends IFaceLogDecorator {
	private final ClientExtendTools clientTools;
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

	public DtalkEngineForFacelog initDtalkEngine(Token token, MenuItem root) throws DtalkException{
		Map<MQParam, String> redisParam = getRedisParameters(token);
		FacelogRedisConfigProvider.setRedisLocation(URI.create(redisParam.get(MQParam.REDIS_URI)));
		RedisConfigType configType = RedisConfigType.lookupRedisConnect(null);
		return new DtalkEngineForFacelog(configType, root);
	}
}
