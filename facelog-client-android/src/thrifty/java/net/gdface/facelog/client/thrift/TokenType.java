// Automatically generated by the Thrifty compiler; do not edit!
// Generated on: 2019-09-23T07:19:09.719Z
// Source: J:\facelog\facelog-service\IFaceLog.thrift at 7:1
package net.gdface.facelog.client.thrift;

import javax.annotation.Generated;

@Generated(
    value = "com.microsoft.thrifty.gen.ThriftyCodeGenerator",
    comments = "https://github.com/microsoft/thrifty"
)
public enum TokenType {
  UNINITIALIZED(0),

  DEVICE(1),

  PERSON(2),

  ROOT(3);

  public final int value;

  TokenType(int value) {
    this.value = value;
  }

  public static TokenType findByValue(int value) {
    switch (value) {
      case 0: return UNINITIALIZED;
      case 1: return DEVICE;
      case 2: return PERSON;
      case 3: return ROOT;
      default: return null;
    }
  }
}
