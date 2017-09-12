package net.gdface.facelog.message;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

import net.gdface.facelog.message.JedisPoolLazy.PropName;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * 
 * 封装 redis list 为阻塞式双向队列 {@link BlockingDeque}
 * 
 * @author guyadong
 *
 * @param <E> 队列中的元素类型
 */
public class RedisQueue<E> extends AbstractQueue<E>implements BlockingDeque<E>,IRedisComponent {
	private final Type type;
	private JsonEncoder encoder = JsonEncoder.getEncoder();
	/** 队列名(key) */
	private String queueName;
	private final JedisPoolLazy poolLazy;
	@Override
	public JedisPoolLazy getPoolLazy() {
		return poolLazy;
	}

	private Jedis getJedis(){
        return poolLazy.apply();
    }
    
    private void releaseJedis() {
    	poolLazy.free();
    }
    
	public RedisQueue(Type type) {
		this(type,JedisPoolLazy.getDefaultInstance());
	}
	
	public RedisQueue(Type type,JedisPoolLazy poolLazy) {
		if( ! (type instanceof Class<?> ||  type instanceof ParameterizedType) ){
			throw new IllegalArgumentException("invalid type of 'type' :must be Class<?> or ParameterizedType");
		}
		this.type = type;
		this.queueName = type.toString();
		this.poolLazy = poolLazy;
	}

	public String getQueueName() {
		return queueName;
	}
	private void assertNotEmpty(String str,String name){
		if(null == str || str.isEmpty())
			throw new IllegalArgumentException(" '"+name+"' must not be null or empty");
	}
	
	public RedisQueue<E> setQueueName(String queueName) {
		assertNotEmpty(queueName,"queueName");
		this.queueName = queueName;
		return this;
	}

	public RedisQueue(Type type,Map<PropName, Object> props) {
		this(type,JedisPoolLazy.getInstance(props, true));
	}

	public RedisQueue(Type type,String host, int port, final String password, int database) {
		this(type, JedisPoolLazy.DEFAULT_CONFIG, host, port, password, database, Protocol.DEFAULT_TIMEOUT);
	}

	public RedisQueue(Type type, JedisPoolConfig jedisPoolConfig, URI uri, int timeout) {
		this(type,JedisPoolLazy.getInstance(jedisPoolConfig, uri, timeout));
	}

	public RedisQueue(Type type, JedisPoolConfig jedisPoolConfig, String host, int port, final String password,
			int database, int timeout) {
		this(type,JedisPoolLazy.getInstance(jedisPoolConfig, host, port, password, database, timeout));
	}

	public Type getType() {
		return type;
	}

	@Override
	public boolean offer(E e) {
		return this.offerLast(e);
	}

	@Override
	public E poll() {
		return pollFirst();
	}

	@Override
	public E peek() {
		return this.peekFirst();
	}

	@Override
	public Iterator<E> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		Jedis jedis = getJedis();
		try{
			return jedis.llen(queueName).intValue();
		}finally{
			this.releaseJedis();
		}
	}

	@Override
	public int remainingCapacity() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		return drainTo(c, Integer.MAX_VALUE);
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E removeFirst() {
        E x = pollFirst();
        if (x == null) throw new NoSuchElementException();
        return x;
	}

	@Override
	public E removeLast() {
        E x = pollLast();
        if (x == null) throw new NoSuchElementException();
        return x;
	}

	@Override
	public E pollFirst() {
		Jedis jedis = getJedis();
		try{
			return this.encoder.fromJson(jedis.lpop(this.queueName),this.getType());
		}finally{
			releaseJedis();
		}
	}

	@Override
	public E pollLast() {
		Jedis jedis = getJedis();
		try{
			return this.encoder.fromJson(jedis.rpop(this.queueName),this.getType());
		}finally{
			releaseJedis();
		}
	}

	@Override
	public E getFirst() {
        E x = peekFirst();
        if (x == null) throw new NoSuchElementException();
        return x;
	}

	@Override
	public E getLast() {
        E x = peekLast();
        if (x == null) throw new NoSuchElementException();
        return x;
	}

	@Override
	public E peekFirst() {
		Jedis jedis = getJedis();
		try{
			List<String> list = jedis.lrange(this.queueName,0,0);
			if(list.size()>0)
				return this.encoder.fromJson(list.get(0),this.getType());
			else return null;
		}finally{
			releaseJedis();
		}
	}

	@Override
	public E peekLast() {
		Jedis jedis = getJedis();
		try{
			List<String> list = jedis.lrange(this.queueName,-1,-1);
			if(list.size()>0)
				return this.encoder.fromJson(list.get(0),this.getType());
			else return null;
		}finally{
			releaseJedis();
		}
	}

	@Override
	public E pop() {
		return removeFirst();
	}

	@Override
	public Iterator<E> descendingIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addFirst(E e) {
		this.offerFirst(e);
		
	}

	@Override
	public void addLast(E e) {
		this.offerLast(e);
	}

	@Override
	public boolean offerFirst(E e) {
		Jedis jedis = getJedis();
		try{
			jedis.lpush(this.queueName, this.encoder.toJsonString(e));
			return true;
		}finally{
			releaseJedis();
		}
	}

	@Override
	public boolean offerLast(E e) {
		Jedis jedis = getJedis();
		try{
			jedis.rpush(this.queueName, this.encoder.toJsonString(e));
			return true;
		}finally{
			releaseJedis();
		}
	}

	@Override
	public void putFirst(E e) throws InterruptedException {
		offerFirst(e);
	}

	@Override
	public void putLast(E e) throws InterruptedException {
		offerLast(e);		
	}

	@Override
	public boolean offerFirst(E e, long timeout, TimeUnit unit) throws InterruptedException {
		return offerFirst(e);
	}

	@Override
	public boolean offerLast(E e, long timeout, TimeUnit unit) throws InterruptedException {
		return offerLast(e);
	}

	@Override
	public E takeFirst() throws InterruptedException {
		Jedis jedis = getJedis();
		try{
			List<String> list = jedis.blpop(Integer.MAX_VALUE, this.queueName);
			return this.encoder.fromJson(list.get(0),getType());
		}finally{
			releaseJedis();
		}
	}

	@Override
	public E takeLast() throws InterruptedException {
		Jedis jedis = getJedis();
		try{
			List<String> list = jedis.brpop(Integer.MAX_VALUE, this.queueName);
			return this.encoder.fromJson(list.get(0),getType());
		}finally{
			releaseJedis();
		}
	}

	@Override
	public E pollFirst(long timeout, TimeUnit unit) throws InterruptedException {
		Jedis jedis = getJedis();
		try{			
			List<String> list = jedis.blpop((int)TimeUnit.SECONDS.convert(timeout, unit), this.queueName);
			return this.encoder.fromJson(list.get(0),getType());
		}finally{
			releaseJedis();
		}
	}

	@Override
	public E pollLast(long timeout, TimeUnit unit) throws InterruptedException {
		Jedis jedis = getJedis();
		try{			
			List<String> list = jedis.brpop((int)TimeUnit.SECONDS.convert(timeout, unit), this.queueName);
			return this.encoder.fromJson(list.get(0),getType());
		}finally{
			releaseJedis();
		}
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void put(E e) throws InterruptedException {
		putLast(e);		
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		return offerLast(e, timeout, unit);
	}

	@Override
	public E take() throws InterruptedException {
		return takeFirst();
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		return pollFirst(timeout, unit);
	}

	@Override
	public void push(E e) {
		addFirst(e);		
	}

}
