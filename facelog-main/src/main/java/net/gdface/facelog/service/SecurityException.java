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
public final class SecurityException extends BaseServiceException {
	private static final long serialVersionUID = 5298414024971333060L;
	@ThriftStruct
	public static enum DeviceExceptionType{
        /** 其他未分类异常 */UNCLASSIFIED,
        /** 无效MAC地址 */INVALID_MAC,
        /** 无效序列号 */INVALID_SN,
        /** 序列号被占用 */OCCUPIED_SN,
        /** 无效的设备令牌 */INVALID_TOKEN,
        /** 无效设备ID*/INVALID_DEVICE_ID,
        /** 无效人员ID*/INVALID_PERSON_ID
	}
    private DeviceExceptionType type = DeviceExceptionType.UNCLASSIFIED;
    private Integer deviceID;
	public SecurityException() {
	}

	public SecurityException(String message) {
		super(message);
	}

	public SecurityException(String message, Throwable cause) {
		super(message, cause);
	}

	public SecurityException(Throwable cause) {
		super(cause);
	}
	
	public SecurityException(DeviceExceptionType type) {
		super();
		this.type = checkNotNull(type);
	}
    /** return exception type */
    @ThriftField(4)
    public DeviceExceptionType getType() {
        return type;
    }
    @ThriftField
    public SecurityException setType(DeviceExceptionType type) {
        this.type = type;
        return this;
    }
    @ThriftField(5)
	public Integer getDeviceID() {
		return deviceID;
	}
    @ThriftField
	public SecurityException setDeviceID(Integer deviceID) {
		this.deviceID = deviceID;
		return this;
	}
}
