package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

public class FastjsonEncoder2 extends FastjsonEncoder {
	private static final FastjsonEncoder2 instance = new FastjsonEncoder2();
	public static FastjsonEncoder2 getInstance(){
		return instance;		
	}
	private FastjsonEncoder2() {
	}

	@Override
	protected Map<String, String> _toJsonMap(Object bean) {
		@SuppressWarnings("unchecked")
		Map<String, String> jsonObject=(Map<String, String>) JSON.toJSON(bean);
		for(Entry<String, String> entry:jsonObject.entrySet()){
			entry.setValue(JSON.toJSONString(entry.getValue()));
		}
		return jsonObject;
	}

	@Override
	protected <T> T _fromJson(Map<String, String> json, Type type) {
		HashMap<String, Object> fields = new HashMap<String,Object>(); 
		for(Entry<String, String> entry:json.entrySet()){
			fields.put(entry.getKey(), JSON.parse(entry.getValue()));
		}
		return com.alibaba.fastjson.util.TypeUtils.cast(fields, type, ParserConfig.global);
	}

}
