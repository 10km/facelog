package net.gdface.facelog;

/**
 * client端和服务端共用的基本类型常量定义
 * @author guyadong
 *
 */
public interface CommonConstant {
    public static final int NULL_ID_INTEGER = -1;
    /** 字符串型主键的{@code null} 替代值 */
    public static final String NULL_ID_STRING = "";
    /** 默认服务端口号 */
    public static final int DEFAULT_PORT = 26411;
    /** 默认XHR(XML Http Request)服务端口号 */
    public static final int DEFAULT_PORT_XHR = 26412;
    /** 默认RESTful服务端口号 */
    public static final int DEFAULT_PORT_RESTFUL = 26413;
    /** 默认webredis主机名 */
    public static final String DEFAULT_WEBREDIS_HOST = "locahost";
    /** 默认webredis服务端口号 */
    public static final int DEFAULT_WEBREDIS_PORT = 26415;
    /** 默认心跳包间隔(秒) */
    public static final int DEFAULT_HEARTBEAT_PERIOD = 8;
    /** 默认心跳包失效时间(秒) */
    public static final int DEFAULT_HEARTBEAT_EXPIRE = 60;
    /** 服务端状态频道名 */
    public static final String FACELOG_HB_CHANNEL = "FACELOG_HB";
    /** 组播地址和端口号 */
	public static String MULTICAST_ADDRESS = "224.42.64.11:26411";
    /** 默认(设备/人员)组id */
    public static final int DEFAULT_GROUP_ID = 1;
    /** 默认(设备/人员)组名 */
    public static final String DEFAULT_GROUP_NAME = "DEFAULT_GROUP";
    /** 超级管理员用户名 */
    public static final String ROOT_NAME = "root";
    
    /** 服务端id名 */
    public static final int SERVICE_ID = -2;
    /** ISO8601时间格式 */
    public static final String ISO8601_FORMATTER_STR = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    
    /** 用于SQL语句的时间戳格式转换格式 */
    public static final String TIMESTAMP_FORMATTER_STR = "yyyy-MM-dd HH:mm:ss";
    
    /** 时间转换格式 */
    public static final String DATE_FORMATTER_STR = "yyyy-MM-dd";
    
    /** 用于验证sdk_version格式的正则表达式 */
    public static final String SDK_VERSION_REGEX = "[\\w\\.\\-]+";
    
    /** 每用户每个SDK版本允许建模最大数量默认值 */
    public static final int DEFAULT_FEATURE_PERSON_LIMIT = 1;
    
    /** 特征参数配置前缀 */
    public static final String FEATURE_PREFIX = "feature";
    
    /** 更新参数配置前缀 */
    public static final String UPDATE_PREFIX = "update";
    
    /** 特征参数配置LIMIT前缀 */
    public static final String FEATURE_PERSON_LIMIT_PREFIX = "feature.person.limit.";
    
    /** faceapi任务队列名 */
    public static final String TASK_FACEAPI_BASE = "FACEAPI_";
    
    /** 用户注册任务队列名 */
    public static final String TASK_REGISTER_BASE = "REGISTER_";
    
    /**
     * 定义图像数据引用来源
     */
    public static enum RefSrcType{
    	/** 返回 fl_image表指定的图像数据 */ 	DEFAULT,
    	/** 返回 fl_image表指定的图像数据 */ 	IMAGE,
    	/** 返回 fl_person表中的image_md5字段指定的图像数据 */	PERSON,
    	/** 返回 fl_face表中的image_md5字段指定的图像数据 */	FACE,
    	/** 返回 fl_log表中的compare_face字段间接指定的图像数据 */	LOG,
    	/** 返回 fl_log_light视图对应fl_log表记录中的compare_face字段的图像数据 */ LIGHT_LOG,
    }
    /**
     * 服务异常类型定义
     */
    public static enum ExceptionType{
        /** 其他未分类异常 */
        UNCLASSIFIED,
        /** 数据库访问异常 */
        DAO,
        /** redis 服务器异常 */
        REDIS_ERROR,
        /** RuntimeException */
        RUNTIME,
        /** 图像数据异常 */
        IMAGE_ERROR,
        /** 安全异常 ServiceSecurityException */
        SECURITY_ERROR
    }
    /**
     * 人员等级类型定义,参见表结构定义
     * @author guyadong 
     */
    public enum PersonRank{
        /** 普通用户 */person(0),
        /** 操作员 */operator(2),
        /** 管理员 */admin(3),
        /** root */root(4);
        /** 用户等级 */
        public final int rank;
        PersonRank(int rank){
            this.rank = rank;
        }
        /** 
         * 根据用户等级值返回{@link PersonRank}对象,
         * @return  {@code rank}为{@code null}时返回{@link ${esc.hash}person},{@code rank}为无效值时返回{@code null}
         */
        public static final PersonRank fromRank(Integer rank){
            if(null == rank){
                return person;
            }
            for(PersonRank r : PersonRank.values()){
                if(r.rank == rank){
                    return r;
                }
            }
            return null;
        }
        /**
         * 参见{@link ${esc.hash}fromRank(Integer)}
         * @param rank
         * @return 
         * @throws NullPointerException 当{@code rank}为无效值时
         */
        public static final PersonRank fromRankChecked(Integer rank){
            PersonRank r = fromRank(rank); 
            if(null == r){
                throw new NullPointerException(String.format("INVALID rank %d",rank));
            }
            return r;
        }
    }
    
    ///////////// COMMONS PROPERTY KEY DEFINITION ///////////
    
    /** root 用户密码 */
    public static final String ROOT_PASSWORD = "root.password";
    
    /** 是否执行设备令牌验证 */
    public static final String TOKEN_DEVICE_VALIDATE = "token.device.validate";
    
    /** 是否执行人员令牌验证 */
    public static final String TOKEN_PERSON_VALIDATE = "token.person.validate";
    
    /** 人员令牌失效时间(分钟) */
    public static final String TOKEN_PERSON_EXPIRE = "token.person.expire";
    
    /** 设备命令序列号有效时间[秒] */
    public static final String TOKEN_CMD_SERIALNO_EXPIRE = "token.cmd.serialNo.expire";
    
    /** 设备命令响应通道有效时间[秒] */
    public static final String TOKEN_CMD_ACKCHANNEL_EXPIRE= "token.cmd.ackChannel.expire";
    
    /** 是否允许普通人员申请令牌 */
    public static final String TOKEN_PERSON_REJECTZERO = "token.person.rejectZero";
    
    /** 服务端口号 */
    public static final String SERVER_PORT = "server.port";
    
    /** 最大连接数 */
    public static final String SERVER_CONNECTION_LIMIT = "server.connectiontLimit";
    
    /** 空闲连接超时(秒) */
    public static final String SERVER_IDLE_CONNECTION_TIMEMOUT = "server.idleConnectionTimeout";
    
    /** 工作线程数 */
    public static final String SERVER_WORKER_THREAD_COUNT ="server.workerThreadCount";

    /** 服务心跳包间隔[秒] */
    public static final String SERVER_HBINTERVAL ="server.hbInterval";

    /**  设备心跳包间隔(秒)  */
    public static final String HEARTBEAT_INTERVAL = "heartbeat.interval";
    
    /** 设备心跳包失效时间(秒) */
    public static final String HEARTBEAT_EXPIRE = "heartbeat.expire";
    
    /** REDIS SERVER 可执行程序路径 */
    public static final String REDIS_EXE = "redis.exe";
    
    /** REDIS 没启动时是否等待 */
    public static final String REDIS_WAITIFABSENT = "redis.waitIfAbsent";
    
    /** REDIS  等待重试次数 */
    public static final String REDIS_TRYCOUNT = "redis.tryCount";
    
    /** REDIS  等待重试间隔(毫秒) */
    public static final String REDIS_TRYINTERVAL = "redis.tryInterval";
    
    /** REDIS 主机名 */
    public static final String REDIS_HOST = "redis.host";
    
    /** REDIS 端口 */
    public static final String REDIS_PORT = "redis.port";    
    
    /** REDIS 数据库 */
    public static final String REDIS_DATABASE = "redis.database";        
    
    /** REDIS 密码 */
    public static final String REDIS_PASSWORD = "redis.password";        
    
    /** REDIS 访问地址,如 'jedis://localhost:6397/0',设置此值时忽略所有其他redis参数(host,port,password,database) */
    public static final String REDIS_URI = "redis.uri";
    
    /** REDIS 超时(秒) */
    public static final String REDIS_TIMEOUT = "redis.timeout";
    
    /** jedis pool 最大线程数 */
    public static final String REDIS_POOL_MAXTOTAL = "redis.pool.maxTotal";
    
    /** node可执行程序路径 */
    public static final String NODEJS_EXE = "nodejs.exe";
 
    /** webredis 启动脚本路径 */
    public static final String WEBREDIS_FILE = "webredis.file";
   
    /** webredis主机名,为非本机名('localhost','127.0.0.1')时,不执行本地webredis启动 */
    public static final String WEBREDIS_HOST = "webredis.host";

    /** webredis服务端口 */
    public static final String WEBREDIS_PORT = "webredis.port";

    /** webredis:redis 主机名 */
    public static final String WEBREDIS_RHOST = "webredis.rhost";
    
    /** webredis:redis 端口 */
    public static final String WEBREDIS_RPORT = "webredis.rport";
    
    /** webredis:redis 密码 */
    public static final String WEBREDIS_RAUTH = "webredis.rauth";
    
    /** webredis:redis 数据库 */
    public static final String WEBREDIS_RDB = "webredis.rdb";

    /** webredis:redis 连接uri,设置此值时忽略所有其他redis参数('rhost','rport'...) */
    public static final String WEBREDIS_RURI = "webredis.ruri";

    /** XHR(XML HTTP Request)服务参数配置:XHR服务端是否启动 */
    public static final String XHR_START = "xhr.start";
    
    /** XHR(XML HTTP Request)服务参数配置:XHR服务端口,默认值{@value #DEFAULT_PORT_XHR} */
    public static final String XHR_PORT = "xhr.port";

    /** RESTful web服务参数配置:RESTful 服务端是否启动 */
    public static final String RESTFUL_START = "restful.start";
    
    /** RESTful web服务参数配置:RESTful服务端口,默认值{@value #DEFAULT_PORT_RESTFUL} */
    public static final String RESTFUL_PORT = "restful.port";
    
    /** RESTful web服务参数配置:是否显示在线swagger文档,默认值{@code true} */
    public static final String RESTFUL_SWAGGER_ENABLE = "restful.swaggerEnable";

    /** 特征参数配置:允许的SDK版本号(白名单),逗号分隔 */
    public static final String FEATURE_SDKVERSION_WHITELIST = "feature.sdkVersion.whitelist";

    /** 特征参数配置:启动特征码自动更新机制:当用户已经有特征码达到最大值时，再次添加特征将替换原有特征中最旧的 */
    public static final String FEATURE_PERSON_UPDATE_AUTO = "feature.person.update.auto";

    /** 特征参数配置:每用户每个SDK版本允许建模最大数量默认值 */
    public static final String FEATURE_PERSON_LIMIT_DEFAULT = "feature.person.limit.default";

    /** 操作员对person表允许的操作 */
    public static final String SECURITY_OPERATOR_TABLE_PERSON_ALLOW = "security.operator.table.person.allow";
    
    /** 操作员对person_group表允许的操作 */
    public static final String SECURITY_OPERATOR_TABLE_PERSON_GROUP_ALLOW = "security.operator.table.persongroup.allow";
    
    /** 操作员对person表允许的操作 */
    public static final String SECURITY_OPERATOR_TABLE_DEVICE_ALLOW = "security.operator.table.device.allow";
    
    /** 操作员对person_group表允许的操作 */
    public static final String SECURITY_OPERATOR_TABLE_DEVICE_GROUP_ALLOW = "security.operator.table.devicegroup.allow";

    /** 设备端对person表允许的操作 */
    public static final String SECURITY_DEVICE_TABLE_PERSON_ALLOW = "security.device.table.person.allow";
    
    /** 设备端对person_group表允许的操作 */
    public static final String SECURITY_DEVICE_TABLE_PERSON_GROUP_ALLOW = "security.device.table.persongroup.allow";
    
    /** 设备端对person表允许的操作 */
    public static final String SECURITY_DEVICE_TABLE_DEVICE_ALLOW = "security.device.table.device.allow";
    
    /** 设备端对person_group表允许的操作 */
    public static final String SECURITY_DEVICE_TABLE_DEVICE_GROUP_ALLOW = "security.device.table.devicegroup.allow";

    /** 服务端是否主动向人员验证实时监控通道发送log日志 */
    public static final String MONITOR_LOG = "monitor.log";
    
    /** 通用线程池:最小线程数量,默认为CPU逻辑核数 */
    public static final String EXECUTOR_CACHEDPOOL_COREPOOLSIZE = "executor.cachedPool.corePoolSize";

    /** 通用线程池:最大线程数量 */
    public static final String EXECUTOR_CACHEDPOOL_MAXIMUMPOOLSIZE = "executor.cachedPool.maximumPoolSize";

    /** 通用线程池:空闲线程保留时间(秒) */
    public static final String EXECUTOR_CACHEDPOOL_KEEPALIVETIME = "executor.cachedPool.keepAliveTime";

    /** 通用线程池:任务队列容量 */
    public static final String EXECUTOR_CACHEDPOOL_QUEUECAPACITY = "executor.cachedPool.queueCapacity";

    /** 通用线程池:线程命名格式 */
    public static final String EXECUTOR_CACHEDPOOL_NAMEFORMAT = "executor.cachedPool.nameFormat";
    
    /** 定时任务线程池:线程数量,默认值:1 */
    public static final String EXECUTOR_TIMERPOOL_COREPOOLSIZE = "executor.timerPool.corePoolSize";

    /** 定时任务线程池:线程命名格式 */
    public static final String EXECUTOR_TIMERPOOL_NAMEFORMAT = "executor.timerPool.nameFormat";
    
    /** person 需要记录日志的操作 */
    public static final String SYSLOG_OP_DAO_PERSON = "syslog.op.dao.person";
    
    /** persongroup 表需要记录日志的操作 */
    public static final String SYSLOG_OP_DAO_PERSONGROUP = "syslog.op.dao.persongroup";
    
    /** device 表需要记录日志的操作 */
    public static final String SYSLOG_OP_DAO_DEVICE = "syslog.op.dao.device";
    
    /** devicegroup 表需要记录日志的操作 */
    public static final String SYSLOG_OP_DAO_DEVICEGROUP = "syslog.op.dao.devicegroup";
    
    /** permit 表需要记录日志的操作 */
    public static final String SYSLOG_OP_DAO_PERMIT = "syslog.op.dao.permit";
    
    /** 令牌日志:是否记录详细异常信息 */
    public static final String SYSLOG_OP_TOKEN_ERRORDETAIL = "syslog.op.token.errorDetail";

    /** 系统日志级别 [OFF,FATAL,ERROR,WARN,INFO,DEBUG,ALL] */
    public static final String SYSLOG_LEVEL = "syslog.level";

    /** 系统日志文件位置 */
    public static final String SYSLOG_LOCATION = "syslog.location";

}
