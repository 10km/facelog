package net.gdface.facelog;

import java.util.Iterator;
import java.util.ServiceLoader;

import static com.google.common.base.Preconditions.*;

/**
 * 安全异常
 * @author guyadong
 *
 */
public class ServiceSecurityException extends Exception {
	public static interface FieldJsonTransformer<T> {
		public String jsonOfDeclaredFields(T input);
	}
	/**
	 * SPI(Service Provider Interface)机制加载 {@link FieldJsonTransformer}实例,没有找到返回{@code null}
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static FieldJsonTransformer spiTransformer() {		
		ServiceLoader<FieldJsonTransformer> providers = ServiceLoader.load(FieldJsonTransformer.class);
		Iterator<FieldJsonTransformer> itor = providers.iterator();
		if(!itor.hasNext()){
			return null;
		}
		return itor.next();
	}
	private static final long serialVersionUID = 5298414024971333060L;
	@SuppressWarnings("unchecked")
	private static FieldJsonTransformer<ServiceSecurityException> transformer = spiTransformer();
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
		CurrentTokenContextOp.getInstance().currentError(this);
	}

	public ServiceSecurityException(Throwable cause) {
		this(null,cause);
	}
	
	public ServiceSecurityException(SecurityExceptionType type) {
		this(checkNotNull(type, "type is null").name());
		this.type = type;
	}
    /** return exception type */
    public SecurityExceptionType getType() {
        return type;
    }
    public ServiceSecurityException setType(SecurityExceptionType type) {
    	if(null != type){
    		this.type = type;
    	}
        return this;
    }
	public Integer getDeviceID() {
		return deviceID;
	}
	public ServiceSecurityException setDeviceID(Integer deviceID) {
		this.deviceID = deviceID;
		return this;
	}
    
    /**
     * 如果父类方法返回空,则将错误类型{@link #type}名字返回
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		String msg = super.getMessage();
		return msg == null ? type.name() : msg;
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
