package net.gdface.facelog;

import java.util.Date;

import com.google.common.base.Predicate;

/**
 * 日期时间过滤器<br>
 * 用于判断日期是否符合日期过滤器条件，过滤器定义由字符串字符.
 * @author guyadong
 *
 */
public interface IDateTimeFilter extends Predicate<Date>{

	/**
	 * 使用当前过滤器过滤日期 
	 * @param date
	 * @return 日期可以通过指定的过滤器返回{@code true},否则返回{@code false}
	 */
	boolean apply(Date date);
	/**
	 * @param date {@code yyyy-MM-dd}或{@code yyyy-MM-dd HH:mm:ss}或{@code yyyy-MM-dd'T'HH:mm:ss.SSS'Z'}(ISO8601)格式日期字符串
	 * @return 日期可以通过指定的过滤器返回{@code true},否则返回{@code false}
	 */
	boolean apply(String date);
	/**
	 * 使用指定的过滤器过滤日期
	 * @param date 
	 * @param filter 过滤器字符串
	 * @return 日期可以通过指定的过滤器返回{@code true},否则返回{@code false}
	 */
	public boolean apply( Date date,String filter);
	/**
	 * @param date {@code yyyy-MM-dd}或{@code yyyy-MM-dd HH:mm:ss}或{@code yyyy-MM-dd'T'HH:mm:ss.SSS'Z'}(ISO8601)格式日期字符串
	 * @param filter json格式的过滤器字符串
	 * @return
	 */
	boolean apply(String date, String filter);
	/**
	 * 指定当前对象使用指定的过滤器
	 * @param filter 过滤器字符串
	 * @return 当前接口对象
	 */
	public IDateTimeFilter use(String filter);
	/**
	 * @return 返回过滤器字符串
	 */
	public String filter();
}
