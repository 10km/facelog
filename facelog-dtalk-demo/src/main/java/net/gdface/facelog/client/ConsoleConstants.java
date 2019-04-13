package net.gdface.facelog.client;

import net.gdface.cli.CommonCliConstants;

/**
 * 常量定义
 * @author guyadong
 *
 */
public interface ConsoleConstants extends CommonCliConstants {
	public static final String DEFAULT_HOST="localhost";
	public static final int DEFAULT_USER_ID=-1;
	public static final String DEFAULT_PWD="root";
	public static final String SERVICE_HOST_OPTION_LONG = "host";
	public static final String SERVICE_HOST_OPTION_DESC = "service host name,default: ";
	public static final String SERVICE_PORT_OPTION_LONG = "port";
	public static final String SERVICE_PORT_OPTION_DESC = "service port number,default: ";
	public static final String SERVICE_USER_OPTION_LONG = "user";
	public static final String SERVICE_USER_OPTION_DESC = "user id,default: ";
	public static final String SERVICE_PWD_OPTION_LONG = "pwd";
	public static final String SERVICE_PWD_OPTION_DESC = "password ,default: ";
	public static final String DEVICE_MAC_OPTION_LONG = "mac";
	public static final String DEVICE_MAC_OPTION_DESC = "MAC addres(hex)for target device,such as d0:17:c2:d0:3f:bf,default: self mac address";
	public static final String TRACE_OPTION = "X";
	public static final String TRACE_OPTION_LONG = "trace";
	public static final String TRACE_OPTION_DESC = "show stack trace on error ,default: false";
}
