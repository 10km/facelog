package gu.simplemq.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author guyadong
 *
 */
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
		if (null == strings || 0 == strings.length){
			return Collections.emptyList();
		}
		ArrayList<String> list = new ArrayList<String>(strings.length);
		for( String element: strings){
			if(null == element || element.isEmpty() ){
				continue;
			}
			list.add(element);
		}
		return list;
	}
	
	/**
	 * 清除数组中的null元素
	 * @param objects
	 * @return 返回不含null元素的新的数组对象,objects为null时返回null
	 */
	@SuppressWarnings("unchecked")
	public static final <T>  T[] cleanNull(T...objects) {
		if(null == objects){
			return null;
		}
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
		if (null == objects || 0 == objects.length){
			return Collections.emptyList();
		}
		ArrayList<T> list = new ArrayList<T>(objects.length);
		for( T element: objects){
			if(null == element ){
				continue;
			}
			list.add(element);
		}
		return list;
	}
	
	public static final <T>  List<T> cleanNullAsList(Collection<T> objects) {
		if (null == objects){
			return Collections.emptyList();
		}
		ArrayList<T> list = new ArrayList<T>(objects.size());
		for( T element: objects){
			if(null == element ){
				continue;
			}
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
		if(null == src){
			return null;
		}
		Object notNull=null;
		// 找出第一个不为null的元素
		for(T element:src){
			if(null != element){notNull=element;break;}
		}
		if(null == notNull){
			// 所有元素为null则返回null
			return null;
		}
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
		if(null == componentType){
			throw new NullPointerException();
		}
		if(null == src){
			return (T[]) Array.newInstance(componentType, 0);
		}
		return src.toArray( (T[]) Array.newInstance(componentType, src.size()));
	}
 }
