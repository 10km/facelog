package net.gdface.facelog.message;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CommonUtils {

	private CommonUtils() {}

	/**
	 * 清除数组中的null或空字符串
	 * @param strings
	 * @return 返回不含null或空字符串的数组对象,objects为null或空时返回数组长度为0
	 */
	public static  final String[] cleanEmpty(String... strings) {
		return cleanEmptyAsList(strings).toArray(new String[0]);
	}
	
	/**
	 * 清除数组中的null或空字符串
	 * @param strings
	 * @return 返回不含null或空字符串的{@link List}对象,objects为null或空时 {@link List}对象长度为0
	 */
	public static  final List<String> cleanEmptyAsList(String... strings) {
		if (null == strings || 0 == strings.length)return new ArrayList<String>(0);
		ArrayList<String> list = new ArrayList<String>(strings.length);
		for( String element: strings){
			if(null == element || element.isEmpty() )continue;
			list.add(element);
		}
		return list;
	}
	
	/**
	 * 清除数组中的null元素
	 * @param objects
	 * @return 返回不含null元素的数组对象,objects为null或空时返回objects
	 */
	@SuppressWarnings("unchecked")
	public static final <T>  T[] cleanNull(T...objects) {
		if(Judge.isEmpty(objects))return objects;
		List<T> list = cleanNullAsList(objects);
		return list.toArray((T[]) Array.newInstance(objects.getClass().getComponentType(), list.size()));
	}
	
	/**
	 * 清除数组中的null元素
	 * @param objects 
	 * @return 返回不含null元素的{@link List}对象,objects为null或空时 {@link List}对象长度为0
	 */
	@SuppressWarnings("unchecked")
	public static final <T>  List<T> cleanNullAsList(T... objects) {
		if (null == objects || 0 == objects.length)return new ArrayList<T>(0);
		ArrayList<T> list = new ArrayList<T>(objects.length);
		for( T element: objects){
			if(null == element )continue;
			list.add(element);
		}
		return list;
	}
		
	/**
	 * 将泛型集合转为数组<br>
	 * 如果src不为null则要求集合中至少有一个不为null的元素,否则返回null
	 * 根据集合中第一个不为null的元素的类型创建数组。
	 * @param src
	 * @return  返回指定的泛型数组,如果src为null则返回null，
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T[] toGenericArray(Collection<T>src){
		if(null == src)return null;
		Object notNull=null;
		// 找出第一个不为null的元素
		for(T element:src)if(null != element){notNull=element;break;}
		if(null == notNull)return null;// 所有元素为null则返回null
		return src.toArray( (T[]) Array.newInstance(notNull.getClass(), src.size()));
	}
	
	/**
	 * @param src
	 * @return
	 * @see #toGenericArray(Collection)
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T[] toGenericArray(Set<T>src){
		if(null == src|| src.isEmpty())return null;
		Object notNull=null;
		// 找出第一个不为null的元素
		for(T element:src)if(null != element){notNull=element;break;}
		return src.toArray( (T[]) Array.newInstance(notNull.getClass(), src.size()));
	}
	
	/**
	 * 将泛型集合转为数组<br>
	 * @param src
	 * @param componentType 指定返回的数组类型元素,不可为null
	 * @return 返回指定的泛型数组,如果src为null则返回空数组
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T[] toGenericArray(Collection<T>src,Class<T> componentType){
		if(null == componentType)
			throw new NullPointerException();
		if(null == src)return (T[]) Array.newInstance(componentType, 0);
		return src.toArray( (T[]) Array.newInstance(componentType, src.size()));
	}
 }
