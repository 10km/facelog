package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
	
	public abstract <T> T fromJson(Map<String,String> json, Type type);
	
	public <T>List<Object> toJsonArray(@SuppressWarnings("unchecked") T...array){
		if(null == array)return null;		
		try{
			ArrayList<Object> list = new ArrayList<Object>();
			for( Object element:array){
				list.add(this.toJsonMap(element));
			}
			return list;
		}catch(NotBeanException e){
			ArrayList<Object> list = new ArrayList<Object>();
			for( Object element:array){
				list.add(this.toJsonString(element));
			}
			return list;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T>List<Object> toJsonArray(Collection<T> c){
		return null == c 
				? null
				: toJsonArray(c.toArray((T[])new Object[0]));
	}
	
	public static final JsonEncoder getEncoder(){
		return FastjsonEncoder1.getInstance(); 
	}
}
