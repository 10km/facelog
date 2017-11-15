// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: common.constant.java.vm
// ______________________________________________________
package net.gdface.facelog.client;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gu.simplemq.Channel;
/**
 * client端和服务端共用的变量定义
 * @author guyadong
 *
 */
public interface CommonConstant {
    public static final Logger logger = LoggerFactory.getLogger(CommonConstant.class);
    /** 整数型主键的{@code null} 替代值 */
    public static final int NULL_ID_INTEGER = -1;
    /** 字符串型主键的{@code null} 替代值 */
    public static final String NULL_ID_STRING = "";
    /** 默认服务端口号 */
    public static final int DEFAULT_PORT = 26411;
    /** 设备心跳包表 */
    public static final Channel<Date> TABLE_HEARTBEAT = new Channel<Date>("DeviceHeartbeat"){} ;
    /** 心跳包间隔(秒) */
    public static final int DEFAULT_HEARTBEAT_INTERVAL = 8;
    /** 心跳包失效时间(秒) */
    public static final int DEFAULT_HEARTBEAT_EXPIRE = 60;
    /** 默认(设备/人员)组id */
    public static final int DEFAULT_GROUP_ID = 1;
    /** 默认(设备/人员)组名 */
    public static final String DEFAULT_GROUP_NAME = "DEFAULT_GROUP";

    public static final Channel<Integer> PUBSUB_PERSON_INSERT = new Channel<Integer>("PersonInsert"){};
    public static final Channel<Integer> PUBSUB_PERSON_UPDATE = new Channel<Integer>("PersonUpdate"){};
    public static final Channel<Integer> PUBSUB_PERSON_DELETE = new Channel<Integer>("PersonDelete"){};

    public static final Channel<String> PUBSUB_FEATURE_INSERT = new Channel<String>("FeatureInsert"){};
    public static final Channel<String> PUBSUB_FEATURE_UPDATE = new Channel<String>("FeatureUpdate"){};
    public static final Channel<String> PUBSUB_FEATURE_DELETE = new Channel<String>("FeatureDelete"){};

    public static final Channel<PermitBean> PUBSUB_PERMIT_INSERT = new Channel<PermitBean>("PermitInsert"){};
    public static final Channel<PermitBean> PUBSUB_PERMIT_UPDATE = new Channel<PermitBean>("PermitUpdate"){};
    public static final Channel<PermitBean> PUBSUB_PERMIT_DELETE = new Channel<PermitBean>("PermitDelete"){};

    public static final Channel<LogBean> QUEUE_LOG = new Channel<LogBean>("queueLog"){};

    
    /** 用于SQL语句的时间戳格式转换对象 */
    public static final ThreadLocal<SimpleDateFormat> TIMESTAMP_FORMATTER = new ThreadLocal<SimpleDateFormat>(){
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}};
		/**
		 * 服务异常类型定义
		 * @author guyadong 
		 */
		public static enum ExceptionType{
		    /** unknown type*/
        UNKNOWN,
        /** database access exception type*/
        DAO
		}
}
