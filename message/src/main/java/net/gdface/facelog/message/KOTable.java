package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.util.Collection;

public abstract class KOTable<T extends IIncludeKey> extends KVTable< T> {
	public KOTable() {
		super();
	}

	public KOTable(Type type) {
		super(type);
	}

	public void set(Collection<T> collection){
		if(null == collection) return ;
		for(T element:collection){
			set(element.returnKey(), element);
		}
	}

	public void set(@SuppressWarnings("unchecked") T ...array){
		if(null == array)return ;
		for(T element:array){
			set(element.returnKey(), element);
		}
	}
}
