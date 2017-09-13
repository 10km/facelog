package net.gdface.facelog.message;

import java.lang.reflect.Type;

public class ChannelSub<T> extends Channel {
	final IOnSubscribe<T> handle;
	public ChannelSub(String name, Type type,IOnSubscribe<T> handle) {
		super(name, type);
		this.handle = handle;
	}
	
	public ChannelSub(String name, Class<T> clazz,IOnSubscribe<T> handle) {
		this(name,(Type)clazz,handle);
	}
}
