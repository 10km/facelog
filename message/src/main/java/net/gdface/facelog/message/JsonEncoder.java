package net.gdface.facelog.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class JsonEncoder<B> {
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
	
	
	public abstract Map<String,String> toJsonMap(B obj)throws NotBeanException;

	/**
	 * deserializes json into T
	 * @param json
	 * @param type
	 * @return
	 */
	public abstract B fromJson(String json);
	
	public List<Object> toJsonArray(B[] array){
		if(null == array)return null;		
		try{
			ArrayList<Object> list = new ArrayList<Object>();
			for( B element:array){
				list.add(this.toJsonMap(element));
			}
			return list;
		}catch(NotBeanException e){
			ArrayList<Object> list = new ArrayList<Object>();
			for( B element:array){
				list.add(this.toJsonString(element));
			}
			return list;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> toJsonArray(Collection<B> c){
		return null == c 
				? null
				: toJsonArray(c.toArray((B[])new Object[0]));
	}
}
