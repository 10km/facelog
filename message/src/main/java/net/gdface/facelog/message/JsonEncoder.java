package net.gdface.facelog.message;

import java.lang.reflect.Type;

public abstract class JsonEncoder<T> {

	public JsonEncoder() {
	
	}
	/**
	 * serializes model to Json
	 * @param obj
	 * @return
	 */
	public abstract String toJson(T obj);
	
	/**
	 * deserializes json into T
	 * @param json
	 * @param type
	 * @return
	 */
	public abstract T fromJson(String json,Class<T> type);
	
	public abstract T fromJson(String json,Type type);
}
