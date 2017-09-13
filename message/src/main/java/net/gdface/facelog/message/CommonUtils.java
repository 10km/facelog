package net.gdface.facelog.message;

import java.util.ArrayList;

public class CommonUtils {

	private CommonUtils() {}

	public static  final String[] cleanEmpty(String... strings) {
		if (null == strings || 0 == strings.length)return new String[0];
		ArrayList<String> list = new ArrayList<String>(strings.length);
		for( String element: strings){
			if(null == element || element.isEmpty() )continue;
			list.add(element);
		}
		return list.toArray(new String[list.size()]);
	}

	@SuppressWarnings("unchecked")
	public static final <T>  T[] cleanNull(T... objects) {
		if (null == objects || 0 == objects.length)return (T[]) new Object[0];
		ArrayList<T> list = new ArrayList<T>(objects.length);
		for( T element: objects){
			if(null == element )continue;
			list.add(element);
		}
		return list.toArray((T[])new Object[list.size()]);
	}

}
