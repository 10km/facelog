#######################
# delete all tables ###
#######################
DROP VIEW IF EXISTS fl_face_light ;
DROP VIEW IF EXISTS fl_feature ;
#DROP TABLE IF EXISTS fl_feature ;
DROP TABLE IF EXISTS fl_log ;
DROP TABLE IF EXISTS fl_face ;
DROP TABLE IF EXISTS fl_person ;
DROP TABLE IF EXISTS fl_image ;
DROP TABLE IF EXISTS fl_device ;
DROP TABLE IF EXISTS fl_store ;

#######################
# create all tables ###
#######################
CREATE TABLE IF NOT EXISTS fl_store (
  `md5`      char(32) NOT NULL PRIMARY KEY COMMENT '主键,md5检验码',
  `encoding` varchar(16) COMMENT '编码类型,GBK,UTF8...',
  `data`     blob COMMENT '二进制数据'
) COMMENT '二进制大数据存储表' ;

CREATE TABLE IF NOT EXISTS fl_device (
  `id`          int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '设备id',
  `name`        varchar(32) DEFAULT NULL COMMENT '设备名称',
  `online`      tinyint(1) DEFAULT NULL COMMENT '设备是否在线标记',
  `group_id`    int(11) DEFAULT NULL COMMENT '设备所属组id',
  `version`     varchar(32) DEFAULT NULL COMMENT '设备版本号',
  `create_time` timestamp(3)NULL DEFAULT NULL,
  `update_time` timestamp(3)NULL DEFAULT NULL   
) COMMENT '前端设备基本信息' ;

CREATE TABLE IF NOT EXISTS fl_image (
  `md5`         char(32) NOT NULL PRIMARY KEY COMMENT '主键,图像md5检验码,同时也是外键fl_store(md5)',
  `format`      varchar(32) COMMENT '图像格式', 
  `width`       int COMMENT '图像宽度',
  `height`      int COMMENT '图像高度',
  `depth`       int COMMENT '通道数',
  `face_num`    int default 0 NOT NULL COMMENT '图像中的人脸数目',
  `thumbnail`   char(32) DEFAULT NULL COMMENT '外键,缩略图id',
  `device_id`   int(11) DEFAULT NULL COMMENT '外键,图像来源设备',
  FOREIGN KEY (md5)  REFERENCES fl_store(md5) ON DELETE CASCADE, 
  FOREIGN KEY (thumbnail)  REFERENCES fl_store(md5) ON DELETE SET NULL,
  FOREIGN KEY (device_id)  REFERENCES fl_device(id) ON DELETE SET NULL
) COMMENT '图像存储表,用于存储系统中所有用到的图像数据' ;

CREATE TABLE IF NOT EXISTS fl_person (
  `id`          int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '用户识别码',
  `group_id`    int(11) DEFAULT NULL COMMENT '用户所属组id',
  `name`        varchar(32) NOT NULL COMMENT '姓名',
  `sex`         varchar(2) DEFAULT NULL COMMENT '性别',
  `birthdate`   date DEFAULT NULL COMMENT '出生日期',
  `papers_type` tinyint(1) DEFAULT NULL COMMENT '证件类型,0:身份证,1:护照,2:台胞证,3:港澳通行证,4:军官证,5:外国人居留证,6:员工卡,7:其他',
  `papers_num`  varchar(32) DEFAULT NULL UNIQUE COMMENT '证件号码' ,
  `photo_id`    char(32) DEFAULT NULL COMMENT '用户照片的md5校验码,外键',
  `disable`      tinyint(1) DEFAULT 0 COMMENT '用户是否被禁用标志' ,
  `create_time` timestamp(3)NULL DEFAULT NULL,
  `update_time` timestamp(3)NULL DEFAULT NULL,
  FOREIGN KEY (photo_id)  REFERENCES fl_image(md5) ON DELETE SET NULL,
  INDEX `disable` (`disable` ASC),
  CHECK(papers_type>=0 AND papers_type<7)
) COMMENT '基本用户信息' ;

CREATE TABLE IF NOT EXISTS fl_face (
  `md5`         char(32) NOT NULL PRIMARY KEY COMMENT '主键,特征数据md5校验码',
  `person_id`   int(11) DEFAULT NULL COMMENT '外键,所属用户id',
  `img_md5`     char(32) NOT NULL COMMENT '外键,所属图像id',
  ###### 人脸检测基本信息 <<
  # 人脸位置坐标
  `face_left`   int NOT NULL ,
  `face_top`    int NOT NULL ,
  `face_width`  int NOT NULL ,
  `face_height` int NOT NULL ,
  # 眼睛位置 
  `eye_leftx`   int ,
  `eye_lefty`   int ,
  `eye_rightx`  int ,
  `eye_righty`  int ,
  # 嘴巴位置 
  `mouth_x`     int ,
  `mouth_y`     int ,
  # 鼻子位置
  `nose_x`      int ,
  `nose_y`      int ,
  # 人脸角度 
  `angle_yaw`   int ,
  `angle_pitch` int ,
  `angle_roll`  int ,
  ###### 人脸检测基本信息 >> 
  `ext_info`    blob DEFAULT NULL COMMENT '扩展字段,保存人脸检测基本信息之外的其他数据,内容由SDK负责解析',
  `feature`     blob DEFAULT NULL COMMENT '二进制特征数据',
  `create_time` timestamp(3) NULL DEFAULT NULL,
  FOREIGN KEY (img_md5)  REFERENCES fl_image(md5) ON DELETE CASCADE,
  FOREIGN KEY (person_id)  REFERENCES fl_person(id) ON DELETE SET NULL
) COMMENT '人脸检测信息数据表,用于保存检测到的人脸的所有信息(特征数据除外)' ;


CREATE TABLE IF NOT EXISTS fl_log (
  `id`          int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '日志id',
  `person_id`   int(11) NOT NULL COMMENT '外键,用户id',
  `device_id`   int(11) DEFAULT NULL COMMENT '外键,设备id',
  `verify_image`   varchar(32) DEFAULT NULL COMMENT '外键,登录人脸信息id',
  `compare_face` varchar(32) DEFAULT NULL COMMENT '外键,对比人脸id',
  `similarty`	double NOT NULL COMMENT '验证相似度',
  `verify_time` timestamp(3)NOT NULL,
  FOREIGN KEY (device_id)  REFERENCES fl_device(id) ON DELETE SET NULL,
  FOREIGN KEY (person_id)  REFERENCES fl_person(id) ON DELETE CASCADE,
  FOREIGN KEY (verify_image)  REFERENCES fl_image(md5) ON DELETE SET NULL,
  FOREIGN KEY (compare_face)  REFERENCES fl_face(md5) ON DELETE SET NULL
) COMMENT '验证日志,记录所有通过验证的人员' ;

/*
CREATE TABLE IF NOT EXISTS fl_feature (
  `md5`         char(32) NOT NULL PRIMARY KEY COMMENT '主键,特征码md5校验码',
  `person_id`   int(11) DEFAULT NULL COMMENT '外键,所属用户id',
  `img_md5`     char(32) NOT NULL COMMENT '外键,所属图像id',
  `feature`     blob NOT NULL COMMENT '二进制特征数据',
  `create_time` timestamp(3) NULL DEFAULT NULL,
  FOREIGN KEY (img_md5)  REFERENCES fl_image(md5) ON DELETE CASCADE,
  FOREIGN KEY (person_id)  REFERENCES fl_person(id) ON DELETE SET NULL
) COMMENT '人脸特征数据表' ;
*/
# 创建只包含特征数据的 view
CREATE VIEW fl_feature AS SELECT md5,person_id,img_md5,feature,create_time FROM fl_face WHERE feature IS NOT NULL;
# 创建不包含特征数据的 view (减少数据长度)
CREATE VIEW fl_face_light AS SELECT md5,person_id,img_md5,
	face_left,face_top,face_width,face_height,
    eye_leftx,eye_lefty,eye_rightx,eye_righty,
    mouth_x,mouth_y,nose_x,nose_y,
    angle_yaw,angle_pitch,angle_roll,
    ext_info,
    create_time
	FROM fl_face ;