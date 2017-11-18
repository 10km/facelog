package net.gdface.facelog.service;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 设备异常
 * @author guyadong
 *
 */
@ThriftStruct
public final class DeviceException extends BaseServiceException {
	private static final long serialVersionUID = 5298414024971333060L;
	@ThriftStruct
	public static enum DeviceExceptionType{
        /** 其他未分类异常 */UNCLASSIFIED,
        /** 无效MAC地址 */INVALID_MAC,
        /** 无效序列号 */INVALID_SN,
        /** 序列号被占用 */OCCUPIED_SN
	}
    private DeviceExceptionType type = DeviceExceptionType.UNCLASSIFIED;
    private Integer deviceID;
	public DeviceException() {
	}

	public DeviceException(String message) {
		super(message);
	}

	public DeviceException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeviceException(Throwable cause) {
		super(cause);
	}
	
	public DeviceException(DeviceExceptionType type) {
		super();
		this.type = checkNotNull(type);
	}
    /** return exception type */
    @ThriftField(4)
    public DeviceExceptionType getType() {
        return type;
    }
    @ThriftField
    public DeviceException setType(DeviceExceptionType type) {
        this.type = type;
        return this;
    }
}
