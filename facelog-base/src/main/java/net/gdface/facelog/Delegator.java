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
	T delegate();

}
