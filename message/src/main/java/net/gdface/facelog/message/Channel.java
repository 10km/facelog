package net.gdface.facelog.message;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * (消息)频道对象定义<br>
 * 这里的频道概念可以是发布/订阅模型中的频道,也可以是生产者/消费者模型中的队列名
 * @author guyadong
 *
 * @param <T> 频道消息数据类型
 */
public class Channel<T> implements IMessageAdapter<Object> {
	protected static final Logger logger = LoggerFactory.getLogger(Channel.class);
	/**  频道名(消息来源) */
	final String name;
	/**  频道对应的消息数据类型 */
	final Type type;

	/**  频道对应的消息处理器 */
	private IMessageAdapter<T> adapter;
	
	public Channel(String name, Class<T> clazz) {
		this(name,(Type)clazz);
	}
	
	public Channel(String name, Type type) {
		super();
		Assert.notEmpty(name, "name");
		this.name = name;
		if( null !=null && !(type instanceof ParameterizedType) &&! (type instanceof Class<?>) ){
			throw new IllegalArgumentException("invalid type of 'type'");
		}
		this.type = type;
	}
	public Channel(String name, Type type,IMessageAdapter<T> handle) {
		this(name, type);
		this.adapter = handle;
	}
	
	public Channel(String name, Class<T> clazz,IMessageAdapter<T> handle) {
		this(name,(Type)clazz,handle);
	}
	
	/**
	 * {@link #adapter} 代理方法,用于调用 {@link #adapter}的被代理方法前对参数进行合法性检查
	 * @see net.gdface.facelog.message.IMessageAdapter#onSubscribe(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onSubscribe(Object t) throws net.gdface.facelog.message.IMessageAdapter.UnsubscribeException {
		if(null == this.adapter || null == t)return;
		try{
			this.adapter.onSubscribe((T) t);
		}catch(UnsubscribeException e){
			throw e;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
	}
	/**
	 * @return adapter
	 */
	public IMessageAdapter<T> getAdapter() {
		return adapter;
	}
	/**
	 * @param adapter 要设置的 adapter
	 */
	public void setAdapter(IMessageAdapter<T> adapter) {
		this.adapter = adapter;
	}

}
