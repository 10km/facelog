package net.gdface.facelog.message;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CommonUtils {

	private CommonUtils() {}

	public static  final String[] cleanEmpty(String... strings) {
		return cleanEmptyAsList(strings).toArray(new String[0]);
	}
	
	public static  final List<String> cleanEmptyAsList(String... strings) {
		if (null == strings || 0 == strings.length)return new ArrayList<String>(0);
		ArrayList<String> list = new ArrayList<String>(strings.length);
		for( String element: strings){
			if(null == element || element.isEmpty() )continue;
			list.add(element);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static final <T>  T[] cleanNull(T...objects) {
		if(Judge.isEmpty(objects))return objects;
		List<T> list = cleanNullAsList(objects);
		if(list.isEmpty())
			return list.toArray((T[])new Object[0]);
		return list.toArray((T[]) Array.newInstance(objects.getClass().getComponentType(), list.size()));
		//return cleanNullAsList(objects).toArray((T[])new Object[0]);
	}
	
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
	public static final <T> T[] toArray(Object...src){
		if(null == src || 0 == src.length )return (T[]) src;
		Object dest = Array.newInstance(src.getClass().getComponentType(), src.length);
		System.arraycopy(src, 0, dest, 0, src.length);
		return (T[]) dest;
	}
	
	
 }
