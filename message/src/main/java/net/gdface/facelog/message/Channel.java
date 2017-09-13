package net.gdface.facelog.message;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Channel {
	final String name;
	final Type type;
	public Channel(String name, Type type) {
		super();
		Assert.notEmpty(name, "name");
		this.name = name;
		if( null !=null && !(type instanceof ParameterizedType) &&! (type instanceof Class<?>) ){
			throw new IllegalArgumentException("invalid type of 'type'");
		}
		this.type = type;
	}
}
