package net.gdface.facelog.client.dtalk;

import net.gdface.annotation.DeriveMethod;
import net.gdface.sdk.FaceApi;
import static com.google.common.base.Preconditions.*;
import static net.gdface.facelog.client.dtalk.FacelogMenu.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.weakref.jmx.com.google.common.collect.Sets;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import gu.dtalk.ICmdImmediateAdapter;
import gu.dtalk.exception.CmdExecutionException;
import gu.simplemq.json.JSONObjectDecorator;

/**
 * 将{@link FaceApi}实例封装成{@link ICmdImmediateAdapter}接口<br>
 * 为设备端的SDK版本算法提供远程调用能力
 * @author guyadong
 *
 */
class FaceApiCmdAdapter implements ICmdImmediateAdapter {
	
	
	public static final Function<Method, String> FUN_PORT_GETTER = new Function<Method, String>() {

		@Override
		public String apply(Method method) {
			if(method == null){
				return null;
			}
			String port = method.getName();
			DeriveMethod deriveMethod = method.getAnnotation(DeriveMethod.class);		
			if(deriveMethod != null && deriveMethod.methodSuffix().length > 0){
				return port + deriveMethod.methodSuffix()[0];
			}
			return port;
		}
	};
	private static final ImmutableMap<String, Method> methods =
		Maps.uniqueIndex(Arrays.asList(FaceApi.class.getDeclaredMethods()), FUN_PORT_GETTER);
	private BiMap<String, FaceApi> faceapis = HashBiMap.create();
	
	static final FaceApiCmdAdapter INSTANCE = new FaceApiCmdAdapter();
	private FaceApiCmdAdapter(){
	}
	FaceApiCmdAdapter(String sdkVersion,FaceApi faceApi) {
		bindFaceApi(	sdkVersion,faceApi);
	}
	FaceApiCmdAdapter(Map<String,FaceApi> faceApis) {
		this.faceapis.putAll(MoreObjects.firstNonNull(faceApis, Collections.<String,FaceApi>emptyMap()));
	}
	@Override
	public Object apply(Map<String, Object> input) throws CmdExecutionException {
		JSONObjectDecorator json = new JSONObjectDecorator(input);
		
		final String sdkVersion = json.getStringOrNull(CMD_FACEAPI_SDKVERSION);
		checkArgument(sdkVersion != null,"NOT DEFINED valid sdkVersion");
		final FaceApi instance = faceapis.get(sdkVersion);
		checkArgument(instance != null,"UNSUPPORTED sdkVersion %s",sdkVersion);
		final String name = json.getStringOrNull(CMD_FACEAPI_METHOD);
		checkArgument(name != null,"NOT DEFINED valid method name");		
		final Method method = methods.get(name);
		checkArgument(method != null,"INVALID method name for FaceApi %s",name);

		try {
			JSONArray parameters = MoreObjects.firstNonNull(json.getJSONArray(CMD_FACEAPI_PARAMETERS),new JSONArray());
			Type[] paramTypes = method.getGenericParameterTypes();
			checkArgument(paramTypes.length == parameters.size(),
					"MISMATCH PARAMETER NUMBER for %s,input %s,required %s",name,parameters.size(),paramTypes.length);
			Object[] args = new Object[paramTypes.length];

			// 从CMD_FACEAPI_PARAMETERS中解析参数顺序填充到参数列表中调用faceapi实例
			for(int i = 0;  i < args.length; ++i){
				args[i] = parameters.getObject(i, paramTypes[i]);
				if(paramTypes[i] instanceof Class<?>){
					checkArgument(!((Class<?>)paramTypes[i]).isPrimitive() || args[i] !=null,"arg%s of method %s is primitive type,must not be null",i,name);
				}
			}
			return method.invoke(instance, args);
		} catch (JSONException e) {
			throw new CmdExecutionException(e);
		}catch (IllegalAccessException e) {
			throw new CmdExecutionException(e);
		} catch (IllegalArgumentException e) {
			throw new CmdExecutionException(e);
		} catch (InvocationTargetException e) {
			throw new CmdExecutionException(e.getTargetException());
		} catch (Exception e) {
			Throwables.throwIfUnchecked(e);
			throw new CmdExecutionException(e);
		}
	}
	void bindFaceApi(String sdkVersion,FaceApi faceApi){
		checkArgument(faceApi != null,"faceApi is null");
		checkArgument(faceApi.isLocal(),"faceApi must be local implemention");
		faceapis.put(checkNotNull(sdkVersion,"sdkVersion is null"),faceApi);			
	}
	void unbindFaceApi(String sdkVersion){
		faceapis.remove(checkNotNull(sdkVersion,"sdkVersion is null"));
	}
	void unbindFaceApi(FaceApi faceApi){
		faceapis.inverse().remove(checkNotNull(faceApi,"faceApi is null"));
	}
	Set<String> useSdks(){
		return Sets.newHashSet(faceapis.keySet());
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FaceApiCmdAdapter [faceapis=");
		builder.append(faceapis);
		builder.append("]");
		return builder.toString();
	}
}
