package net.gdface.service.facelog;

import net.gdface.cli.ThriftServiceConstants;

/**
 * 常量定义
 * @author guyadong
 *
 */
public interface FacelogServiceConstants extends ThriftServiceConstants {
	public static final int DEFAULT_PORT= 26411;

	public static final String FACEAPI_CLASS_OPTION_LONG ="faceapi";
	public static final String FACEAPI_CLASS_OPTION_DESC ="faceapi implemention class name,default: empty";
	public static final String FACEAPI_STATIC_OPTION_LONG = "faceapiStaticMethod";
	public static final String FACEAPI_STATIC_OPTION_DESC = "static method name for get instance from faceapi class,default: getInstance";
	public static final String FACELOG_CLASS_OPTION_LONG = "facedb";
	public static final String FACELOG_FULL_CLASS_OPTION_DESC = "facedb implemention class name,default: empty";
	public static final String FACELOG_STATIC_OPTION_LONG = "facedbStaticMethod";
	public static final String FACELOG_STATIC_OPTION_DESC = "static method name for get instance from facedb class,default: getInstance";

}
