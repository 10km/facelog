package net.gdface.facelog.db;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class KVTable<V>{

	private final Class<?> type;

	public KVTable() {
		this(null);
	}

	public KVTable(Class<?> type) {
		super();
		this.type = type;
	}

	public Class<?> getType() {
		return type;
	}

	public abstract V get(String key);

	public abstract V set(String key, V value);

	public abstract V remove(String key);
	
	public abstract Set<String> keys() ;
	
	public int size() {
		return keys().size();
	}

	public boolean isEmpty() {
		return 0 == size();
	}

	public boolean containsKey(String key) {
		return get(key)!=null;
	}

	public boolean containsValue(V value) {
		if(null != value){
			for(String key:keys()){
				if(get(key).equals(value))return true;
			}
		}
		return false;
	}
	
	public void putAll(Map<String, ? extends V> m) {
		if(null == m) return ;
		for(Entry<String, ? extends V> entry:m.entrySet())	{
			set(entry.getKey(),entry.getValue());
		}
	}

	public void clear() {
		for(String key:keys()){
			remove(key);
		}
	}
}
