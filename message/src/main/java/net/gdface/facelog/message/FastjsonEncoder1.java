package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;

public class FastjsonEncoder1 extends FastjsonEncoder {
	private static final FastjsonEncoder1 instance = new FastjsonEncoder1();
	public static FastjsonEncoder1 getInstance(){
		return instance;		
	}
	private FastjsonEncoder1() {
	}

	@Override
	public Map<String, String> _toJsonMap(Object bean) {
		if(null ==bean )return null;
		if(!TypeUtils.isJavaBean(bean.getClass()))
			throw new NotBeanException("invalid type,not a java bean object");		
		@SuppressWarnings("unchecked")
		Map<String, String> jsonObject=(Map<String, String>) JSON.parse(this.toJsonString(bean));
		for(Entry<String, String> entry:jsonObject.entrySet()){
			Object value = entry.getValue();
			entry.setValue(null == value? null : JSON.toJSONString(value));
		}
		return jsonObject;
	}

	@Override
	public <T> T _fromJson(Map<String, String> json, Type type) {
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String,Object>(); 
		for(Entry<String, String> entry:json.entrySet()){
			fields.put(entry.getKey(), JSON.parse(entry.getValue()));
		}
		return com.alibaba.fastjson.util.TypeUtils.cast(fields, type, null);
	}

}
