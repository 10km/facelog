// Automatically generated by the Thrifty compiler; do not edit!
// Generated on: 2019-09-23T07:19:09.721Z
// Source: J:\facelog\facelog-service\IFaceLog.thrift at 15:1
package net.gdface.facelog.client.thrift;

import javax.annotation.Generated;

@Generated(
    value = "com.microsoft.thrifty.gen.ThriftyCodeGenerator",
    comments = "https://github.com/microsoft/thrifty"
)
public enum MQParam {
  REDIS_URI(0),

  WEBREDIS_URL(1),

  CMD_CHANNEL(2),

  LOG_MONITOR_CHANNEL(3),

  HB_MONITOR_CHANNEL(4),

  HB_INTERVAL(5),

  HB_EXPIRE(6);

  public final int value;

  MQParam(int value) {
    this.value = value;
  }

  public static MQParam findByValue(int value) {
    switch (value) {
      case 0: return REDIS_URI;
      case 1: return WEBREDIS_URL;
      case 2: return CMD_CHANNEL;
      case 3: return LOG_MONITOR_CHANNEL;
      case 4: return HB_MONITOR_CHANNEL;
      case 5: return HB_INTERVAL;
      case 6: return HB_EXPIRE;
      default: return null;
    }
  }
}
