package net.gdface.facelog;

import net.gdface.exception.BaseFaceException;

/**
 * 安全异常
 * @author guyadong
 *
 */
public final class ServiceSecurityException extends BaseFaceException {
	public static interface FieldJsonTransformer<T> {
		public String jsonOfDeclaredFields(T input);
	}

	private static final long serialVersionUID = 5298414024971333060L;
	private static FieldJsonTransformer<ServiceSecurityException> transformer;
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
		CurrentTokenContextOp.getDefaultInstance().currentError(this);
	}

	public ServiceSecurityException(Throwable cause) {
		this(null,cause);
	}
	
	public ServiceSecurityException(SecurityExceptionType type) {
		this();
		if(type == null){
			throw new NullPointerException("type is null");
		}
		this.type = type;
	}
    /** return exception type */
    public SecurityExceptionType getType() {
        return type;
    }
    public ServiceSecurityException setType(SecurityExceptionType type) {
        this.type = type;
        return this;
    }
	public Integer getDeviceID() {
		return deviceID;
	}
	public ServiceSecurityException setDeviceID(Integer deviceID) {
		this.deviceID = deviceID;
		return this;
	}
    
    /** return a JSON string of declared fields  */
	@Override
	public String toString() {
		if(null == transformer){
			return "";
		}
		return transformer.jsonOfDeclaredFields(this);
	} 
	
	static FieldJsonTransformer<ServiceSecurityException> getTransformer() {
		return transformer;
	}

	static void setTransformer(FieldJsonTransformer<ServiceSecurityException> transformer) {
		ServiceSecurityException.transformer = transformer;
	}
}
