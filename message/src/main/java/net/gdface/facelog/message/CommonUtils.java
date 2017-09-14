package net.gdface.facelog.message;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	
	@SuppressWarnings("unchecked")
	public static final <T> T[] toGenericArray(Object...src){
		if(null == src )return null;
		Object dest = Array.newInstance(src.getClass().getComponentType(), src.length);
		System.arraycopy(src, 0, dest, 0, src.length);
		return (T[]) dest;
	}
	
	public static final <T> T[] toGenericArray(Collection<T>src){
		if(null == src)return null;			
		return toGenericArray(src.toArray());
	}
	
	
 }
