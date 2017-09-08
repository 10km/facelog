package net.gdface.facelog.db;

import java.util.Collection;

public abstract class KOTable extends KVTable< ValueWithKey> {
	public KOTable() {
		super();
	}

	public KOTable(Class<?> type) {
		super(type);
	}

	public void putAll(Collection<ValueWithKey> collection){
		if(null == collection) return ;
		for(ValueWithKey element:collection){
			set(element.returnKey(), element);
		}
	}

	public void putAll(ValueWithKey ...array){
		if(null == array)return ;
		for(ValueWithKey element:array){
			set(element.returnKey(), element);
		}
	}
}
