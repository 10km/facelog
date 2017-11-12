package gu.simplemq;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkArgument;

import gu.simplemq.json.BaseJsonEncoder;
import gu.simplemq.utils.TypeUtils;

/**
 * KV表抽象类，K为String类型
 * @author guyadong
 *
 * @param <V> 值对象数据类型 
 */
public abstract class AbstractTable<V>{
	protected static  class BreakException extends RuntimeException{
		private static final long serialVersionUID = 1L;		
	}
	public static interface Filter<V>{
		boolean run(String key, V value) throws BreakException;
	}
	protected final Filter<V> alwaysTrue=new Filter<V>(){
		@Override
		public boolean run(String key, V value) throws BreakException {
			return true;
		}};
	private final Type type;
	protected final boolean isJavaBean ;
	protected BaseJsonEncoder encoder = BaseJsonEncoder.getEncoder();
	private IKeyHelper<V> keyHelper;
	private List<String>filedNames = null;
	protected KeyExpire keyExpire =new KeyExpire();
	public AbstractTable(Type type) {
		super();
		if( ! (type instanceof Class<?> ||  type instanceof ParameterizedType) ){
			throw new IllegalArgumentException("invalid type of 'type' :must be Class<?> or ParameterizedType");
		}
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
		return this.keyHelper.returnKey(v);
	}

	private void assertJavaBean(){
		if(!isJavaBean){
			throw new UnsupportedOperationException("because of not javabean,");
		}
	}
	protected abstract V doGet(String key);

	public V get(String key){
		checkArgument(!Strings.isNullOrEmpty(key),"key is null or empty");
		return doGet(key);
	}
	
	public Map<String, V> get(String... keys){
		@SuppressWarnings("unchecked")
		HashMap<String, V> m = (HashMap<String, V>) new HashMap<String,Object>(16);
		for(String key:keys){
			if(!Strings.isNullOrEmpty(key)){
				m.put(key, doGet(key));
			}
		}
		return m;
	}
	
	protected abstract void doSet(String key, V value, boolean nx);
	
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
	
	protected abstract void doSetField(String key, String field, Object value, boolean nx);
	
	public  void setField(String key, String field, Object value, boolean nx){
		assertJavaBean();
		checkArgument(!Strings.isNullOrEmpty(key),"key is null or empty");
		checkArgument(!Strings.isNullOrEmpty(field),"field is null or empty");
		doSetField(key,field,value, nx);
	}
	
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
		if(null == fields || 0== fields.length){
			fields = json.keySet().toArray(new String[0]);
		}
		if(1 == fields.length){
			checkArgument(!Strings.isNullOrEmpty(fields[0]),"fields[0] is null or empty");

			doSetField(key,fields[0],json.get(fields[0]), nx);
		}
		else{
			for (String field : fields) {
				if (null == field || field.isEmpty()){
					continue;
				}
				if (!json.containsKey(field)){
					json.remove(field);
				}
			}
			doSetFields(key,json, nx);
		}
	}
	
	protected abstract void doSetFields(String key, Map<String, String> fieldsValues, boolean nx) ;
	
	public void setFields(String key, Map<String,Object>fieldsValues, boolean nx){
		assertJavaBean();
		checkArgument(!Strings.isNullOrEmpty(key),"key is null or empty");
		if(null == fieldsValues || fieldsValues.isEmpty()){
			return;
		}
		HashMap<String, String> fields = new HashMap<String,String>();
		for(Entry<String, Object> entry:fieldsValues.entrySet()){
			Object value = entry.getValue();
			fields.put(key, null == value ? null : this.encoder.toJsonString(value));
		}
		doSetFields(key,fields,nx);
	}
	
	protected abstract  Map<String,Object> doGetFields(String key,Map<String,Type> types);
	
	public Map<String,Object> getFields(String key,Map<String,Type> types){
		assertJavaBean();
		checkArgument(!Strings.isNullOrEmpty(key),"key is null or empty");
		return doGetFields(key,types);		
	}
	
	public Map<String, Object> getFields(String key,String ...fields){
		assertJavaBean();
		checkArgument(!Strings.isNullOrEmpty(key),"key is null or empty");
		LinkedHashMap<String, Type> types = new LinkedHashMap<String,Type>();
		if(null != fields){
			// 去除为空或 null 的字段名
			for(String field : fields){
				if(null == field || 0 == field.length()){
					continue;
				}
				types.put(field, null);
			}
		}
		return doGetFields(key,types);
	}
	
	@SuppressWarnings("unchecked")
	public <T>T getField(String key,String field,Type type){
		assertJavaBean();
		checkArgument(!Strings.isNullOrEmpty(key),"key is null or empty");
		checkArgument(!Strings.isNullOrEmpty(field),"field is null or empty");
		LinkedHashMap<String, Type> types = new LinkedHashMap<String,Type>();
		types.put(field, type);
		return (T) doGetFields(key,types).get(field);
	}
	
	public <T>T getField(String key,String field,Class<T> clazz){
		return getField(key,field,(Type)clazz);
	}
	
	public <T>T getField(String key,String field){
		return getField(key,field,(Type)null);
	}
	
	protected abstract int doRemove(String... keys);
	
	public int remove(String... keys){
		if(null == keys || 0 == keys.length){
			return 0;
		}
		ArrayList<String> list = new ArrayList<String>(keys.length);
		for(String key:keys){
			if(null == key || 0 == key.length()){
				continue;
			}
			list.add(key);
		}
		return list.isEmpty()?0:doRemove(list.toArray(new String[list.size()]));
	}
	
	public int remove(@SuppressWarnings("unchecked") V... values){
		if(null == values){
			return 0;
		}
		ArrayList<String> list = new ArrayList<String>(values.length);
		for(V value:values){
			if(null == value){
				continue;
			}
			list.add(this.keyHelper(value));
		}	
		return remove(list.toArray(new String[list.size()]));
	}
	
	protected abstract Set<String> doKeys(String pattern) ;
	
	public Set<String> keys(String pattern) {
		if(null == pattern || pattern.isEmpty()){
			pattern="*";
		}
		return doKeys(pattern);
	}
	
	protected abstract void doSet(Map<String, V> m, boolean nx) ;
	
	public void set(Map<String, V> m, boolean nx){
		if(null == m || m.isEmpty()) {
			return ;
		}
		doSet(m,nx);
	}
	
	public int size(String pattern) {
		return keys(pattern).size();
	}

	public boolean isEmpty() {
		return 0 == size(null);
	}

	public boolean containsKey(String key) {
		return get(key)!=null;
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
		final AtomicBoolean b=new AtomicBoolean(false); 
		foreach(pattern,new Filter<V>(){
			@Override
			public boolean run(String key, V value) throws BreakException {
				if(v.equals(value)){
					b.set(true);
					throw new BreakException();
				}
				return false;
			}});
		return b.get();
	}
	
	public Map<String, V> values(final String pattern, Filter<V> filter) {
			final HashMap<String, V> map = new HashMap<String,V>(); 
			final Filter<V> f = null == filter ?alwaysTrue : filter;
			foreach(pattern,new Filter<V>(){
				@Override
				public boolean run(String key, V value) throws BreakException {
					if(f.run(key, value)){
						map.put(key, value);
					}
					return false;
				}});
			return map;
	}
	
	public int foreach(String pattern, Filter<V> filter) {
		int count=0;
		try {
			for (String key : keys(pattern)) {
				V value = get(key);
				if(null != value && filter.run(key, value)){
					++count;
				}
			}
		} catch (BreakException e) {}
		return count;
	}
	
	public int removeKeys(String pattern, final Filter<V> filter) {
		final AtomicInteger count=new AtomicInteger(0); 
		foreach(pattern,new Filter<V>(){
			@Override
			public boolean run(String key, V value) throws BreakException {
				if(null == filter|| filter.run(key, value)){
					count.addAndGet(remove(key))	;
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
		HashMap<String, V> keysValues = new HashMap<String,V>();
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

	public IKeyHelper<V> getKeyHelper() {
		return keyHelper;
	}

	public void setKeyHelper(IKeyHelper<V> keyHelper) {
		this.keyHelper = keyHelper;
	}

	protected abstract List<String> doGetFieldNames() ;
	
	public List<String> getFieldNames() {
		this.assertJavaBean();
		if(null == filedNames){
			synchronized(this){
				if(null == filedNames){
					filedNames= doGetFieldNames();		
				}
			}
		}			
		return filedNames;
	}

	public void setExpire(long time, TimeUnit timeUnit) {
		keyExpire.setExpire(time, timeUnit);
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

	public void expire(String key, long time, TimeUnit timeUnit) {
		keyExpire.expire(key, time, timeUnit);
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

	public void expire(V value, long time, TimeUnit timeUnit) {
		keyExpire.expire(keyHelper(value), time, timeUnit);
	}

	public void expire(V value, java.util.Date date) {
		keyExpire.expire(keyHelper(value), date);
	}

	public void expire(V value) {
		keyExpire.expire(keyHelper(value));
	}
}
