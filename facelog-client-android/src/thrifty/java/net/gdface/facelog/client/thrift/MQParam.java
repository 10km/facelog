// Automatically generated by the Thrifty compiler; do not edit!
// Generated on: 2019-03-28T05:46:24.695Z
// Source: J:\facelog\facelog-service\IFaceLog.thrift at 15:1
package net.gdface.facelog.client.thrift;

import javax.annotation.Generated;

@Generated(
    value = "com.microsoft.thrifty.gen.ThriftyCodeGenerator",
    comments = "https://github.com/microsoft/thrifty"
)
public enum MQParam {
  REDIS_URI(0),

  CMD_CHANNEL(1),

  LOG_MONITOR_CHANNEL(2),

  HB_MONITOR_CHANNEL(3),

  HB_INTERVAL(4),

  HB_EXPIRE(5);

  public final int value;

  MQParam(int value) {
    this.value = value;
  }

  public static MQParam findByValue(int value) {
    switch (value) {
      case 0: return REDIS_URI;
      case 1: return CMD_CHANNEL;
      case 2: return LOG_MONITOR_CHANNEL;
      case 3: return HB_MONITOR_CHANNEL;
      case 4: return HB_INTERVAL;
      case 5: return HB_EXPIRE;
      default: return null;
    }
  }
}
