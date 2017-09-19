package net.gdface.facelog.message;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class NameChecker {
	private static final ConcurrentMap<JedisPoolLazy,NameChecker> checkers = new ConcurrentHashMap<JedisPoolLazy,NameChecker>();

	public static NameChecker getNameChecker(JedisPoolLazy poolLazy){
		// Double Checked Locking
		NameChecker checker = checkers.get(poolLazy);
		if(null ==checker ){
			checkers.putIfAbsent(poolLazy, new NameChecker(poolLazy));
			checker = checkers.get(poolLazy);
		}
		return checker;
	}
	
	private final JedisPoolLazy poolLazy;
	
	private final ConcurrentMap<String,ComponentType> nameTypes = new ConcurrentHashMap<String,ComponentType>(); 
	protected NameChecker(JedisPoolLazy poolLazy) {
		super();
		this.poolLazy = poolLazy;
	}
	public void check(String name,ComponentType type){
		if(Judge.isEmpty(name)|| null == type)
			throw new IllegalArgumentException("the arguments must not be null or empty");
		type.check(poolLazy, name);
		ComponentType old = nameTypes.putIfAbsent(name, type);
		if(old != type){
			throw new IllegalStateException(String.format("the '%s' can't be used for %s",name,type.toString()));
		}
	}
	/**
	 * @return poolLazy
	 */
	public JedisPoolLazy getPoolLazy() {
		return poolLazy;
	}
}
