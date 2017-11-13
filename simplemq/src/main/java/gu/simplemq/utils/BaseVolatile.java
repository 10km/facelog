package gu.simplemq.utils;

/**
 * 基于volatile的双重检查锁定实现{@link ILazyInitVariable}的抽象类<br>
 * 原理说明参见<a href="http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html">《The "Double-Checked Locking is Broken" Declaration》</a><br>
 * 要求 JDK5 以上版本 
 * @author guyadong
 *
 * @param <T> variable type
 */
public abstract class BaseVolatile<T> extends ILazyInitVariable.BaseLazyVar<T>{
	private volatile T var = null;
	public BaseVolatile() {
	}
	@Override
	public T get() {
		// Double-checked locking
		if(null == var){
			synchronized(this){
				if(null == var){
					var = doGet();
				}
			}
		}
		return var;
	}
}
