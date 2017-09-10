package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class FastjsonEncoder2 extends FastjsonEncoder {
	private static final FastjsonEncoder2 instance = new FastjsonEncoder2();
	public static FastjsonEncoder2 getInstance(){
		return instance;		
	}
	private FastjsonEncoder2() {
	}

	@Override
	protected Map<String, String> _toJsonMap(Object bean) {
		JSONObject jsonObject=(JSONObject) JSON.toJSON(bean);
		Map<String, String> fields = new LinkedHashMap<String, String>();
		for(Entry<String, Object> entry:jsonObject.entrySet()){
			Object value = entry.getValue();
			fields.put(entry.getKey(),null == value ? null : JSON.toJSONString(value));
		}
		return fields;
	}

	@Override
	protected <T> T _fromJson(Map<String, String> json, Type type) {
		Map<String, Object> fields = new LinkedHashMap<String,Object>(); 
		for(Entry<String, String> entry:json.entrySet()){
			fields.put(entry.getKey(), JSON.parse(entry.getValue()));
		}
		return com.alibaba.fastjson.util.TypeUtils.cast(fields, type, null);
	}

}
