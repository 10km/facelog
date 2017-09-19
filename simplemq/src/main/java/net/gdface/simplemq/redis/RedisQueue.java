package net.gdface.simplemq.redis;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import net.gdface.simplemq.json.JsonEncoder;
import net.gdface.simplemq.utils.CommonUtils;
import net.gdface.simplemq.utils.Judge;
import redis.clients.jedis.Jedis;

/**
 * 
 * 封装 redis list 为阻塞式双向队列 {@link BlockingDeque}
 * 
 * @author guyadong
 *
 * @param <E> 队列中的元素类型
 */
public class RedisQueue<E> extends AbstractQueue<E>implements IRedisQueue<E> {
	private final Type type;
	private JsonEncoder encoder = JsonEncoder.getEncoder();
	/** 队列名(key) */
	private String queueName;
	private final JedisPoolLazy poolLazy;
	@Override
	public JedisPoolLazy getPoolLazy() {
		return poolLazy;
	}

	@Override
	public String getQueueName() {
		return queueName;
	}

	public RedisQueue<E> setQueueName(String queueName) {
		if( ! Judge.isEmpty(queueName))
			this.queueName = RedisComponentType.Queue.check(poolLazy,queueName);
		return this;
	}

    public RedisQueue(Type type) {
		this(type,JedisPoolLazy.getDefaultInstance());
	}
	
	public RedisQueue(Type type,JedisPoolLazy poolLazy) {
		if( ! (type instanceof Class<?> ||  type instanceof ParameterizedType) ){
			throw new IllegalArgumentException("invalid type of 'type' :must be Class<?> or ParameterizedType");
		}
		this.type = type;
		this.poolLazy = poolLazy;
		this.setQueueName(type.toString());
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
		Jedis jedis = poolLazy.apply();
		try{
			return jedis.llen(queueName).intValue();
		}finally{
			poolLazy.free();
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
		Jedis jedis = poolLazy.apply();
		try{
			return this.encoder.fromJson(jedis.lpop(this.queueName),this.getType());
		}finally{
			poolLazy.free();
		}
	}

	@Override
	public E pollLast() {
		Jedis jedis = poolLazy.apply();
		try{
			return this.encoder.fromJson(jedis.rpop(this.queueName),this.getType());
		}finally{
			poolLazy.free();
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
		Jedis jedis = poolLazy.apply();
		try{
			List<String> list = jedis.lrange(this.queueName,0,0);
			if(list.size()>0)
				return this.encoder.fromJson(list.get(0),this.getType());
			else return null;
		}finally{
			poolLazy.free();
		}
	}

	@Override
	public E peekLast() {
		Jedis jedis = poolLazy.apply();
		try{
			List<String> list = jedis.lrange(this.queueName,-1,-1);
			if(list.size()>0)
				return this.encoder.fromJson(list.get(0),this.getType());
			else return null;
		}finally{
			poolLazy.free();
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
		if (e == null) throw new NullPointerException();
		Jedis jedis = poolLazy.apply();
		try{
			jedis.lpush(this.queueName, this.encoder.toJsonString(e));
			return true;
		}finally{
			poolLazy.free();
		}
	}
	
	@Override
	public boolean offerLast(E e) {
		if (e == null) throw new NullPointerException();
		Jedis jedis = poolLazy.apply();
		try{
			jedis.rpush(this.queueName, this.encoder.toJsonString(e));
			return true;
		}finally{
			poolLazy.free();
		}
	}

	/**
	 * 向队列添加一组对象
	 * @param offerLast 是否添加到队列末尾
	 * @param array 对象数组
	 * @return
	 */
	public int offer(boolean offerLast,@SuppressWarnings("unchecked") E... array) {
		List<E> list = CommonUtils.cleanNullAsList(array);
		if(list.isEmpty())return 0;
		String[] strings = new String[list.size()];
		for(int i =0;i<strings.length;++i){
			strings[i] = this.encoder.toJsonString(list.get(i));
		}
		Jedis jedis = poolLazy.apply();
		try{
			if(offerLast)
				return jedis.rpush(this.queueName, strings).intValue();
			else
				return jedis.lpush(this.queueName, strings).intValue();
		}finally{
			poolLazy.free();
		}
	}
	/** @see #offer(boolean, Object...) */
	public int offerFirst(@SuppressWarnings("unchecked") E... e) {
		return offer(false,e);
	}
	/** @see #offer(boolean, Object...) */
	public int offerLast(@SuppressWarnings("unchecked") E... e) {
		return offer(true,e);
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
		Jedis jedis = poolLazy.apply();
		try{
			List<String> list = jedis.blpop(Integer.MAX_VALUE, this.queueName);
			return this.encoder.fromJson(list.get(0),getType());
		}finally{
			poolLazy.free();
		}
	}

	@Override
	public E takeLast() throws InterruptedException {
		Jedis jedis = poolLazy.apply();
		try{
			List<String> list = jedis.brpop(Integer.MAX_VALUE, this.queueName);
			return this.encoder.fromJson(list.get(0),getType());
		}finally{
			poolLazy.free();
		}
	}

	@Override
	public E pollFirst(long timeout, TimeUnit unit) throws InterruptedException {
		Jedis jedis = poolLazy.apply();
		try{			
			List<String> list = jedis.blpop((int)TimeUnit.SECONDS.convert(timeout, unit), this.queueName);
			if(list.isEmpty())return null;
			return this.encoder.fromJson(list.get(1),getType());
		}finally{
			poolLazy.free();
		}
	}

	@Override
	public E pollLast(long timeout, TimeUnit unit) throws InterruptedException {
		Jedis jedis = poolLazy.apply();
		try{			
			List<String> list = jedis.brpop((int)TimeUnit.SECONDS.convert(timeout, unit), this.queueName);
			if(list.isEmpty())return null;
			return this.encoder.fromJson(list.get(1),getType());
		}finally{
			poolLazy.free();
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

	@Override
	public BlockingQueue<E> getQueue() {
		return this;
	}

}
