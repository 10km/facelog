package gu.simplemq.utils;

/**
 * 基于Thread Local Storage的双重检查锁定实现{@link ILazyInitVariable}的抽象类<br>
 * 原理说明参见<a href="http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html">《The "Double-Checked Locking is Broken" Declaration》</a><br>
 * @author guyadong
 *
 * @param <T> variable type
 */
public abstract class BaseTls<T> extends ILazyInitVariable.BaseLazyVar<T> {
	/**
	 * If perThreadInstance.get() returns a non-null value, this thread has done
	 * synchronization needed to see initialization of helper
	 */
	@SuppressWarnings("rawtypes")
	private final ThreadLocal perThreadInstance = new ThreadLocal();
	private T var = null;

	public BaseTls() {
	}

	@Override
	public T get() {
		if (null == perThreadInstance.get()) {
			initFieldNames();
		}
		return var;
	}

	@SuppressWarnings({ "unchecked" })
	private void initFieldNames() {
		synchronized (this) {
			if (null == var) {
				var = doGet();
			}
		}
		// Any non-null value would do as the argument here
		perThreadInstance.set(perThreadInstance);
	}
}
