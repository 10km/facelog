package gu.simplemq.utils;

/**
 * 延迟初始化(Lazy initialization)变量封装接口
 * @author guyadong
 *
 * @param <T> 延迟变量类型
 */
public interface ILazyInitVariable<T> {
	public static abstract class BaseLazyVar<T> implements ILazyInitVariable<T>{
		/**
		 * 返回 T 实例
		 * @return
		 */
		abstract protected T doGet() ;
	}
	/**
	 * 返回延迟初始化的 T 实例 
	 * @return
	 */
	public T get();
}
