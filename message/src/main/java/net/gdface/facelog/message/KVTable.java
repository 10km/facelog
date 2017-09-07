package net.gdface.facelog.message;

import java.util.Map;
import java.util.Set;

public abstract class KVTable <K,V> {

	public static interface KV<K> {
		public K returnKey();
	}
	public KVTable() {
	}

	public int size() {
		return keySet().size();
	}
	
	public abstract boolean isEmpty() ;

	public abstract boolean containsKey(K key) ;

	public abstract V get(K key) ;

	public abstract  V set(K key, V value) ;

	public abstract V remove(K key) ;

	public abstract void putAll(Map<? extends K, ? extends V> m) ;

	public abstract Set<K> keySet() ;

}
