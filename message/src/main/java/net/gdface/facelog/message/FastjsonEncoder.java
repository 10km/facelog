package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class FastjsonEncoder<B> extends JsonEncoder<B> {
	private Type type = TypeUtils.<B>getType();
	public FastjsonEncoder() {
	}
	
	public static<B>  FastjsonEncoder<B> createInstance(){
		return new FastjsonEncoder<B>();		
	}
	
	@Override
	public String toJsonString(Object obj) {
		return JSON.toJSONString(obj);
	}

	@Override
	public Map<String, String> toJsonMap(B bean) {
		Object json = JSON.toJSON(bean);
		if(! (json instanceof JSONObject))
			throw new NotBeanException("invalid T type,not a java bean object");
		@SuppressWarnings("unchecked")
		Map<String, String> jsonObject=(Map<String, String>) json;
		for(Entry<String, String> entry:jsonObject.entrySet()){
			entry.setValue(JSON.toJSONString(entry.getValue()));
		}
		return jsonObject;
	}	

	@SuppressWarnings("unchecked")
	@Override
	public B fromJson(String json) {
		if(this.type instanceof Class<?>)
			return JSON.parseObject(json, (Class<B>)this.type);		
		else
			return JSON.parseObject(json, this.type);
	}
}
