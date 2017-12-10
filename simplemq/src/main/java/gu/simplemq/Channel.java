package gu.simplemq;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkArgument;

import gu.simplemq.exceptions.SmqUnsubscribeException;

/**
 * (消息)频道对象定义<br>
 * 这里的频道概念可以是发布/订阅模型中的频道,也可以是生产者/消费者模型中的队列
 * @author guyadong
 *
 * @param <T> 频道消息数据类型
 */
public class Channel<T> implements IMessageAdapter<Object>, Cloneable,Constant {
	/**  频道名(消息来源) */
	public final String name;
	/**  频道对应的消息数据类型 */
	public final Type type;

	/**  频道对应的消息处理器 */
	private IMessageAdapter<T> adapter;
	/** 注销侦听器,当前频道被注销时调用 */
	private IUnregistedListener<T> unregistedListener;
	public Channel(String name, Class<T> clazz) {
		this(name,(Type)clazz);
	}
    private static Class<?> getRawClass(Type type){
        if(type instanceof Class<?>){
            return (Class<?>) type;
        } else if(type instanceof ParameterizedType){
            return getRawClass(((ParameterizedType) type).getRawType());
        } else{
            throw new IllegalArgumentException("invalid type");
        }
    }
    /**
     * usage:<pre>new Channel&lt;Model&gt;("name"){};</pre>
     * @param name 频道(通道)名,不可为{@code null}
     */
    protected Channel(String name){
    	checkArgument(!Strings.isNullOrEmpty(name), "name is null or empty");
        this.name = name;
        Type superClass = getClass().getGenericSuperclass();
        this.type = getRawClass(((ParameterizedType) superClass).getActualTypeArguments()[0]);
    }
    /**
     * usage:<pre>new Channel&lt;Model&gt;("name",handle){};</pre>
     */
    protected Channel(String name,IMessageAdapter<T> handle){
    	this(name);
    	this.adapter = handle;
    }
	public Channel(String name, Type type) {
		super();
		checkArgument(!Strings.isNullOrEmpty(name), "name is null or empty");
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
	 * @see gu.simplemq.IMessageAdapter#onSubscribe(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onSubscribe(Object t) throws SmqUnsubscribeException {
		if(null == this.adapter || null == t){
			return;
		}
		try{
			this.adapter.onSubscribe((T) t);
		}catch(SmqUnsubscribeException e){
			throw e;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
	}
	/**
	 * 当前频道被注销时调用
	 * @see #setUnregistedListener(IUnregistedListener)
	 */
	public void onUnregisted(){
		if(null != this.unregistedListener){
			this.unregistedListener.apply(this);
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
	 * @return 
	 */
	public Channel<T> setAdapter(IMessageAdapter<T> adapter) {
		this.adapter = adapter;
		return this;
	}
	
	/**
	 * 返回注销侦听器
	 * @return
	 */
	public IUnregistedListener<T> getUnregistedListener() {
		return unregistedListener;
	}
	/**
	 * 设置注销侦听器
	 * @param unregistedListener
	 * @return 
	 */
	public Channel<T> setUnregistedListener(IUnregistedListener<T> unregistedListener) {
		this.unregistedListener = unregistedListener;
		return this;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj)){
			return true;
		}
		if(!(obj instanceof Channel)){
			return false;
		}
		Channel<?> other = (Channel<?>)obj;
		return Objects.equal(name, other.name) 
				&& Objects.equal(type, other.type);
	}
	
	public boolean equalsFull(Object obj) {
		return equals(obj) && (adapter == ((Channel<?>)obj).adapter);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Channel<T> clone() {
		try {
			return (Channel<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

}
