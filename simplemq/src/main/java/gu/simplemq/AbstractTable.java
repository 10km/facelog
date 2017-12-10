package gu.simplemq;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import gu.simplemq.json.BaseJsonEncoder;
import gu.simplemq.utils.CommonUtils;
import gu.simplemq.utils.ILazyInitVariable;
import gu.simplemq.utils.TypeUtils;

/**
 * KV表抽象类，K为String类型
 * @author guyadong
 *
 * @param <V> 值对象数据类型 
 */
public abstract class AbstractTable<V>{
	protected final Predicate<String> alwaysTrue=new Predicate<String>(){
		@Override
		public boolean apply(String key) {
			return true;
		}};
	private final Type type;
	protected final boolean isJavaBean ;
	protected BaseJsonEncoder encoder = BaseJsonEncoder.getEncoder();
	private Function<V,String> keyHelper;
	private final ILazyInitVariable<List<String>>filedNames = ILazyInitVariable.Factory.makeInstance(new Supplier<List<String>>(){
		@Override
		public List<String> get() {
			return doGetFieldNames();
		}});
	protected KeyExpire keyExpire =new KeyExpire();
	public AbstractTable(Type type) {
		super();
		checkArgument(type instanceof Class<?> ||  type instanceof ParameterizedType,
				"invalid type of 'type' :must be Class<?> or ParameterizedType");
		this.type = type;
		this.isJavaBean = TypeUtils.isJavaBean(type);
	}

	public Type getType() {
		return type;
	}
	
	private String keyHelper(V v){
		if(null == this.keyHelper){
			throw new UnsupportedOperationException("because of null keyHelper");
		}
		return this.keyHelper.apply(v);
	}

	private void assertJavaBean(){
		if(!isJavaBean){
			throw new UnsupportedOperationException("because of not javabean,");
		}
	}
	/**
	 * Get the value of the specified key. If the key does not exist null is returned
	 * @param key must not be {@code null} or empty
	 * @return
	 */
	protected abstract V doGet(String key);
	/**
	 * Get the value of the specified key. If the key does not exist null is returned
	 * @param key must not be {@code null} or empty
	 * @return
	 * @throws IllegalArgumentException  if key is {@code null} or empty
	 */
	public V get(String key){
		checkArgument(!Strings.isNullOrEmpty(key),"key is null or empty");
		return doGet(key);
	}
	
	/**
	 *  see also {@link #get(String)}
	 * @param keys
	 * @return
	 */
	public Map<String, V> get(String... keys){
		LinkedHashMap<String, V> m = new LinkedHashMap<String,V>(16);
		V value;
		for(String key:CommonUtils.cleanEmptyAsList(keys)){
			if(!Strings.isNullOrEmpty(key) && null != (value = doGet(key))){
				m.put(key, value);
			}
		}
		return m;
	}
	
	/**
	 * Set the string value as value of the key
	 * @param key
	 * @param value
	 * @param nx
	 */
	protected abstract void doSet(String key, V value, boolean nx);
	/**
	 * Set the string value as value of the key
	 * @param key
	 * @param value
	 * @param nx
	 * @throws IllegalArgumentException  if key is {@code null} or empty
	 */
	public void set(String key, V value, boolean nx){
		checkArgument(!Strings.isNullOrEmpty(key),"key is null or empty");
		if(null == value ){
			if(!nx){
				remove(key);
			}
		}else{
			if(isJavaBean){
				setFields(nx, key, value);
			}
			else{
				doSet(key,value,nx);
			}
		}
	}
	
	public void set(V value,boolean nx){
		if(null ==value){
			return;
		}
		set(keyHelper(value),value,nx);
	}
	
	/**
	 * Set the specified hash field to the specified value.
     * If key does not exist, a new key holding a hash is created.<br>
	 * @param key
	 * @param field
	 * @param value
	 * @param nx
	 * @throws IllegalArgumentException key or field is {@code null} or empty
	 */
	protected abstract void doSetField(String key, String field, Object value, boolean nx);
	
	/** 
	 * see also {@link #doSetField(String, String, Object, boolean)} <br>
     * V must be a java bean,otherwise throw exception
	 */
	public  void setField(String key, String field, Object value, boolean nx){
		assertJavaBean();
		checkArgument(!Strings.isNullOrEmpty(key),"key is null or empty");
		checkArgument(!Strings.isNullOrEmpty(field),"field is null or empty");
		doSetField(key,field,value, nx);
	}
	
	/**
     * V must be a java bean,otherwise throw exception<br>
	 * see also {@link #setField(String, String, Object, boolean)}
	 * @param nx
	 * @param key
	 * @param obj
	 * @param fields
	 */
	public void setFields(boolean nx,String key,V obj, String ...fields){
		assertJavaBean();
		checkArgument(!Strings.isNullOrEmpty(key),"key is null or empty");
		if(null == obj){
			if(!nx){
				this.remove(key);
			}
			return ;
		}
		Map<String, String> json = this.encoder.toJsonMap(obj);
		fields = CommonUtils.cleanEmpty(fields);
		if(1 == fields.length){
			checkArgument(!Strings.isNullOrEmpty(fields[0]),"fields[0] is null or empty");

			doSetField(key,fields[0],json.get(fields[0]), nx);
		}
		else{
			for (String field : fields) {
				if (!json.containsKey(field)){
					json.remove(field);
				}
			}
			doSetFields(key,json, nx);
		}
	}
	
	/**
	 * Set the specified hash field to the specified field values ({@code fieldsValues}). 
	 * @param key
	 * @param fieldsValues
	 * @param nx
	 */
	protected abstract void doSetFields(String key, Map<String, String> fieldsValues, boolean nx) ;
	
	/**
	 * see also {@link #doSetFields(String, Map, boolean)}<br>
     * V must be a java bean,otherwise throw exception<br>
	 * @param key
	 * @param fieldsValues
	 * @param nx
	 */
	public void setFields(String key, Map<String,Object>fieldsValues, boolean nx){
		assertJavaBean();
		checkArgument(!Strings.isNullOrEmpty(key),"key is null or empty");
		if(null == fieldsValues || fieldsValues.isEmpty()){
			return;
		}
		HashMap<String, String> fields = new HashMap<String,String>(16);
		for(Entry<String, Object> entry:fieldsValues.entrySet()){
			Object value = entry.getValue();
			fields.put(key, null == value ? null : this.encoder.toJsonString(value));
		}
		doSetFields(key,fields,nx);
	}
	
	/**
	 * Retrieve the values associated to the specified fields.
	 * @param key
	 * @param types
	 * @return
	 */
	protected abstract  Map<String,Object> doGetFields(String key,Map<String,Type> types);
	
	/**
	 * Retrieve the values associated to the specified fields.<br>
     * V must be a java bean,otherwise throw exception<br>
	 * @param key
	 * @param types field types,if {@code null} or empty ,retrieve all fields
	 * @return
	 * @throw IllegalArgumentException key is {@code null} or empty
	 */
	public Map<String,Object> getFields(String key,Map<String,Type> types){
		assertJavaBean();
		checkArgument(!Strings.isNullOrEmpty(key),"key is null or empty");
		return doGetFields(key,types);		
	}
	
	/**
	 * see also {@link #doGetFields(String, Map)}
     * V must be a java bean,otherwise throw exception<br>
	 * @param key
	 * @param fields field list retrieved,if {@code null} or empty ,retrieve all fields
	 * @return
	 */
	public Map<String, Object> getFields(String key,String ...fields){
		assertJavaBean();
		checkArgument(!Strings.isNullOrEmpty(key),"key is null or empty");
		LinkedHashMap<String, Type> types = new LinkedHashMap<String,Type>();
		// 去除为空或 null 的字段名
		for(String field : CommonUtils.cleanEmpty(fields)){
			types.put(field, null);
		}
		return doGetFields(key,types);
	}
	
	/**
	 * 
	 * see also {@link #doGetFields(String, Map)}
     * V must be a java bean,otherwise throw exception<br>
	 * @param key
	 * @param field
	 * @param type field's type,if {@code null} return {@link Object} value
	 * @return
	 * @throw IllegalArgumentException key or field is {@code null} or empty
	 */
	@SuppressWarnings("unchecked")
	public <T>T getField(String key,String field,Type type){
		assertJavaBean();
		checkArgument(!Strings.isNullOrEmpty(key),"key is null or empty");
		checkArgument(!Strings.isNullOrEmpty(field),"field is null or empty");
		return (T) doGetFields(key,Collections.singletonMap(field, type)).get(field);
	}
	
	/** see also {@link #getField(String, String, Class)} */
	public <T>T getField(String key,String field,Class<T> clazz){
		return getField(key,field,(Type)clazz);
	}
	/** see also {@link #getField(String, String, Type)} */
	public <T>T getField(String key,String field){
		return getField(key,field,(Type)null);
	}
	
	/**
	 * Remove the specified keys. If a given key does not exist no operation is performed for this key.
	 * @param keys
	 * @return  the number of keys removed. 
	 */
	protected abstract int doRemove(String... keys);
	
	/**
	 * Remove the specified keys. If a given key does not exist no operation is performed for this key.
	 * @param keys
	 * @return
	 */
	public int remove(String... keys){
		List<String> list = CommonUtils.cleanEmptyAsList(keys);
		return list.isEmpty()
					? 0
					: doRemove(list.toArray(new String[list.size()]));
	}
	
	/**
	 * see also {@link #remove(String...)}
	 * @param values
	 * @return
	 */
	public int remove(@SuppressWarnings("unchecked") V... values){
		List<String> keys = Lists.transform(CommonUtils.cleanNullAsList(values),new Function<V,String>(){
			@Override
			public String apply(V input) {
				return keyHelper(input);
			}});
		return remove(keys.toArray(new String[keys.size()]));
	}
	/**
	 * 返回满足指定匹配模式的所有key
	 * @param pattern 匹配模式,不可为空或{@code null}
	 * @return
	 */
	protected abstract Set<String> doKeys(String pattern) ;
	
	/**
	 * 返回满足指定匹配模式的所有key
	 * @param pattern 匹配模式
	 * @return
	 */
	public Set<String> keys(String pattern) {
		if(Strings.isNullOrEmpty(pattern)){
			pattern="*";
		}
		return doKeys(pattern);
	}
	
	/**
	 * save {@code m} specified records  
	 * @param m
	 * @param nx
	 */
	protected abstract void doSet(Map<String, V> m, boolean nx) ;
	
	/**
	 * 保存 {@code m}指定的一组记录
	 * @param m
	 * @param nx
	 */
	public void set(Map<String, V> m, boolean nx){
		if(null == m || m.isEmpty()) {
			return ;
		}
		doSet(m,nx);
	}
	
	/**
	 * 返回满足指定匹配模式的所有key的数目
	 * @param pattern
	 * @return
	 * @see #keys(String)
	 */
	public int size(String pattern) {
		return keys(pattern).size();
	}

	public boolean isEmpty() {
		return 0 == size(null);
	}

	/**
	 * 判断是否存在指定的key
	 * @param key {@code null} or empty 返回 {@code false}
	 * @return 
	 */
	public boolean containsKey(String key) {
		return Strings.isNullOrEmpty(key) ? false : get(key)!=null;
	}

	/**
	 * 在表中查找指定的对象(V),如果找到(至少一个)返回true,否则返回false.
	 * @param <V> 必须重写 equals()方法,提供对象比较能力,否则不能返回正确结果
	 * @param pattern
	 * @param v 
	 * @return
	 */
	public boolean containsValue(final String pattern,final V v) {
		if(null == v){
			return false;
		}
		return Iterators.tryFind(keys(pattern).iterator(), new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				return v.equals(get(input));
			}
		}).isPresent();
	}
	
	/**
	 * 返回满足指定的过滤器{@code filter}条件的所有记录
	 * @param pattern
	 * @param filter
	 * @return
	 */
	public Map<String, V> values(final String pattern, Predicate<String> filter) {
		return Maps.asMap(Sets.filter(keys(pattern), checkNotNull(filter)), new Function<String, V>() {
			@Override
			public V apply(String input) {
				return get(input);
			}
		});
	}
	
	public int foreach(String pattern, Predicate<String> filter) {
		int count=0;
		for (String key : keys(pattern)) {
			if(checkNotNull(filter).apply(key)){
				++count;
			}
		}
		return count;
	}
	
	public int removeKeys(String pattern, final Predicate<String> filter) {
		checkArgument(null != filter,"filter is null");
		final AtomicInteger count=new AtomicInteger(0); 
		foreach(pattern,
				new Predicate<String>() {
					@Override
					public boolean apply(String key) {
						if (null == filter || filter.apply(key)) {
							count.addAndGet(remove(key));
						}
						return false;
					}});
		return count.get();
	}
	
	public void set(Collection<V> c, boolean nx){
		if(null == c || c.isEmpty()) {
			return ;
		}
		if(null == this.keyHelper){
			throw new UnsupportedOperationException("because of null keyHelper");
		}
		HashMap<String, V> keysValues = new HashMap<String,V>(16);
		for(V value:c)	{
			keysValues.put(this.keyHelper(value), value);
		}
		set(keysValues,false);
	}
	
	public void set(boolean nx,@SuppressWarnings("unchecked") V ...array){
		if(null == array){
			return ;
		}
		set(Arrays.asList(array), nx);
	}
	public BaseJsonEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(BaseJsonEncoder encoder) {
		this.encoder = encoder;
	}

	public Function<V,String> getKeyHelper() {
		return keyHelper;
	}

	public void setKeyHelper(Function<V,String> keyHelper) {
		this.keyHelper = keyHelper;
	}

	/**
	 * 返回所有字段名列表
	 * @return
	 */
	protected abstract List<String> doGetFieldNames() ;
	
	/**
	 * 返回所有字段名列表
	 * @return
	 */
	public List<String> getFieldNames() {
		this.assertJavaBean();
		return filedNames.get();
	}

	public void setExpire(long duration, TimeUnit unit) {
		keyExpire.setExpire(duration, unit);
	}

	public void setExpire(java.util.Date date) {
		keyExpire.setExpire(date);
	}

	public void setExpire(boolean timestamp, long timeMills) {
		keyExpire.setExpire(timestamp, timeMills);
	}

	public void expire(String key, long timeMills, boolean timestamp) {
		keyExpire.expire(key, timeMills, timestamp);
	}

	public void expire(String key, long duration, TimeUnit unit) {
		keyExpire.expire(key, duration, unit);
	}

	public void expire(String key, java.util.Date date) {
		keyExpire.expire(key, date);
	}

	public void expire(String key) {
		keyExpire.expire(key);
	}

	public void expire(V value, long timeMills, boolean timestamp) {
		keyExpire.expire(keyHelper(value), timeMills, timestamp);
	}

	public void expire(V value, long duration, TimeUnit unit) {
		keyExpire.expire(keyHelper(value), duration, unit);
	}

	public void expire(V value, java.util.Date date) {
		keyExpire.expire(keyHelper(value), date);
	}

	public void expire(V value) {
		keyExpire.expire(keyHelper(value));
	}
}
