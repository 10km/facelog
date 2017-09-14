/**   
 * @Title: Judge.java 
 * @Package net.gdface.utils 
 * @Description: TODO 
 * @author guyadong   
 * @date 2015年4月23日 上午9:57:02 
 * @version V1.0   
 */
package net.gdface.facelog.message;

import java.util.Collection;

/**
 * 条件判断
 * 
 * @author guyadong
 *
 */
public class Judge {
	/**
	 * 判断数组对象是不是有为null或空的
	 * 
	 * @param args
	 * @return true/false
	 */
	public static final boolean isEmpty(byte[] arg) {
		return null==arg||0==arg.length;
	}
	/**
	 * 判断所有数组对象是不是有为null或空的，只要有一个，就返回true;
	 * 
	 * @param args
	 * @return true/false
	 */
	public static final boolean hasEmpty(byte[]... args) {
		for (byte[] e : args) {
			if (null == e || 0 == e.length)
				return true;
		}
		return false;
	}
	/**
	 * 判断对象是不是有为null或空的
	 * 
	 * @param arg
	 * @return true/false
	 */
	public static final boolean isEmpty(String arg) {
		return (null == arg || 0 == arg.length());
	}
	/**
	 * 判断所有数组对象是不是有为null或空的，只要有一个，就返回true;
	 * 
	 * @param args
	 * @return true/false
	 */
	public static final boolean hasEmpty(String... args) {
		for (String e : args) {
			if (null == e || 0 == e.length())
				return true;
		}
		return false;
	}
	/**
	 * 判断Collection对象是不是有为null或空的
	 * 
	 * @param arg
	 * @return true/false
	 */
	public static final <T extends Collection<?>> boolean isEmpty(T arg) {
		return null == arg || 0 == arg.size();
	}
	/**
	 * 判断所有Collection对象是不是有为null或空的，只要有一个，就返回true;
	 * 
	 * @param args
	 * @return true/false
	 */
	public static final <T extends Collection<?>> boolean hasEmpty(T... args) {
		for (T e : args) {
			if (null == e || 0 == e.size())
				return true;
		}
		return false;
	}
	/**
	 * 判断所有数组对象是不是有为null或空的，只要有一个，就返回true;
	 * 
	 * @param arg
	 * @return true/false
	 */
	public static final <T> boolean isEmpty(T[] arg) {
			return null == arg || 0 == arg.length;
	}
	/**
	 * 判断所有数组对象是不是有为null或空的，只要有一个，就返回true;
	 * 
	 * @param args
	 * @return true/false
	 */
	public static final <T> boolean hasEmpty(T[]... args) {
		for (T[] e : args) {
			if (null == e || 0 == e.length)
				return true;
		}
		return false;
	}
	/**
	 * 判断参数是不是为null
	 * 
	 * @param args
	 * @return true/false
	 */
	public static final <T> boolean isNull(T arg) {
		return null==arg;		
	}
	/**
	 * 判断所有参数是不是有为null的，只要有一个，就返回true;
	 * 
	 * @param args
	 * @return true/false
	 */
	public static final <T> boolean hasNull(T... args) {
		if (null != args) {
			for (T e : args) {
				if (null == e)
					return true;
			}
		}
		return false;
	}

}
