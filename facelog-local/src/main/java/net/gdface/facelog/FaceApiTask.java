package net.gdface.facelog;

import java.lang.reflect.Method;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import gu.simplemq.redis.JedisUtils;
import net.gdface.sdk.FaceApi;

import static com.google.common.base.Preconditions.*;

class FaceApiTask implements ServiceConstant{
	
	private final RedisManagement rm;
	private final TokenMangement tm;
	private final static ImmutableMap<String, Method> methods;
	static {
		Builder<String, Method> builder = ImmutableMap.builder();
		for(Method m:FaceApi.class.getDeclaredMethods()){
			builder.put(m.getName(), m);
		}
		methods = builder.build();
	}
	public FaceApiTask(RedisManagement rm,TokenMangement tm) {
		this.rm = checkNotNull(rm,"rm is null");
		this.tm = checkNotNull(tm,"tm is null");
	}

	public Object runTask(String sdkVersion,String method,Map<String, Object>parameters){
		checkArgument(Strings.isNullOrEmpty(sdkVersion),"sdkVersion is null or empty");
		checkArgument(methods.containsKey(method),"INVALID method name for FaceApi");		
		JSONObject jsonParam = new JSONObject(parameters);
		String queue = rm.taskQueueOf(TASK_FACEAPI_BASE + sdkVersion);
		checkState(JedisUtils.pubsubNumSub(queue)>0,"not found onlined device for %s",sdkVersion);
		String ackChannel = tm.applyAckChannel(SERVICE_ID, 0);
		long cmdSn = tm.applyCmdSn(SERVICE_ID);
		return null;
	}
}
