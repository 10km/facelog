package net.gdface.facelog;

import static com.google.common.base.Preconditions.*;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import net.gdface.facelog.ServiceSecurityException.FieldJsonTransformer;
import net.gdface.facelog.ServiceSecurityException.SecurityExceptionType;

public class ServiceSecurityExceptionDecorator {
	/** 指定生成的json中field不带引号 */
	private static final int NO_FIELD_QUOTE_FEATURE=SerializerFeature.config(
			JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.QuoteFieldNames, false);
	private final ServiceSecurityException delegate;
	private static final FieldJsonTransformer<ServiceSecurityException> transformer = new FieldJsonTransformer<ServiceSecurityException>(){

		@Override
		public String jsonOfDeclaredFields(ServiceSecurityException input) {
			Map<String,Object> map = Maps.newHashMap();
			map.put("type", input.getType());
			map.put("deviceID", input.getDeviceID());
			return JSON.toJSONString(
					map,
					NO_FIELD_QUOTE_FEATURE);
		}};
	static{
		ServiceSecurityException.setTransformer(transformer);
	}
	public ServiceSecurityExceptionDecorator(ServiceSecurityException delegate) {
		super();
		this.delegate = checkNotNull(delegate);
	}

	public ServiceSecurityException getDelegate() {
		return delegate;
	}

	public SecurityExceptionType getType() {
		return delegate.getType();
	}

	public ServiceSecurityException setType(SecurityExceptionType type) {
		return delegate.setType(type);
	}

	public Integer getDeviceID() {
		return delegate.getDeviceID();
	}

	public ServiceSecurityException setDeviceID(Integer deviceID) {
		return delegate.setDeviceID(deviceID);
	}

	public String getMessage() {
		return delegate.getMessage();
	}

	public void setMessage(String message) {
		delegate.setMessage(message);
	}

	public String getCauseClass() {
		return delegate.getCauseClass();
	}

	public void setCauseClass(String causeClass) {
		delegate.setCauseClass(causeClass);
	}

	public String getServiceStackTraceMessage() {
		return delegate.getServiceStackTraceMessage();
	}

	public void setServiceStackTraceMessage(String serviceStackTraceMessage) {
		delegate.setServiceStackTraceMessage(serviceStackTraceMessage);
	}

	public String getCauseFields() {
		return delegate.getCauseFields();
	}

	public void setCauseFields(String causeFields) {
		delegate.setCauseFields(causeFields);
	}

}
