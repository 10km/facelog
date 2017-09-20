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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import gu.simplemq.json.JsonEncoder;
import gu.simplemq.utils.Assert;
import gu.simplemq.utils.TypeUtils;

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
	protected JsonEncoder encoder = JsonEncoder.getEncoder();
	protected IKeyHelper<V> keyHelper;
	private List<String>filedNames = null;

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
		if(null == this.keyHelper)
			throw new UnsupportedOperationException("because of null keyHelper");
		return this.keyHelper.returnKey(v);
	}

	private void assertJavaBean(){
		if(!isJavaBean)
			throw new UnsupportedOperationException("because of not javabean,");
	}
	protected abstract V _get(String key);

	public V get(String key){
		Assert.notEmpty(key,"key");
		return _get(key);
	}
	
	protected abstract void _set(String key, V value, boolean nx);
	
	public void set(String key, V value, boolean nx){
		Assert.notEmpty(key,"key");
		if(null == value ){
			if(!nx)
				remove(key);
		}else{
			if(isJavaBean)
				setFields(nx, key, value);
			else
				_set(key,value,nx);
		}
	}
	
	public void set(V value,boolean nx){
		if(null ==value)return;
		set(keyHelper(value),value,nx);
	}
	
	protected abstract void _setField(String key, String field, Object value, boolean nx);
	
	public  void setField(String key, String field, Object value, boolean nx){
		assertJavaBean();
		Assert.notEmpty(key,"key");
		Assert.notEmpty(field,"field");
		_setField(key,field,value, nx);
	}
	
	public void setFields(boolean nx,String key,V obj, String ...fields){
		assertJavaBean();
		Assert.notEmpty(key,"key");
		if(null == obj){
			if(!nx)
				this.remove(key);
			return ;
		}
		Map<String, String> json = this.encoder.toJsonMap(obj);
		if(null == fields || 0== fields.length)
			fields = json.keySet().toArray(new String[0]);
		if(1 == fields.length){
			Assert.notEmpty(fields[0],"fields[0]");
			_setField(key,fields[0],json.get(fields[0]), nx);
		}
		else{
			for (String field : fields) {
				if (null == field || field.isEmpty())
					continue;
				if (!json.containsKey(field))
					json.remove(field);
			}
			_setFields(key,json, nx);
		}
	}
	
	protected abstract void _setFields(String key, Map<String, String> fieldsValues, boolean nx) ;
	
	public void setFields(String key, Map<String,Object>fieldsValues, boolean nx){
		assertJavaBean();
		Assert.notEmpty(key,"key");
		if(null == fieldsValues || fieldsValues.isEmpty())return;
		HashMap<String, String> fields = new HashMap<String,String>();
		for(Entry<String, Object> entry:fieldsValues.entrySet()){
			Object value = entry.getValue();
			fields.put(key, null == value ? null : this.encoder.toJsonString(value));
		}
		_setFields(key,fields,nx);
	}
	
	protected abstract  Map<String,Object> _getFields(String key,Map<String,Type> types);
	
	public Map<String,Object> getFields(String key,Map<String,Type> types){
		assertJavaBean();
		Assert.notEmpty(key,"key");
		return _getFields(key,types);		
	}
	
	public Map<String, Object> getFields(String key,String ...fields){
		assertJavaBean();
		Assert.notEmpty(key,"key");
		LinkedHashMap<String, Type> types = new LinkedHashMap<String,Type>();
		if(null != fields){
			// 去除为空或 null 的字段名
			for(String field : fields){
				if(null == field || 0 == field.length())continue;
				types.put(field, null);
			}
		}
		return _getFields(key,types);
	}
	
	@SuppressWarnings("unchecked")
	public <T>T getField(String key,String field,Type type){
		assertJavaBean();
		Assert.notEmpty(key,"key");
		Assert.notEmpty(field,"field");
		LinkedHashMap<String, Type> types = new LinkedHashMap<String,Type>();
		types.put(field, type);
		return (T) _getFields(key,types).get(field);
	}
	
	public <T>T getField(String key,String field,Class<T> clazz){
		return getField(key,field,(Type)clazz);
	}
	
	public <T>T getField(String key,String field){
		return getField(key,field,(Type)null);
	}
	
	protected abstract int _remove(String... keys);
	
	public int remove(String... keys){
		if(null == keys || 0 == keys.length)
			return 0;
		ArrayList<String> list = new ArrayList<String>(keys.length);
		for(String key:keys){
			if(null == key || 0 == key.length())continue;
			list.add(key);
		}
		return list.isEmpty()?0:_remove(list.toArray(new String[list.size()]));
	}
	
	public int remove(@SuppressWarnings("unchecked") V... values){
		if(null == values)
			return 0;
		ArrayList<String> list = new ArrayList<String>(values.length);
		for(V value:values){
			if(null == value)continue;
			list.add(this.keyHelper(value));
		}	
		return remove(list.toArray(new String[list.size()]));
	}
	
	protected abstract Set<String> _keys(String pattern) ;
	
	public Set<String> keys(String pattern) {
		if(null == pattern || pattern.isEmpty())
			pattern="*";
		return _keys(pattern);
	}
	
	protected abstract void _set(Map<String, V> m, boolean nx) ;
	
	public void set(Map<String, V> m, boolean nx){
		if(null == m || m.isEmpty()) return ;
		_set(m,nx);
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

	public boolean containsValue(final String pattern,final V v) {
		if(null != v){
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
		return false;
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
				if(null != value && filter.run(key, value))
					++count;
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
		if(null == c || c.isEmpty()) return ;
		if(null == this.keyHelper)
			throw new UnsupportedOperationException("because of null keyHelper");
		HashMap<String, V> keysValues = new HashMap<String,V>();
		for(V value:c)	{
			keysValues.put(this.keyHelper(value), value);
		}
		set(keysValues,false);
	}
	
	public void set(boolean nx,@SuppressWarnings("unchecked") V ...array){
		if(null == array)return ;
		set(Arrays.asList(array), nx);
	}
	public JsonEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(JsonEncoder encoder) {
		this.encoder = encoder;
	}

	public IKeyHelper<V> getKeyHelper() {
		return keyHelper;
	}

	public void setKeyHelper(IKeyHelper<V> keyHelper) {
		this.keyHelper = keyHelper;
	}

	protected abstract List<String> _getFieldNames() ;
	
	public List<String> getFieldNames() {
		this.assertJavaBean();
		if(null == filedNames){
			synchronized(this){
				if(null == filedNames){
					filedNames= _getFieldNames();		
				}
			}
		}			
		return filedNames;
	}
}
