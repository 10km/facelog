package net.gdface.facelog.message;

import java.lang.reflect.Type;

import com.alibaba.fastjson.JSON;

public class FastjsonEncoder<T> extends JsonEncoder<T> {

	@Override
	public String toJson(T obj) {
		return JSON.toJSONString(obj);
	}

	@Override
	public T fromJson(String json, Class<T> type) {
		return JSON.parseObject(json, type);
	}

	@Override
	public T fromJson(String json, Type type) {
		return JSON.parseObject(json, type);
	}

}
