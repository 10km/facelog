package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class FastjsonEncoder extends JsonEncoder {
	private FastjsonEncoder() {
	}
	private static final FastjsonEncoder instance = new FastjsonEncoder();
	public static FastjsonEncoder getInstance(){
		return instance;		
	}
	
	@Override
	public String toJsonString(Object obj) {
		return JSON.toJSONString(obj);
	}

	@Override
	public Map<String, String> toJsonMap(Object bean) {
		if(null ==bean )return null;
		if(!TypeUtils.isJavaBean(bean.getClass()))
			throw new NotBeanException("invalid type,not a java bean object");		
		@SuppressWarnings("unchecked")
		Map<String, String> jsonObject=(Map<String, String>) JSON.parse(JSON.toJSONString(bean));
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

	@Override
	public <T> T fromJson(Map<String, String> json, Type type) {
		HashMap<String, Object> fieldsMap = new HashMap<String,Object>(); 
		for(Entry<String, String> entry:json.entrySet()){
			fieldsMap.put(entry.getKey(), JSON.parse(entry.getValue()));
		}
		return fromJson(JSON.toJSONString(fieldsMap),type);
	}
}
