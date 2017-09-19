package gu.simplemq.json;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import gu.simplemq.exceptions.SmqNotBeanException;
import gu.simplemq.utils.TypeUtils;

/**
 * 
 * 基于fastjson实现{@link JsonEncoder}
 * @author guyadong
 *
 */
class FastjsonEncoder extends JsonEncoder {
	private static final FastjsonEncoder instance = new FastjsonEncoder();
	
	public static FastjsonEncoder getInstance(){
		return instance;		
	}
	
	protected FastjsonEncoder() {}
	
	protected JSONObject _toJSONObject(Object bean){
		// 先序列化再解析成JSONObject对象
		return (JSONObject) JSON.parse(this.toJsonString(bean));//
	}
	
	@Override
	public String toJsonString(Object obj) {
		// java对象序列化(输出 null 字段)
		return JSON.toJSONString(obj,SerializerFeature.WriteMapNullValue);
	}

	@Override
	public Map<String, String> toJsonMap(Object bean)throws SmqNotBeanException {
		if(null ==bean )return null;
		if(!TypeUtils.isJavaBean(bean.getClass()))
			throw new SmqNotBeanException("invalid type,not a java bean object");		

		JSONObject jsonObject =_toJSONObject(bean);
		Map<String, String> fields = new LinkedHashMap<String, String>();
		for(Entry<String, Object> entry : jsonObject.entrySet()) {
			Object value = entry.getValue();
			fields.put(entry.getKey(), null == value ? null : this.toJsonString(value));
		}
		return fields;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T>T fromJson(String json, Type type) {		
		if(type instanceof Class<?>)
			return JSON.parseObject(json, (Class<T>)type);		
		else
			return JSON.parseObject(json, type);
	}
	
	@Override
	public <T> T fromJson(Map<String, String> fieldHash, Type type)throws SmqNotBeanException {
		if(!TypeUtils.isJavaBean(type))
			throw new SmqNotBeanException("invalid type,not a java bean");
		if(null == fieldHash || fieldHash.isEmpty())
			throw new IllegalArgumentException("the argument 'json' must not be null or empty");
		Map<String, Object> fields = new LinkedHashMap<String,Object>(); 
		for(Entry<String, String> entry:fieldHash.entrySet()){
			fields.put(entry.getKey(), JSON.parse(entry.getValue()));
		}
		return com.alibaba.fastjson.util.TypeUtils.cast(fields, type, null);
	}
}
