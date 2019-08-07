package net.gdface.facelog;

import gu.simplemq.json.BaseJsonEncoder;

/**
 * 访问控制对象,用于fl_permit
 * @author guyadong
 *
 */
public class PassLimit {
	
	/**
	 * 为{@code true}时{@link #passLimit}为限制天数，且忽略{@link #passLimitPerDay}的限制<br>
	 * 否则{@link #passLimit}为次数限制
	 */
	private boolean dayLimit=false;
	/**
	 * 用户有效期内可以的通行总次数(或总天数)限制,为{@code null}不限制
	 */
	private Integer passLimit;
	/**
	 * 每天通行次总数限制,为{@code null}不限制
	 */
	private Integer passLimitPerDay;
	/**
	 * 当因为网络异常或其他错误造成设备端无法统计通行次数判断是否允许该用户通行时,
	 * 设置为{@code true}就允许通行,
	 * 设置为{@code false}就不允许通行
	 */
	private boolean permitOnException=true;
	public PassLimit() {
	}
	
	/**
	 * 解析 fl_permit.pass_limit字段为当前对象
	 * @param json
	 * @return
	 */
	public static PassLimit fromJson(String json){
		return BaseJsonEncoder.getEncoder().fromJson(json, PassLimit.class);
	}

	/**
	 * 输出JSON字符串
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return BaseJsonEncoder.getEncoder().toJsonString(this);
	}

	/**
	 * @return dayLimit
	 */
	public boolean isDayLimit() {
		return dayLimit;
	}

	/**
	 * @param dayLimit 要设置的 dayLimit
	 */
	public void setDayLimit(boolean dayLimit) {
		this.dayLimit = dayLimit;
	}

	/**
	 * @return passLimit
	 */
	public Integer getPassLimit() {
		return passLimit;
	}

	/**
	 * @param passLimit 要设置的 passLimit
	 */
	public void setPassLimit(Integer passLimit) {
		this.passLimit = passLimit;
	}

	/**
	 * @return passLimitPerDay
	 */
	public Integer getPassLimitPerDay() {
		return passLimitPerDay;
	}

	/**
	 * @param passLimitPerDay 要设置的 passLimitPerDay
	 */
	public void setPassLimitPerDay(Integer passLimitPerDay) {
		this.passLimitPerDay = passLimitPerDay;
	}

	/**
	 * @return permitOnException
	 */
	public boolean isPermitOnException() {
		return permitOnException;
	}

	/**
	 * @param permitOnException 要设置的 permitOnException
	 */
	public void setPermitOnException(boolean permitOnException) {
		this.permitOnException = permitOnException;
	}
	
}
