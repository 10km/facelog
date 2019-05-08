package net.gdface.facelog;

/** 心跳包报道数据 */
public class DeviceHeadbeatPackage{
    /** 设备ID */
    private int deviceId;
    /** 设备当前IP地址 */
    private String hostAddress;
    public int getDeviceId() {
        return deviceId;
    }
    public DeviceHeadbeatPackage setDeviceId(int deviceId) {
        this.deviceId = deviceId;
        return this;
    }
    public String getHostAddress() {
        return hostAddress;
    }
    public DeviceHeadbeatPackage setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
        return this;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("HeadbeatPackage [deviceId=");
        builder.append(deviceId);
        builder.append(", hostAddress=");
        builder.append(hostAddress);
        builder.append("]");
        return builder.toString();
    }
}