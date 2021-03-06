set character set utf8;
SET NAMES 'utf8';
############################
# delete all table/view  ###
############################
DROP VIEW  IF EXISTS fl_log_light;
DROP TABLE IF EXISTS fl_log ;
DROP TABLE IF EXISTS fl_face ;
DROP TABLE IF EXISTS fl_feature ;
DROP TABLE IF EXISTS fl_person ;
DROP TABLE IF EXISTS fl_image ;
DROP TABLE IF EXISTS fl_device ;
DROP TABLE IF EXISTS fl_permit;
DROP TABLE IF EXISTS fl_person_group ;
DROP TABLE IF EXISTS fl_device_group ;
DROP TABLE IF EXISTS fl_store ;

############################
# create all table/view  ###
############################
# 所有表中
# create_time 记录创建时间戳 (默认提供数据库服务器时间)
# update_time 记录创建时间戳 (默认提供数据库服务器时间)

CREATE TABLE IF NOT EXISTS fl_store (
  `md5`      char(32) NOT NULL PRIMARY KEY COMMENT '主键,md5检验码',
  `encoding` varchar(16) DEFAULT NULL COMMENT '编码类型,GBK,UTF8...',
  `data`     mediumblob COMMENT '二进制数据(最大16MB)'
) COMMENT '二进制数据存储表' DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS fl_device_group (
  `id`          int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '设备组id',
  `name`        varchar(32) NOT NULL COMMENT '设备组名',
  `leaf`        tinyint(1) DEFAULT NULL COMMENT '是否为叶子节点, 1:叶子节点 0:分支节点,null:两者都可',
  `parent`      int(11) DEFAULT NULL COMMENT '上一级设备组id',
  `root_group`  int(11) DEFAULT NULL COMMENT '指向人员组id,用于应用层定义管理员/操作员的管理边界,此字段不为null代表此设备组为管理边界,指向的人员组为此设备组的拥有者的顶级组',
  `schedule`    varchar(4096) DEFAULT NULL COMMENT '设备工作时间表,为null或空为7x24小时工作,格式为JSON,参见开发手册',
  `remark`      varchar(256) DEFAULT NULL COMMENT '备注',
  `ext_bin`     blob DEFAULT NULL COMMENT '应用项目自定义二进制扩展字段(最大64KB)',
  `ext_txt`     text DEFAULT NULL COMMENT '应用项目自定义文本扩展字段(最大64KB)',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (parent)  REFERENCES fl_device_group(id) ON DELETE SET NULL
) COMMENT '设备组信息' DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS fl_person_group (
  `id`          int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '用户组id',
  `name`        varchar(32) NOT NULL COMMENT '用户组名',
  `leaf`        tinyint(1) DEFAULT NULL COMMENT '是否为叶子节点, 1:叶子节点 0:分支节点,null:两者都可',
  `parent`      int(11) DEFAULT NULL COMMENT '上一级用户组id',
  `root_group`  int(11) DEFAULT NULL COMMENT '指向设备组id,用于应用层定义管理员/操作员的管理边界,此字段不为null代表此用户组为管理边界,指向的设备组为此用户组的设备管理边界,
  对于属于此组的管理员和操作员都只能管理此组内的用户及对应设备组内的设备',
  `remark`      varchar(256) DEFAULT NULL COMMENT '备注',
  `ext_bin`     blob DEFAULT NULL COMMENT '应用项目自定义二进制扩展字段(最大64KB)',
  `ext_txt`     text DEFAULT NULL COMMENT '应用项目自定义文本扩展字段(最大64KB)',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (parent)  REFERENCES fl_person_group(id) ON DELETE SET NULL
) COMMENT '用户组信息' DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS fl_permit (
  `device_group_id`   int(11) NOT NULL COMMENT '外键,设备组id',
  `person_group_id`    int(11) NOT NULL COMMENT '外键,人员组id',
  `schedule`    varchar(4096) DEFAULT NULL COMMENT '允许通行时间表,为null或空为7x24小时工作,格式为JSON,参见开发手册',
  `pass_limit`       varchar(512) DEFAULT NULL COMMENT '通行次/天数限制定义,为null或空不限制,JSON格式字符串,参见开发手册',
  `remark`      varchar(256) DEFAULT NULL COMMENT '备注',
  `ext_bin`     blob DEFAULT NULL COMMENT '应用项目自定义二进制扩展字段(最大64KB)',
  `ext_txt`     text DEFAULT NULL COMMENT '应用项目自定义文本扩展字段(最大64KB)',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`device_group_id`, `person_group_id`),
  FOREIGN KEY (device_group_id)  REFERENCES fl_device_group(id) ON DELETE CASCADE,
  FOREIGN KEY (person_group_id)  REFERENCES fl_person_group(id) ON DELETE CASCADE
) COMMENT '通行权限关联表' DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS fl_device (
  `id`          int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '设备id',
  `group_id`    int(11) DEFAULT 1 COMMENT '所属设备组id',
  `name`        varchar(32) DEFAULT NULL COMMENT '设备名称',
  `product_name`varchar(32) DEFAULT NULL COMMENT '产品名称',
  `model`       varchar(32) DEFAULT NULL COMMENT '设备型号',
  `vendor`      varchar(32) DEFAULT NULL COMMENT '设备供应商',
  `manufacturer`varchar(32) DEFAULT NULL COMMENT '设备制造商',
  `made_date`   date DEFAULT NULL COMMENT '设备生产日期',
  `version`     varchar(32) DEFAULT NULL COMMENT '设备版本号',
  `used_sdks`   varchar(128)DEFAULT NULL COMMENT '支持的特征码(算法)版本号列表(逗号分隔),特征版本号用于区分不同人脸识别算法生成的特征数据(SDK版本号命名允许字母,数字,-,.,_符号)',
  `serial_no`   varchar(32) DEFAULT NULL UNIQUE COMMENT '设备序列号',
  `mac`         char(12) DEFAULT NULL UNIQUE COMMENT '6字节MAC地址(HEX)',
  `direction`   int(11) DEFAULT NULL COMMENT '通行方向,NULL,0:入口,1:出口,默认0',
  `remark`      varchar(256) DEFAULT NULL COMMENT '备注',
  `ext_bin`     blob DEFAULT NULL COMMENT '应用项目自定义二进制扩展字段(最大64KB)',
  `ext_txt`     text DEFAULT NULL COMMENT '应用项目自定义文本扩展字段(最大64KB)',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (group_id)  REFERENCES fl_device_group(id) ON DELETE SET NULL,
  INDEX `group_id` (`group_id` ASC),
  # 验证 direction 字段有效性
  CHECK(direction>=0 AND direction<=1)
) COMMENT '前端设备基本信息' DEFAULT CHARSET=utf8;

/*
 md5外键引用fl_store(md5),所以删除 fl_store 中对应的md5,会导致 fl_image 中的记录同步删除(ON DELETE CASCADE),
 所以删除图像最方便的方式是删除 fl_store 中对应的md5
 删除 fl_image 中记录时会同步级联删除 fl_face 中 image_md5 关联的所有记录
 thumb_md5,md5不做外键引用,以与 fl_store 解藕
*/
CREATE TABLE IF NOT EXISTS fl_image (
  `md5`         char(32) NOT NULL PRIMARY KEY COMMENT '主键,图像md5检验码,同时也是从 fl_store 获取图像数据的key',
  `format`      varchar(32)  COMMENT '图像格式', 
  `width`       int NOT NULL COMMENT '图像宽度',
  `height`      int NOT NULL COMMENT '图像高度',
  `depth`       int DEFAULT 0 NOT NULL  COMMENT '通道数',
  `face_num`    int DEFAULT 0 NOT NULL  COMMENT '图像中的人脸数目',
  `thumb_md5`   char(32)   DEFAULT NULL COMMENT '缩略图md5,图像数据存储在 fl_imae_store(md5)',
  `device_id`   int(11)    DEFAULT NULL COMMENT '外键,图像来源设备',
  FOREIGN KEY (device_id)  REFERENCES fl_device(id) ON DELETE SET NULL
) COMMENT '图像信息存储表,用于存储系统中所有用到的图像数据,表中只包含图像基本信息,图像二进制源数据存在在fl_store中(md5对应)' DEFAULT CHARSET=utf8;

/* 
 删除 fl_person 中记录时会同步级联删除 fl_log 中 person_id 关联的所有记录以及 fl_feature 中 person_id 关联的所有记录
 一个人可能会对应多个 fl_feature 记录
*/
CREATE TABLE IF NOT EXISTS fl_person (
  `id`          int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '用户id',
  `group_id`    int(11) DEFAULT 1 COMMENT '所属用户组id',
  `name`        varchar(32) NOT NULL COMMENT '姓名',
  `sex`         tinyint(1) DEFAULT NULL COMMENT '性别,0:女,1:男,其他:未定义',
  `rank`        tinyint(1) DEFAULT NULL COMMENT '用户级别,NULL,0:普通用户,2:操作员,3:管理员,其他:未定义',
  `password`    char(32) DEFAULT NULL COMMENT '用户密码,MD5',
  `birthdate`   date DEFAULT NULL COMMENT '出生日期',
  `mobile_phone`char(11) DEFAULT NULL UNIQUE COMMENT '手机号码',
  `papers_type` tinyint(1) DEFAULT NULL COMMENT '证件类型,0:未知,1:身份证,2:护照,3:台胞证,4:港澳通行证,5:军官证,6:外国人居留证,7:员工卡,8:其他',
  `papers_num`  varchar(32) DEFAULT NULL UNIQUE COMMENT '证件号码' ,
  `image_md5`   char(32)    DEFAULT NULL UNIQUE COMMENT '用户默认照片(证件照,标准照)的md5校验码,外键',
  `expiry_date` date DEFAULT '2050-12-31' COMMENT '验证有效期限(超过期限不能通过验证),为NULL永久有效',
  `activated_date` date DEFAULT NULL COMMENT '帐户激活日期,为NULL时,create_time字段即为激活日期',
  `remark`      varchar(256) DEFAULT NULL COMMENT '备注',
  `ext_bin`     blob DEFAULT NULL COMMENT '应用项目自定义二进制扩展字段(最大64KB)',
  `ext_txt`     text DEFAULT NULL COMMENT '应用项目自定义文本扩展字段(最大64KB)',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (group_id)  REFERENCES fl_person_group(id) ON DELETE SET NULL,
  FOREIGN KEY (image_md5) REFERENCES fl_image(md5) ON DELETE SET NULL,
  INDEX `expiry_date` (`expiry_date` ASC),
  # 验证 papers_type 字段有效性
  CHECK(papers_type>=0 AND papers_type<=8),
  # 验证 sex 字段有效性
  CHECK(sex>=0 AND sex<=1),
  # 验证 rank 字段有效性
  CHECK(rank = 0 OR rank= 2 OR rank = 3)
) COMMENT '人员基本描述信息' DEFAULT CHARSET=utf8;

/* 
 允许多个fl_face记录对应一个 fl_feature记录,以适应红外算法的特殊需要,同时满足服务器端负责对比计算的要求
*/
CREATE TABLE IF NOT EXISTS fl_feature (
  `md5`         char(32) NOT NULL PRIMARY KEY COMMENT '主键,特征码md5校验码',
  `version`     varchar(32) NOT NULL COMMENT '特征码(算法)版本号,用于区分不同人脸识别算法生成的特征数据(允许字母,数字,-,.,_符号)',
  `person_id`   int(11)  DEFAULT NULL COMMENT '外键,所属用户id',
  `feature`     blob     NOT NULL COMMENT '二进制特征数据',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (person_id)  REFERENCES fl_person(id) ON DELETE CASCADE,
  INDEX `feature_version` (`version` ASC)
) COMMENT '用于验证身份的人脸特征数据表' DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS fl_face (
  `id`          int(11)  NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
  `image_md5`   char(32) NOT NULL COMMENT '外键,所属图像id',
  ###### 人脸检测基本信息 <<
  # 人脸位置坐标
  `face_left`   int NOT NULL COMMENT '人脸位置矩形:x',
  `face_top`    int NOT NULL COMMENT '人脸位置矩形:y',
  `face_width`  int NOT NULL COMMENT '人脸位置矩形:width',
  `face_height` int NOT NULL COMMENT '人脸位置矩形:height',
  # 眼睛位置 
  `eye_leftx`   int COMMENT '左眼位置:x',
  `eye_lefty`   int COMMENT '左眼位置:y',
  `eye_rightx`  int COMMENT '右眼位置:x',
  `eye_righty`  int COMMENT '右眼位置:y',
  # 嘴巴位置 
  `mouth_x`     int COMMENT '嘴巴位置:x',
  `mouth_y`     int COMMENT '嘴巴位置:y',
  # 鼻子位置
  `nose_x`      int COMMENT '鼻子位置:x',
  `nose_y`      int COMMENT '鼻子位置:y',
  # 人脸姿态 
  `angle_yaw`   int COMMENT '人脸姿态:偏航角',
  `angle_pitch` int COMMENT '人脸姿态:俯仰角',
  `angle_roll`  int COMMENT '人脸姿态:滚转角',
  ###### 人脸检测基本信息 >> 
  `ext_info`    blob DEFAULT NULL COMMENT '扩展字段,保存人脸检测基本信息之外的其他数据,内容由SDK负责解析',
  `feature_md5` char(32) DEFAULT NULL COMMENT '外键,人脸特征数据MD5 id',
  FOREIGN KEY (image_md5)       REFERENCES fl_image(md5)   ON DELETE CASCADE,
  FOREIGN KEY (feature_md5)     REFERENCES fl_feature(md5) ON DELETE SET NULL
) COMMENT '人脸检测信息数据表,用于保存检测到的人脸的所有信息(特征数据除外)' DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS fl_log (
  `id`              int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '日志id',
  `person_id`       int(11) NOT NULL COMMENT '外键,用户id',
  `device_id`       int(11) DEFAULT NULL COMMENT '外键,日志来源设备id',
  `verify_feature`  char(32)DEFAULT NULL COMMENT '外键,用于验证身份的人脸特征数据MD5 id',
  `compare_face`    int(11) DEFAULT NULL COMMENT '外键,现场采集的人脸信息记录id',
  `verify_status`   tinyint(1) DEFAULT NULL COMMENT '验证状态,NULL,0:允许通过,其他:拒绝',
  `similarty`	    double  DEFAULT NULL COMMENT '验证相似度',
  `direction`       int(11) DEFAULT NULL COMMENT '通行方向,NULL,0:入口,1:出口,默认0',
  `verify_time`     timestamp NOT NULL COMMENT '验证时间(可能由前端设备提供时间)',
  `create_time`     timestamp DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (person_id)       REFERENCES fl_person(id)   ON DELETE CASCADE,
  FOREIGN KEY (device_id)       REFERENCES fl_device(id)   ON DELETE SET NULL,
  FOREIGN KEY (verify_feature)  REFERENCES fl_feature(md5) ON DELETE SET NULL,
  FOREIGN KEY (compare_face)    REFERENCES fl_face(id)     ON DELETE SET NULL
) COMMENT '人脸验证日志,记录所有通过验证的人员' DEFAULT CHARSET=utf8;

# 创建简单日志 view
CREATE VIEW fl_log_light AS SELECT log.id,
	person.id AS person_id,
    person.name,
    person.papers_type,
    person.papers_num,
    log.verify_time,
    log.direction
    FROM fl_log AS log JOIN fl_person AS person ON log.person_id = person.id;

# 创建默认的设备组和人员组
INSERT INTO fl_person_group (name) values ('DEFAULT GROUP(默认用户组)');
INSERT INTO fl_person_group (name) values ('OPERATOR_GROUP(操作员组)');
INSERT INTO fl_person_group (name) values ('ADMIN_GROUP(管理员组)');
INSERT INTO fl_device_group (name) values ('DEFAULT_GROUP(默认设备组)');