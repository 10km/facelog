package net.gdface.facelog.simplemq;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.concurrent.BlockingDeque;

import net.gdface.facelog.simplemq.utils.TypeUtils;

/**
 * 生产者模型接口
 * @author guyadong
 *
 * @param <T>
 */
public interface IProducer<T> {
	public static abstract class AbstractHandler<T> implements IProducer<T> {
	    /** 是否向队列末尾添加 */
		protected boolean offerLast = true;
		protected final Type type;
		protected final Class<?> rawType;
	    public AbstractHandler(Type type) {
			super();
			this.type = type;
			this.rawType = TypeUtils.getRawClass(type);
		}
		@Override
		public int produce(@SuppressWarnings("unchecked") T...array){
			int count = 0;
			for(T t: array){
				if(null != t){
					produce(t);
					++count;
				}
			}
			return count;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public int produce(Collection<T> c){
			return null ==c?0:produce(c.toArray((T[])Array.newInstance(this.rawType, c.size())));
		}

		@Override
		public IProducer<T> setOfferLast(boolean offerLast) {
			this.offerLast = offerLast;
			return this;
		}

		@Override
		public int produce(boolean offerLast, @SuppressWarnings("unchecked") T... array) {
			int count = 0;
			for(T t: array){
				if(null != t){
					produce(t,offerLast);
					++count;
				}
			}
			return count;
		}

		@SuppressWarnings("unchecked")
		@Override
		public int produce(boolean offerLast, Collection<T> c) {
			return null ==c?0:produce(offerLast,c.toArray((T[])Array.newInstance(this.rawType, c.size())));

		}
	}
	/**
	 * 向队列{@link #queue}中压入数据
	 * @param t
	 * @param offerLast 为true向队列末尾添加<br>
	 *                      为false时队列头部添加,要求{@link #queue}必须为双向队列{@link BlockingDeque}<br>
	 * @return
	 */
	boolean produce(T t, boolean offerLast);

	/**
	 * 向队列尾部添加数据
	 * @param t
	 * @return
	 */
	boolean produce(T t);

	/**
	 * 向队列添加一组对象
	 * @param array
	 * @return 实际添加的对象数目
	 */
	int produce(boolean offerLast,@SuppressWarnings("unchecked") T... array);

	/**
	 * @param c
	 * @return
	 * @see #produce(boolean, Object...)
	 */
	int produce(boolean offerLast,Collection<T> c);

	/**
	 * 向队列添加一组对象
	 * @param array
	 * @return 实际添加的对象数目
	 */
	int produce(@SuppressWarnings("unchecked") T... array);

	/**
	 * @param c
	 * @return
	 * @see #produce(Object...)
	 */
	int produce(Collection<T> c);
	/**
	 * @see #offerLast
	 * @param offerLast  
	 * @return
	 */
	IProducer<T> setOfferLast(boolean offerLast);

}