package net.gdface.facelog;

/**
 * 装饰者模式接口
 * @author guyadong
 *
 */
public interface Delegator<T> {
	/**
	 * @return 返回代理对象
	 */
	T delegateInstance();
	/**
	 * @return 返回代理对象类型
	 */
	Class<T> typeOfDelegate();
}
