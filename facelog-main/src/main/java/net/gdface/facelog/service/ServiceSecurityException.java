package net.gdface.facelog.service;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 安全异常
 * @author guyadong
 *
 */
@ThriftStruct
public final class ServiceSecurityException extends BaseServiceException {
	private static final long serialVersionUID = 5298414024971333060L;
	@ThriftStruct
	public static enum SecurityExceptionType{
        /** 其他未分类异常 */UNCLASSIFIED,
        /** 无效MAC地址 */INVALID_MAC,
        /** 无效序列号 */INVALID_SN,
        /** 序列号被占用 */OCCUPIED_SN,
        /** 无效的设备令牌 */INVALID_TOKEN,
        /** 无效设备ID*/INVALID_DEVICE_ID,
        /** 无效人员ID*/INVALID_PERSON_ID
	}
    private SecurityExceptionType type = SecurityExceptionType.UNCLASSIFIED;
    private Integer deviceID;
	public ServiceSecurityException() {
	}

	public ServiceSecurityException(String message) {
		super(message);
	}

	public ServiceSecurityException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceSecurityException(Throwable cause) {
		super(cause);
	}
	
	public ServiceSecurityException(SecurityExceptionType type) {
		super();
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServiceSecurityException [type=");
		builder.append(type);
		builder.append(", deviceID=");
		builder.append(deviceID);
		builder.append("]");
		return builder.toString();
	}    
}
