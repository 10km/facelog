package net.gdface.facelog.message;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelSub<T> extends Channel implements IOnSubscribe<Object> {
	protected static final Logger logger = LoggerFactory.getLogger(ChannelSub.class);

	private final IOnSubscribe<T> handle;
	public ChannelSub(String name, Type type,IOnSubscribe<T> handle) {
		super(name, type);
		this.handle = handle;
	}
	
	public ChannelSub(String name, Class<T> clazz,IOnSubscribe<T> handle) {
		this(name,(Type)clazz,handle);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onSubscribe(Object t) throws net.gdface.facelog.message.IOnSubscribe.UnsubscribeException {
		if(null == this.handle || null == t)return;
		try{
			this.handle.onSubscribe((T) t);
		}catch(UnsubscribeException e){
			throw e;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
	}
}
