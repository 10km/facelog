package net.gdface.facelog.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;

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
        /** 无效设备ID */INVALID_DEVICE_ID,
        /** 无效人员ID */INVALID_PERSON_ID,
        /** 无效root密码 */INVALID_PASSWORD,
        /** 拒绝令牌申请 */REJECT_APPLY
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
    
    /** return a JSON string of declared fields  */
	@Override
	public String toString() {
		return jsonOfDeclaredFields();
	} 
	
	@Override
	public String jsonOfDeclaredFields(){
		HashMap<String,Object> fields = new HashMap<String,Object>(16);
		fields.put("type", getType());
		fields.put("deviceID", getDeviceID());
		int features=SerializerFeature.config(
				JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.QuoteFieldNames, false);
		return JSON.toJSONString(fields,features);
	}
}
