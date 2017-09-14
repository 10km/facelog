package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * json实现对象序列化反序列化的抽象类
 * @author guyadong
 *
 */
public abstract class JsonEncoder {
	public static  class NotBeanException extends RuntimeException{
		public NotBeanException(String string) {
		}
		private static final long serialVersionUID = 1L;
	}
	public JsonEncoder() {
	
	}
	/**
	 * serializes model to Json
	 * @param obj
	 * @return
	 */
	public abstract String toJsonString(Object obj);
	
	public abstract Map<String,String> toJsonMap(Object obj)throws NotBeanException;

	/**
	 * deserializes json into T
	 * @param json
	 * @param type
	 * @return
	 */
	public abstract <T> T fromJson(String json, Type type);
	
	public abstract <T> T fromJson(Map<String,String> fieldHash, Type type)throws NotBeanException ;
	
	public Map<String,Object> fromJson(Map<String,String> fieldHash,Map<String,Type> types){
		if(null == fieldHash) return null;
		LinkedHashMap<String, Object> fields = new LinkedHashMap<String,Object>();
		for(Entry<String, String> entry:fieldHash.entrySet()){
			String field = entry.getKey();
			fields.put(field, this.fromJson(entry.getValue(), null == types ? null : types.get(field)));
		}
		return fields;
	}
	
	public <T>T fromJson(String json, Class<T> clazz) {		
		return fromJson(json,(Type)clazz);
	}	
	
	public <T>List<Object> toJsonArray(@SuppressWarnings("unchecked") T...array){
		if(null == array)return null;
		ArrayList<Object> list = new ArrayList<Object>();
		for( Object element:array){
			try{
				list.add(this.toJsonMap(element));
			}catch(NotBeanException e){
				list.add(this.toJsonString(element));
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public <T>List<Object> toJsonArray(Collection<T> c){
		return null == c 
				? null
				: toJsonArray(c.toArray((T[])new Object[0]));
	}
	
	public static final JsonEncoder getEncoder(){
		return FastjsonEncoder.getInstance(); 
	}
}
