package gu.simplemq;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.concurrent.BlockingDeque;

import com.google.common.reflect.TypeToken;

/**
 * 生产者模型接口(单队列)
 * @author guyadong
 *
 * @param <T>
 */
public interface IProducerSingle<T> {
	public static abstract class AbstractHandler<T> implements IProducerSingle<T> {
	    /** 是否向队列末尾添加 */
		protected boolean offerLast = true;
		protected final Type type;
		protected final Class<?> rawType;
	    public AbstractHandler(Type type) {
			super();
			this.type = type;
			this.rawType = TypeToken.of(type).getRawType();
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
		public IProducerSingle<T> setOfferLast(boolean offerLast) {
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
	 * @param offerLast 为{@code true}向队列头部添加
	 * @param array
	 * @return 实际添加的对象数目
	 */
	int produce(boolean offerLast,@SuppressWarnings("unchecked") T... array);

	/**
	 * 参见 {@link #produce(boolean, Object...)}
	 * @param offerLast
	 * @param c
	 * @return
	 */
	int produce(boolean offerLast,Collection<T> c);

	/**
	 * 向队列添加一组对象
	 * @param array
	 * @return 实际添加的对象数目
	 */
	int produce(@SuppressWarnings("unchecked") T... array);

	/**
	 * 参见 {@link #produce(Object...)}
	 * @param c
	 * @return
	 */
	int produce(Collection<T> c);
	/**
	 * 参见 {@link #offerLast} 
	 * @param offerLast  
	 * @return
	 */
	IProducerSingle<T> setOfferLast(boolean offerLast);

}