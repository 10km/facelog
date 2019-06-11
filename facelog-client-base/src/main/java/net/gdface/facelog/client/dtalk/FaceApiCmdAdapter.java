package net.gdface.facelog.client.dtalk;

import net.gdface.sdk.FaceApi;
import static com.google.common.base.Preconditions.*;
import static net.gdface.facelog.client.dtalk.FacelogMenu.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import gu.dtalk.ICmdImmediateAdapter;
import gu.dtalk.exception.CmdExecutionException;

public class FaceApiCmdAdapter implements ICmdImmediateAdapter {
	private final static ImmutableMap<String, Method> methods;
	static {
		Builder<String, Method> builder = ImmutableMap.builder();
		for(Method m:FaceApi.class.getDeclaredMethods()){
			builder.put(m.getName(), m);
		}
		methods = builder.build();
	}
	private BiMap<String, FaceApi> faceapis = HashBiMap.create();
	public FaceApiCmdAdapter(){
		
	}
	public FaceApiCmdAdapter(String sdkVersion,FaceApi faceApi) {
		bindFaceApi(
				checkNotNull(sdkVersion,"sdkVersion is null"),
				checkNotNull(faceApi,"faceApi is null"));
	}
	public FaceApiCmdAdapter(Map<String,FaceApi> faceApis) {
		this.faceapis.putAll(MoreObjects.firstNonNull(faceApis, Collections.<String,FaceApi>emptyMap()));
	}
	@Override
	public Object apply(Map<String, Object> input) throws CmdExecutionException {
		checkArgument(faceapis.containsKey(input.get(CMD_FACEAPI_SDKVERSION)),
				"UNSUPPORTED sdkVersion %s",	input.get(CMD_FACEAPI_SDKVERSION));
		String sdkVersion = (String) input.get(CMD_FACEAPI_SDKVERSION);
		checkArgument(input.get(CMD_FACEAPI_METHOD) instanceof String,"NOT DEFINED valid method name");
		String name = (String) input.get(CMD_FACEAPI_METHOD);
		Method method = methods.get(name);		
		checkArgument(method != null,"INVALID method name for FaceApi");
		JSONArray parameters = TypeUtils.cast(input.get(CMD_FACEAPI_PARAMETERS), JSONArray.class, ParserConfig.getGlobalInstance());
		Type[] paramTypes = method.getGenericParameterTypes();
		checkArgument(paramTypes.length == parameters.size(),
				"MISMATCH PARAMETER NUMBER for %s,input %s,required %s",name,parameters.size(),paramTypes.length);
		Object[] args = new Object[paramTypes.length];

		try {
			// 从CMD_FACEAPI_PARAMETERS中解析参数顺序填充到参数列表中调用faceapi实例
			for(int i = 0; i < args.length;++i){
				args[i] = TypeUtils.cast(parameters.get(i), paramTypes[i], ParserConfig.getGlobalInstance()); 
				if(paramTypes[i] instanceof Class<?>){
					checkArgument(!((Class<?>)paramTypes[i]).isPrimitive() || args[i] !=null,"arg%s of method %s is primitive type,must not be null",i,name);
				}
			}
			return method.invoke(faceapis.get(sdkVersion), args);
		} catch (IllegalAccessException e) {
			throw new CmdExecutionException(e);
		} catch (IllegalArgumentException e) {
			throw new CmdExecutionException(e);
		} catch (InvocationTargetException e) {
			throw new CmdExecutionException(e.getTargetException());
		} catch (Exception e) {
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}
	public void bindFaceApi(String sdkVersion,FaceApi faceApi){
		faceapis.put(checkNotNull(sdkVersion,"sdkVersion is null"),
				checkNotNull(faceApi,"faceApi is null"));
	}
}
