package gu.simplemq;

/**
 * 频道注销侦听器
 * @author guyadong
 *
 * @param <T>
 */
public interface IAfterUnregister<T> {
	public void apply(Channel<T> channel);
}
