package gu.simplemq.utils;

import com.google.common.base.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;;

/**
 * 延迟初始化(Lazy initialization)变量封装接口
 * 
 * @author guyadong
 *
 * @param <T> 延迟变量类型
 */
public interface ILazyInitVariable<T> {

	/**
	 * 返回延迟初始化的 T 实例
	 * 
	 * @return
	 */
	public T get();
	
	public static class Factory {
		/**
		 * 创建 {@link TlsImpl}实例
		 * @param supplier T 实例提供者
		 * @return
		 */
		public static final<T> ILazyInitVariable<T> makeInstance(final Supplier<T> supplier){
			return makeInstance(false,supplier);
		}

		/**
		 * 创建{@link ILazyInitVariable}实例
		 * @param tls 为{@code true}创建 {@link TlsImpl}实例,否则创建{@link VolatileImpl}实例
		 * @param supplier T 实例提供者
		 * @return
		 */
		public static final <T> ILazyInitVariable<T> makeInstance(boolean tls,final Supplier<T> supplier) {
			return tls
					? new TlsImpl<T>(supplier)
					: new  VolatileImpl<T>(supplier);
		}
		/**
		 * 基于volatile的双重检查锁定实现{@link ILazyInitVariable}接口<br>
		 * 原理说明参见<a href=
		 * "http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html">《The
		 * "Double-Checked Locking is Broken" Declaration》</a><br>
		 * 要求 JDK5 以上版本
		 * 
		 * @param <T> variable type
		 */
		private static class VolatileImpl<T> implements ILazyInitVariable<T> {
			private volatile T var = null;
			private final Supplier<T> supplier;

			public VolatileImpl(Supplier<T> supplier) {
				this.supplier = checkNotNull(supplier);
			}

			@Override
			public T get() {
				// Double-checked locking
				if (null == var) {
					synchronized (this) {
						if (null == var) {
							var = supplier.get();
						}
					}
				}
				return var;
			}
		}
		
		/**
		 * 基于Thread Local Storage的双重检查锁定实现{@link ILazyInitVariable}接口<br>
		 * 原理说明参见<a href="http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html">《The "Double-Checked Locking is Broken" Declaration》</a><br>
		 *
		 * @param <T> variable type
		 */
		private static  class TlsImpl<T> implements ILazyInitVariable<T> {
			/**
			 * If perThreadInstance.get() returns a non-null value, this thread has done
			 * synchronization needed to see initialization of helper
			 */
			@SuppressWarnings("rawtypes")
			private final ThreadLocal perThreadInstance = new ThreadLocal();
			private T var = null;
			private final Supplier<T> supplier;

			public TlsImpl(Supplier<T> supplier) {
				this.supplier = checkNotNull(supplier);
			}

			@Override
			public T get() {
				if (null == perThreadInstance.get()) {
					init();
				}
				return var;
			}

			@SuppressWarnings({ "unchecked" })
			private void init() {
				synchronized (this) {
					if (null == var) {
						var = supplier.get();
					}
				}
				// Any non-null value would do as the argument here
				perThreadInstance.set(perThreadInstance);
			}
		}
	}
}
