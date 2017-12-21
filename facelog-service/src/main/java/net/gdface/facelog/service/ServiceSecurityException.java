package net.gdface.facelog.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;
import com.google.common.collect.ImmutableMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 安全异常
 * @author guyadong
 *
 */
@ThriftStruct
public final class ServiceSecurityException extends BaseServiceException {
	private static final long serialVersionUID = 5298414024971333060L;
	/** 指定生成的json中field不带引号 */
	private static final int NO_FIELD_QUOTE_FEATURE=SerializerFeature.config(
			JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.QuoteFieldNames, false);
	@ThriftStruct
	public static enum SecurityExceptionType{
        /** 其他未分类异常 */UNCLASSIFIED,
        /** 无效MAC地址 */INVALID_MAC,
        /** 无效序列号 */INVALID_SN,
        /** 序列号被占用 */OCCUPIED_SN,
        /** 无效的设备令牌 */INVALID_TOKEN,
        /** 无效设备ID */INVALID_DEVICE_ID,
        /** 无效人员ID */INVALID_PERSON_ID,
        /** 无效root密码 */INVALID_PASSWORD,
        /** 拒绝令牌申请 */REJECT_APPLY
	}
    private SecurityExceptionType type = SecurityExceptionType.UNCLASSIFIED;
    private Integer deviceID;
	public ServiceSecurityException() {
		this(null, null);
	}

	public ServiceSecurityException(String message) {
		this(message,null);
	}

	public ServiceSecurityException(String message, Throwable cause) {
		super(message, cause);
		TokenContext.getCurrentTokenContext().setError(this);
	}

	public ServiceSecurityException(Throwable cause) {
		this(null,cause);
	}
	
	public ServiceSecurityException(SecurityExceptionType type) {
		this();
		this.type = checkNotNull(type);
	}
    /** return exception type */
    @ThriftField(5)
    public SecurityExceptionType getType() {
        return type;
    }
    @ThriftField
    public ServiceSecurityException setType(SecurityExceptionType type) {
        this.type = type;
        return this;
    }
    @ThriftField(6)
	public Integer getDeviceID() {
		return deviceID;
	}
    @ThriftField
	public ServiceSecurityException setDeviceID(Integer deviceID) {
		this.deviceID = deviceID;
		return this;
	}
    
    /** return a JSON string of declared fields  */
	@Override
	public String toString() {
		return jsonOfDeclaredFields();
	} 
	
	@Override
	protected String jsonOfDeclaredFields(){		
		return JSON.toJSONString(
				ImmutableMap.of("type", getType(), "deviceID", getDeviceID()),
				NO_FIELD_QUOTE_FEATURE);
	}
}
