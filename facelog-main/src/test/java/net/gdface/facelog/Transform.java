package net.gdface.facelog;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Maps.EntryTransformer;

public class Transform {
	public static final <K1,K2,V>Map<K2,V> transform(Map<K1,V>fromMap,final Function<K1,K2>transformer){
		checkNotNull(fromMap,"fromMap is null");
		checkNotNull(transformer,"transformer is null");
		ImmutableMap<K2, Entry<K1, V>> k2Entry = Maps.uniqueIndex(fromMap.entrySet(), new Function<Entry<K1, V>,K2>(){
			@Override
			public K2 apply(Entry<K1, V> input) {				
				return transformer.apply(input.getKey());
			}});
		Map<K2, V> k2V = Maps.transformEntries(k2Entry, new EntryTransformer<K2,Entry<K1,V>,V>(){
			@Override
			public V transformEntry(K2 key, Entry<K1, V> value) {
				return value.getValue();
			}});
		return k2V;
	}

	public static final <E1,E2>Set<E2> transform(final Set<E1>fromSet,final Function<E1,E2>transformer){
		checkNotNull(fromSet,"fromMap is null");
		checkNotNull(transformer,"transformer is null");
		return ImmutableSet.copyOf(Iterators.transform(fromSet.iterator(), transformer));
	}
	public Transform() {	
	}
}
