package net.gdface.facelog.client;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;

import static com.google.common.base.Preconditions.checkNotNull;

public class CollectionUtils {
	private CollectionUtils() {	}
	public static final<K1,K2,V>Map<K2,V> tranformKeys(Map<K1,V>fromMap, Function<K1, K2> transformer2, Function<K2, K1> transformer1){
		return new TransformedMap<K1, K2, V>(fromMap,transformer2,transformer1);
	}
	public static final <E1,E2>Set<E2>transform(Set<E1> fromSet, Function<E1, E2> transformer2, Function<E2, E1> transformer1){
		return new TransformedSet<E1,E2>(fromSet,transformer2,transformer1);
	}
	static class TransformedSet<E1, E2> extends AbstractSet<E2> {
		final Set<E1> fromSet;
		private final Function<E1, E2> transformer2;
		private final Function<E2, E1> transformer1;

		TransformedSet(Set<E1> fromSet, Function<E1, E2> transformer2, Function<E2, E1> transformer1) {
			checkNotNull(fromSet);
			checkNotNull(transformer2);
			checkNotNull(transformer1);
			this.fromSet = fromSet;
			this.transformer2 = transformer2;
			this.transformer1 = transformer1;
		}

		@Override
		public int size() {
			return fromSet.size();
		}

		@Override
		public boolean isEmpty() {
			return fromSet.isEmpty();
		}

		@Override
		public Iterator<E2> iterator() {
			return Iterators.transform(fromSet.iterator(), transformer2);
		}

		@Override
		public boolean add(E2 e) {
			return fromSet.add(transformer1.apply(e));
		}

		@Override
		public boolean addAll(Collection<? extends E2> c) {
			return fromSet.addAll(Collections2.transform(c, transformer1));
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean contains(Object o) {
			try {
				return fromSet.contains(transformer1.apply((E2) o));
			} catch (ClassCastException e) {
				return false;
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean remove(Object o) {
			try {
				return fromSet.remove(transformer1.apply((E2) o));
			} catch (ClassCastException e) {
				return false;
			}
		}

		@Override
		public void clear() {
			fromSet.clear();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean equals(Object o) {
			if (o instanceof TransformedSet)
				return fromSet.equals(((TransformedSet) o).fromSet);
			return super.equals(o);
		}

		@Override
		public int hashCode() {
			return fromSet.hashCode();
		}
	}

	static class TransformedMap<K1, K2, V> extends AbstractMap<K2, V> {
		final Map<K1, V> fromMap;
		private final Function<K1, K2> transformer2;
		private final Function<K2, K1> transformer1;

		TransformedMap(Map<K1, V> fromMap, Function<K1, K2> transformer2, Function<K2, K1> transformer1) {
			checkNotNull(fromMap);
			checkNotNull(transformer2);
			checkNotNull(transformer1);
			this.fromMap = fromMap;
			this.transformer2 = transformer2;
			this.transformer1 = transformer1;
		}

		@Override
		public int size() {
			return fromMap.size();
		}

		@Override
		public boolean isEmpty() {
			return fromMap.isEmpty();
		}

		@SuppressWarnings("unchecked")
		@Override
		public V get(Object key) {
			try {
				return fromMap.get(transformer1.apply((K2) key));
			} catch (ClassCastException e) {
				return null;
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean containsKey(Object key) {
			try {
				return fromMap.containsKey(transformer1.apply((K2) key));
			} catch (ClassCastException e) {
				return false;
			}
		}

		@Override
		public V put(K2 key, V value) {
			return fromMap.put(transformer1.apply((K2) key), value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public V remove(Object key) {
			try {
				return fromMap.remove(transformer1.apply((K2) key));
			} catch (ClassCastException e) {
				return null;
			}
		}

		@Override
		public void clear() {
			fromMap.clear();
		}

		@Override
		public Set<K2> keySet() {
			return new TransformedSet<K1, K2>(this.fromMap.keySet(), transformer2, transformer1);
		}

		@Override
		public Collection<V> values() {
			return fromMap.values();
		}

		@Override
		public Set<Entry<K2, V>> entrySet() {
			return new TransformedSet<Entry<K1, V>, Entry<K2, V>>(fromMap.entrySet(),
					new Function<Entry<K1, V>, Entry<K2, V>>() {
						@Override
						public java.util.Map.Entry<K2, V> apply(java.util.Map.Entry<K1, V> input) {
							return new TransformedEntry(input);
						}
					}, new Function<Entry<K2, V>, Entry<K1, V>>() {
						@Override
						public java.util.Map.Entry<K1, V> apply(java.util.Map.Entry<K2, V> input) {
							return new SimpleEntry<K1, V>(transformer1.apply(input.getKey()), input.getValue());
						}
					});
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean equals(Object o) {
			if (o instanceof TransformedMap)
				return fromMap.equals(((TransformedMap) o).fromMap);
			return super.equals(o);
		}

		@Override
		public int hashCode() {
			return fromMap.hashCode();
		}
		class TransformedEntry implements Entry<K2, V> {
			final Entry<K1,V>fromEntry;
			TransformedEntry(Entry<K1,V>fromEntry){
				checkNotNull(fromEntry);
				this.fromEntry = fromEntry;
			}
			@Override
			public K2 getKey() {
				return transformer2.apply(fromEntry.getKey());
			}

			@Override
			public V getValue() {
				return fromEntry.getValue();
			}

			@Override
			public V setValue(V value) {
				throw new UnsupportedOperationException();
			}

			@SuppressWarnings("unchecked")
			@Override 
			public boolean equals(Object object) {
				if (TransformedEntry.class.isInstance(object)) {
				//if(object instanceof TransformedEntry)
					return fromEntry.equals((TransformedEntry)object);
				}
				return super.equals(object);
			}

			@Override 
			public int hashCode() {
				return fromEntry.hashCode();
			}

			/**
			 * Returns a string representation of the form {@code {key}={value}}.
			 */
			@Override 
			public String toString() {
				return fromEntry.toString();
			}
		}

	}
}
