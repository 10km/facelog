package net.gdface.facelog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import gu.simplemq.json.BaseJsonEncoder;
import static net.gdface.facelog.CommonConstant.*;

/**
 * {@link IDateTimeFilter}实现<br>
 * 过滤定义字符串为json格式
 * @author guyadong
 *
 */
public class DateTimeJsonFilter implements IDateTimeFilter {
	private static final Logger logger = LoggerFactory.getLogger(DateTimeJsonFilter.class);
	/** 时间过滤器字段名 */
	public static final String FIELD_HOUR = "hour";
	/** 日期过滤器字段名 */
	public static final String FIELD_DAY = "day";
	/** 附加日期过滤器(周)字段名前缀,格式:w1~w7 */
	public static final String PREFIX_WKEY = "w";
	/** 附加日期过滤器(月)字段名前缀,格式:m1~m31 */
	public static final String PREFIX_MKEY = "m";
	/** 附加日期过滤器(月日)字段名前缀,格式:d0101~d1231 */
	public static final String PREFIX_DATEKEY = "d";
	/** 字段名:boolean类型,对于日期过滤器超出当月最后一天的日期为1时，是否可以作为当月最后一天 */
	public static final String AS_LASTDAY_IF_OVERFLOW = "asLastdayIfOverflow";
	/** 时间过滤器缺省值(0~23分别代表一天的24小时) */
	public static final int DEFAULT_HOUR = 0x00ffffff;
	/** 日期过滤器缺省值<br>
	 * bit0为1时,bit 1~7代表每周的天（1-星期日,2-星期一,3-星期二,4-星期三,5-星期四,6-星期五,7-星期六）<br>
	 * bit0为0时,bit 1~31分别代表每月1~31日<br>
	 */
	public static final int DEFAULT_DAY = 0xffffffff;
	private static final ImmutableMap<String, Object> defaultJsonFilterDefine = ImmutableMap.<String,Object>of(
				FIELD_HOUR, DEFAULT_HOUR,FIELD_DAY, 
				DEFAULT_DAY,
				AS_LASTDAY_IF_OVERFLOW, true);
	public static final DateTimeJsonFilter DEFAULT_FILTER = new DateTimeJsonFilter();
	private static final int MAX_DAY_OF_MONTH = 31;
	private static final BaseJsonEncoder encoder = BaseJsonEncoder.getEncoder();
	private String filter = null;
	private final JSONObject normalized = new JSONObject().fluentPutAll(defaultJsonFilterDefine);
	private boolean alwaysTrue;
	private Integer hour;
	private Integer day;
	private static final Predicate<String> additionalRuleFilter = new Predicate<String>(){

		@Override
		public boolean apply(String input) {
			return input.matches("^(m|w|d)\\d+");
		}};
	public DateTimeJsonFilter() {
		this(null);
	}

	public DateTimeJsonFilter(String filter) {
		use(filter);
	}

	private static final Date toDate(String date) throws ParseException{
		if(Strings.isNullOrEmpty(date)){
			return null;
		}
		try {
			return new SimpleDateFormat(TIMESTAMP_FORMATTER_STR).parse(date);
		} catch (ParseException e) {
			try {
				return new SimpleDateFormat(DATE_FORMATTER_STR).parse(date);				
			} catch (ParseException e2) {
				return new SimpleDateFormat(ISO8601_FORMATTER_STR).parse(date);
			}
		}
	}

	private void init (){
		try {
			alwaysTrue = false;
			normalized.clear();
			normalized.putAll(MoreObjects.firstNonNull(encoder.fromJson(filter,JSONObject.class),defaultJsonFilterDefine));
			hour = MoreObjects.firstNonNull(getIntegerOrNull(FIELD_HOUR), hour);
			day = MoreObjects.firstNonNull(getIntegerOrNull(FIELD_DAY), day);
			if(Strings.isNullOrEmpty(filter)){
				alwaysTrue = true;
				return;
			}
			if((hour & 0x00ffffff) == 0x00ffffff && ((day & 0xff) == 0xff || (day & 0xfffffffe) == 0xfffffffe)){
				if(!Iterables.tryFind(normalized.keySet(), additionalRuleFilter).isPresent()){
					alwaysTrue = true;
					return;	
				}
			}
		} catch (JSONException e) {
			logger.debug(e.getMessage());
			hour = DEFAULT_HOUR;
			day = DEFAULT_DAY;
			normalized.fluentPutAll(defaultJsonFilterDefine);
		}
	}
	private Integer getIntegerOrNull(String key){
		try {
			return normalized.getInteger(key);
		} catch (JSONException e) {
			logger.debug(e.getMessage());
			return null;
		}
	}
	private Boolean getBooleanUncheck(String key){
		try {
			return normalized.getBoolean(key);
		} catch (JSONException e) {
			logger.debug(e.getMessage());
			return null;
		}
	}
	
	private Integer maxOverflowKey(int lastDay){
		for(int i = MAX_DAY_OF_MONTH ; i > lastDay; --i){
			if(normalized.containsKey(PREFIX_MKEY + PREFIX_MKEY)){
				return getIntegerOrNull(PREFIX_MKEY + PREFIX_MKEY);
			}
		}
		return null;
	}
	@Override
	public boolean apply(Date date) {
		if(date != null){
			if(alwaysTrue){
				return true;
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
			int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			boolean asLastdayIfOverflow = MoreObjects.firstNonNull(getBooleanUncheck(AS_LASTDAY_IF_OVERFLOW), true);

			Integer extRuleMask = null;
			String datekey = String.format("%s%s",PREFIX_DATEKEY,new SimpleDateFormat("MMdd").format(date));
			String wkey = String.format("%s%d",PREFIX_WKEY,dayOfWeek);
			String mkey = String.format("%s%d",PREFIX_MKEY,dayOfMonth);
			if(normalized.containsKey(datekey)){
				extRuleMask = getIntegerOrNull(datekey);
			}else if(normalized.containsKey(mkey)){
				extRuleMask = getIntegerOrNull(mkey);
			}else if(asLastdayIfOverflow && (dayOfMonth == lastDay)){
				extRuleMask = maxOverflowKey(dayOfMonth);
			}else if(normalized.containsKey(wkey)){
				extRuleMask = getIntegerOrNull(wkey);
			}
			if(extRuleMask != null){
				return 0 != (extRuleMask & hour);
			}
			int dayMask = (1 == (day & 1)) ? (1 << dayOfWeek) :  (1 << dayOfMonth);
			if (0 == (day & 1) && (dayOfMonth == lastDay) && asLastdayIfOverflow){
				int maxOverflowDay = 31 - Integer.numberOfLeadingZeros(day);
				if(maxOverflowDay > lastDay){
					dayMask |= (1 << maxOverflowDay);
				}
			}
			int hourMask = (1 << hourOfDay);
			return (0 != (day & dayMask)) && (0 != (hour & hourMask));
		}
		return false;
	}
	@Override
	public boolean apply(String date) {
		try {
			return apply(toDate(date));
		} catch (ParseException e) {
			logger.debug(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean apply(Date date, String filter) {
		return new DateTimeJsonFilter(filter).apply(date);
	}
	@Override
	public boolean apply(String date, String filter) {
		return new DateTimeJsonFilter(filter).apply(date);
	}
	@Override
	public DateTimeJsonFilter use(String filter) {
		this.filter = filter;
		init();
		return this;
	}

	@Override
	public String filter(){
		return filter;
	}

	public String toJSONString() {
		return normalized.toJSONString();
	}

	/**
	 * 返回归一化的过滤器字符串(JSON)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return normalized.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((normalized == null) ? 0 : normalized.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof DateTimeJsonFilter)) {
			return false;
		}
		DateTimeJsonFilter other = (DateTimeJsonFilter) obj;
		if (normalized == null) {
			if (other.normalized != null) {
				return false;
			}
		} else if (!normalized.equals(other.normalized)) {
			return false;
		}
		return true;
	}

}
