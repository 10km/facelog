package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.util.Map;
import com.alibaba.fastjson.JSON;

public abstract class FastjsonEncoder extends JsonEncoder {
	protected FastjsonEncoder() {}
	
	protected abstract Map<String, String> _toJsonMap(Object bean);
	
	protected abstract <T> T _fromJson(Map<String, String> json, Type type);
	
	@Override
	public String toJsonString(Object obj) {
		return JSON.toJSONString(obj);
	}

	@Override
	public Map<String, String> toJsonMap(Object bean) {
		if(null ==bean )return null;
		if(!TypeUtils.isJavaBean(bean.getClass()))
			throw new NotBeanException("invalid type,not a java bean object");		
		return _toJsonMap(bean);
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
		if(!TypeUtils.isJavaBean(type))
			throw new NotBeanException("invalid type,not a java bean");
		if(null == json || json.isEmpty())
			throw new IllegalArgumentException("the argument 'json' must not be null or empty");
		return _fromJson(json,type);
	}
}
