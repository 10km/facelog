package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class KVTable<V>{
	protected static  class BreakException extends RuntimeException{
		private static final long serialVersionUID = 1L;		
	}
	public static class CallException extends RuntimeException{
		private static final long serialVersionUID = 1L;

		public CallException() {
			super();
		}

		public CallException(String message, Throwable cause) {
			super(message, cause);
		}

		public CallException(String message) {
			super(message);
		}

		public CallException(Throwable cause) {
			super(cause);
		}
		
	}
	public static interface Filter<V>{
		boolean run(String key, V value) throws BreakException;
	}
	protected final Filter<V> alwaysTrue=new Filter<V>(){
		@Override
		public boolean run(String key, V value) throws BreakException {
			return true;
		}};
	private final Type type;

//	protected final boolean isJavaBean = TypeUtils.<V>isJavaBean();

	public KVTable(Type type) {
		super();
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public abstract V get(String key);

	public abstract boolean set(String key, V value);
	
	public abstract boolean setIfAbsent(String key, V value);

	public abstract <T>void modify(String key, String field, T value,Type type);

	public abstract int remove(String key);
	
	public abstract Set<String> keys(String pattern) ;
	
	public abstract void set(Map<String, ? extends V> m) ;
	
	public int size(String pattern) {
		return keys(pattern).size();
	}

	public boolean isEmpty() {
		return 0 == size(null);
	}

	public boolean containsKey(String key) {
		return get(key)!=null;
	}

	public boolean containsValue(final String pattern,final V v) {
		if(null != v){
			final AtomicBoolean b=new AtomicBoolean(false); 
			foreach(pattern,new Filter<V>(){
				@Override
				public boolean run(String key, V value) throws BreakException {
					if(v.equals(value)){
						b.set(true);
						throw new BreakException();
					}
					return false;
				}});
			return b.get();
		}
		return false;
	}
	
	public Map<String, V> values(final String pattern, Filter<V> filter) {
			final HashMap<String, V> map = new HashMap<String,V>(); 
			final Filter<V> f = null == filter ?alwaysTrue : filter;
			foreach(pattern,new Filter<V>(){
				@Override
				public boolean run(String key, V value) throws BreakException {
					if(f.run(key, value)){
						map.put(key, value);
					}
					return false;
				}});
			return map;
	}
	
	public int foreach(String pattern, Filter<V> filter) {
		int count=0;
		try {
			for (String key : keys(pattern)) {
				V value = get(key);
				if(null != value && filter.run(key, value))
					++count;
			}
		} catch (BreakException e) {}
		return count;
	}
	
	public int removeKeys(String pattern, final Filter<V> filter) {
		final AtomicInteger count=new AtomicInteger(0); 
		foreach(pattern,new Filter<V>(){
			@Override
			public boolean run(String key, V value) throws BreakException {
				if(null == filter|| filter.run(key, value)){
					count.addAndGet(remove(key))	;
				}
				return false;
			}});
		return count.get();
	}
}
