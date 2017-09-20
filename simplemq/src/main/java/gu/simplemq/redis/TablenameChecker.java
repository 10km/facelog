package gu.simplemq.redis;

import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import gu.simplemq.utils.Judge;

public class TablenameChecker implements IRedisComponent{
	private static final ConcurrentMap<JedisPoolLazy,TablenameChecker> checkers = new ConcurrentHashMap<JedisPoolLazy,TablenameChecker>();

	public static TablenameChecker getNameChecker(JedisPoolLazy poolLazy){
		// Double Checked Locking
		TablenameChecker checker = checkers.get(poolLazy);
		if(null ==checker ){
			checkers.putIfAbsent(poolLazy, new TablenameChecker(poolLazy));
			checker = checkers.get(poolLazy);
		}
		return checker;
	}
	
	private final JedisPoolLazy poolLazy;
	
	private final ConcurrentMap<String,Type> nameTypes = new ConcurrentHashMap<String,Type>(); 
	protected TablenameChecker(JedisPoolLazy poolLazy) {
		super();
		this.poolLazy = poolLazy;
	}
	public String check(String name,RedisComponentType componentType,Type type){
		if(Judge.isEmpty(name) || null == componentType || null == type )
			throw new IllegalArgumentException("the arguments must not be null or empty");
		componentType.check(poolLazy, name);
		Type old = nameTypes.putIfAbsent(name, type);
		if(null != old && old != type){
			throw new IllegalStateException(String.format("the '%s' can't be used for %s,because it be used for %s",name,type.toString(),old.toString()));
		}
		return name;
	}

	@Override
	public JedisPoolLazy getPoolLazy() {
		return poolLazy;
	}
}
