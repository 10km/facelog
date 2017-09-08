package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

public abstract class KOTable<KO extends IIncludeKey> extends KVTable< KO> {
	public KOTable() {
		super();
	}

	public KOTable(Type type) {
		super(type);
	}

	public abstract void set(Collection<KO> collection);

	public void set(@SuppressWarnings("unchecked") KO ...array){
		if(null == array)return ;
		set(Arrays.asList(array));
	}
}
