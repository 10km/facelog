package net.gdface.facelog;

/**
 * 设备心跳包
 * @author guyadong
 *
 */
public class DeviceHeartdbeatPackage{
    /** 设备ID */
    private int deviceId;
    /** 设备当前IP地址 */
    private String hostAddress;
    /** 设备当前状态,默认值0(正常状态),其他值由应用层定义 */
    private int status;
    /** 设备运行特性,应用层定义 */
    private int feature;
    public int getDeviceId() {
        return deviceId;
    }
    public DeviceHeartdbeatPackage setDeviceId(int deviceId) {
        this.deviceId = deviceId;
        return this;
    }
    public String getHostAddress() {
        return hostAddress;
    }
    public DeviceHeartdbeatPackage setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
        return this;
    }
    /**
	 * @return status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status 要设置的 status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return feature
	 */
	public int getFeature() {
		return feature;
	}
	/**
	 * 设置设备的工作特性
	 * @param feature 要设置的 feature
	 */
	public void setFeature(int feature) {
		this.feature = feature;
	}
	/* （非 Javadoc）
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceHeartdbeatPackage [deviceId=");
		builder.append(deviceId);
		builder.append(", ");
		if (hostAddress != null) {
			builder.append("hostAddress=");
			builder.append(hostAddress);
			builder.append(", ");
		}
		builder.append("status=");
		builder.append(status);
		builder.append(", feature=");
		builder.append(feature);
		builder.append("]");
		return builder.toString();
	}

}