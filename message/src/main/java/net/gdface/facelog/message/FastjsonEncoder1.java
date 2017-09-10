package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

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
		Map<String, String> jsonObject=(Map<String, String>) JSON.parse(JSON.toJSONString(bean));
		for(Entry<String, String> entry:jsonObject.entrySet()){
			entry.setValue(JSON.toJSONString(entry.getValue()));
		}
		return jsonObject;
	}

	@Override
	public <T> T _fromJson(Map<String, String> json, Type type) {
		HashMap<String, Object> fields = new HashMap<String,Object>(); 
		for(Entry<String, String> entry:json.entrySet()){
			fields.put(entry.getKey(), JSON.parse(entry.getValue()));
		}
		return com.alibaba.fastjson.util.TypeUtils.cast(fields, type, null);
	}

}
