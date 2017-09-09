package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class FastjsonEncoder extends JsonEncoder {
	public FastjsonEncoder() {
	}
	
	public static FastjsonEncoder createInstance(){
		return new FastjsonEncoder();		
	}
	
	@Override
	public String toJsonString(Object obj) {
		return JSON.toJSONString(obj);
	}

	@Override
	public Map<String, String> toJsonMap(Object bean) {
		Object json = JSON.toJSON(bean);
		if(! (json instanceof JSONObject))
			throw new NotBeanException("invalid type,not a java bean object");
		@SuppressWarnings("unchecked")
		Map<String, String> jsonObject=(Map<String, String>) json;
		for(Entry<String, String> entry:jsonObject.entrySet()){
			entry.setValue(JSON.toJSONString(entry.getValue()));
		}
		return jsonObject;
	}	

	@SuppressWarnings("unchecked")
	@Override
	public <T>T fromJson(String json, Type type) {
		if(null == type)
			throw new NullPointerException();
		if(type instanceof Class<?>)
			return JSON.parseObject(json, (Class<T>)type);		
		else
			return JSON.parseObject(json, type);
	}
}
