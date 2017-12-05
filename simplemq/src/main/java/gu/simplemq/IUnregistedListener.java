package gu.simplemq;

/**
 * 频道注销侦听器
 * @author guyadong
 *
 * @param <T>
 */
public interface IUnregistedListener<T> {
	public void apply(Channel<T> channel);
}
