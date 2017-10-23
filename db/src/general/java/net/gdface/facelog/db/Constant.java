// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// template: constant.java.vm
// ______________________________________________________

package net.gdface.facelog.db;
/**
 * constant declare
 * @author guyadong
 */
public interface Constant {    

    /** set =QUERY for loadUsingTemplate */
    public static final int SEARCH_EXACT = 0;
    /** set %QUERY% for loadLikeTemplate */
    public static final int SEARCH_LIKE = 1;
    /** set %QUERY for loadLikeTemplate */
    public static final int SEARCH_STARTING_LIKE = 2;
    /** set QUERY% for loadLikeTemplate */
    public static final int SEARCH_ENDING_LIKE = 3;
    
    //////////////////////////////////////
    // FOREIGN KEY INDEX DECLARE
    //////////////////////////////////////    
    /** foreign key fl_face(feature_md5) -> fl_feature */
    public static final int FL_FACE_FK_FEATURE_MD5 = 0;
    /** foreign key fl_face(image_md5) -> fl_image */
    public static final int FL_FACE_FK_IMAGE_MD5 = 1;
    /** foreign key fl_feature(person_id) -> fl_person */
    public static final int FL_FEATURE_FK_PERSON_ID = 0;
    /** foreign key fl_image(device_id) -> fl_device */
    public static final int FL_IMAGE_FK_DEVICE_ID = 0;
    /** foreign key fl_log(device_id) -> fl_device */
    public static final int FL_LOG_FK_DEVICE_ID = 0;
    /** foreign key fl_log(compare_face) -> fl_face */
    public static final int FL_LOG_FK_COMPARE_FACE = 1;
    /** foreign key fl_log(verify_feature) -> fl_feature */
    public static final int FL_LOG_FK_VERIFY_FEATURE = 2;
    /** foreign key fl_log(person_id) -> fl_person */
    public static final int FL_LOG_FK_PERSON_ID = 3;
    /** foreign key fl_person(image_md5) -> fl_image */
    public static final int FL_PERSON_FK_IMAGE_MD5 = 0;
    //////////////////////////////////////
    // IMPORTED KEY INDEX DECLARE
    //////////////////////////////////////    
    /** imported key fl_image(device_id) -> fl_device */
    public static final int FL_DEVICE_IK_FL_IMAGE_DEVICE_ID = 0;
    /** imported key fl_log(device_id) -> fl_device */
    public static final int FL_DEVICE_IK_FL_LOG_DEVICE_ID = 1;
    /** imported key fl_log(compare_face) -> fl_face */
    public static final int FL_FACE_IK_FL_LOG_COMPARE_FACE = 0;
    /** imported key fl_face(feature_md5) -> fl_feature */
    public static final int FL_FEATURE_IK_FL_FACE_FEATURE_MD5 = 0;
    /** imported key fl_log(verify_feature) -> fl_feature */
    public static final int FL_FEATURE_IK_FL_LOG_VERIFY_FEATURE = 1;
    /** imported key fl_face(image_md5) -> fl_image */
    public static final int FL_IMAGE_IK_FL_FACE_IMAGE_MD5 = 0;
    /** imported key fl_person(image_md5) -> fl_image */
    public static final int FL_IMAGE_IK_FL_PERSON_IMAGE_MD5 = 1;
    /** imported key fl_feature(person_id) -> fl_person */
    public static final int FL_PERSON_IK_FL_FEATURE_PERSON_ID = 0;
    /** imported key fl_log(person_id) -> fl_person */
    public static final int FL_PERSON_IK_FL_LOG_PERSON_ID = 1;
    //////////////////////////////////////
    // INDEX INDEX DECLARE
    //////////////////////////////////////    
    /** fl_device index (mac) */
    public static final int FL_DEVICE_INDEX_MAC = 0;
    /** fl_device index (serial_no) */
    public static final int FL_DEVICE_INDEX_SERIAL_NO = 1;
    /** fl_device index (group_id) */
    public static final int FL_DEVICE_INDEX_GROUP_ID = 2;
    /** fl_face index (feature_md5) */
    public static final int FL_FACE_INDEX_FEATURE_MD5 = 0;
    /** fl_face index (image_md5) */
    public static final int FL_FACE_INDEX_IMAGE_MD5 = 1;
    /** fl_feature index (person_id) */
    public static final int FL_FEATURE_INDEX_PERSON_ID = 0;
    /** fl_image index (device_id) */
    public static final int FL_IMAGE_INDEX_DEVICE_ID = 0;
    /** fl_log index (compare_face) */
    public static final int FL_LOG_INDEX_COMPARE_FACE = 0;
    /** fl_log index (device_id) */
    public static final int FL_LOG_INDEX_DEVICE_ID = 1;
    /** fl_log index (person_id) */
    public static final int FL_LOG_INDEX_PERSON_ID = 2;
    /** fl_log index (verify_feature) */
    public static final int FL_LOG_INDEX_VERIFY_FEATURE = 3;
    /** fl_person index (image_md5) */
    public static final int FL_PERSON_INDEX_IMAGE_MD5 = 0;
    /** fl_person index (papers_num) */
    public static final int FL_PERSON_INDEX_PAPERS_NUM = 1;
    /** fl_person index (expiry_date) */
    public static final int FL_PERSON_INDEX_EXPIRY_DATE = 2;
    /** fl_person index (group_id) */
    public static final int FL_PERSON_INDEX_GROUP_ID = 3;
    //////////////////////////////////////
    // COLUMN ID DECLARE
    //////////////////////////////////////    
    /** Identify the fl_device.id field (ordinal:1). */
    public static final int FL_DEVICE_ID_ID = 0;
    public static final long FL_DEVICE_ID_ID_MASK = 1L << 0;
    /** Identify the fl_device.name field (ordinal:2). */
    public static final int FL_DEVICE_ID_NAME = 1;
    public static final long FL_DEVICE_ID_NAME_MASK = 1L << 1;
    /** Identify the fl_device.group_id field (ordinal:3). */
    public static final int FL_DEVICE_ID_GROUP_ID = 2;
    public static final long FL_DEVICE_ID_GROUP_ID_MASK = 1L << 2;
    /** Identify the fl_device.version field (ordinal:4). */
    public static final int FL_DEVICE_ID_VERSION = 3;
    public static final long FL_DEVICE_ID_VERSION_MASK = 1L << 3;
    /** Identify the fl_device.serial_no field (ordinal:5). */
    public static final int FL_DEVICE_ID_SERIAL_NO = 4;
    public static final long FL_DEVICE_ID_SERIAL_NO_MASK = 1L << 4;
    /** Identify the fl_device.mac field (ordinal:6). */
    public static final int FL_DEVICE_ID_MAC = 5;
    public static final long FL_DEVICE_ID_MAC_MASK = 1L << 5;
    /** Identify the fl_device.create_time field (ordinal:7). */
    public static final int FL_DEVICE_ID_CREATE_TIME = 6;
    public static final long FL_DEVICE_ID_CREATE_TIME_MASK = 1L << 6;
    /** Identify the fl_device.update_time field (ordinal:8). */
    public static final int FL_DEVICE_ID_UPDATE_TIME = 7;
    public static final long FL_DEVICE_ID_UPDATE_TIME_MASK = 1L << 7;
    /** Identify the fl_face.id field (ordinal:1). */
    public static final int FL_FACE_ID_ID = 0;
    public static final long FL_FACE_ID_ID_MASK = 1L << 0;
    /** Identify the fl_face.image_md5 field (ordinal:2). */
    public static final int FL_FACE_ID_IMAGE_MD5 = 1;
    public static final long FL_FACE_ID_IMAGE_MD5_MASK = 1L << 1;
    /** Identify the fl_face.face_left field (ordinal:3). */
    public static final int FL_FACE_ID_FACE_LEFT = 2;
    public static final long FL_FACE_ID_FACE_LEFT_MASK = 1L << 2;
    /** Identify the fl_face.face_top field (ordinal:4). */
    public static final int FL_FACE_ID_FACE_TOP = 3;
    public static final long FL_FACE_ID_FACE_TOP_MASK = 1L << 3;
    /** Identify the fl_face.face_width field (ordinal:5). */
    public static final int FL_FACE_ID_FACE_WIDTH = 4;
    public static final long FL_FACE_ID_FACE_WIDTH_MASK = 1L << 4;
    /** Identify the fl_face.face_height field (ordinal:6). */
    public static final int FL_FACE_ID_FACE_HEIGHT = 5;
    public static final long FL_FACE_ID_FACE_HEIGHT_MASK = 1L << 5;
    /** Identify the fl_face.eye_leftx field (ordinal:7). */
    public static final int FL_FACE_ID_EYE_LEFTX = 6;
    public static final long FL_FACE_ID_EYE_LEFTX_MASK = 1L << 6;
    /** Identify the fl_face.eye_lefty field (ordinal:8). */
    public static final int FL_FACE_ID_EYE_LEFTY = 7;
    public static final long FL_FACE_ID_EYE_LEFTY_MASK = 1L << 7;
    /** Identify the fl_face.eye_rightx field (ordinal:9). */
    public static final int FL_FACE_ID_EYE_RIGHTX = 8;
    public static final long FL_FACE_ID_EYE_RIGHTX_MASK = 1L << 8;
    /** Identify the fl_face.eye_righty field (ordinal:10). */
    public static final int FL_FACE_ID_EYE_RIGHTY = 9;
    public static final long FL_FACE_ID_EYE_RIGHTY_MASK = 1L << 9;
    /** Identify the fl_face.mouth_x field (ordinal:11). */
    public static final int FL_FACE_ID_MOUTH_X = 10;
    public static final long FL_FACE_ID_MOUTH_X_MASK = 1L << 10;
    /** Identify the fl_face.mouth_y field (ordinal:12). */
    public static final int FL_FACE_ID_MOUTH_Y = 11;
    public static final long FL_FACE_ID_MOUTH_Y_MASK = 1L << 11;
    /** Identify the fl_face.nose_x field (ordinal:13). */
    public static final int FL_FACE_ID_NOSE_X = 12;
    public static final long FL_FACE_ID_NOSE_X_MASK = 1L << 12;
    /** Identify the fl_face.nose_y field (ordinal:14). */
    public static final int FL_FACE_ID_NOSE_Y = 13;
    public static final long FL_FACE_ID_NOSE_Y_MASK = 1L << 13;
    /** Identify the fl_face.angle_yaw field (ordinal:15). */
    public static final int FL_FACE_ID_ANGLE_YAW = 14;
    public static final long FL_FACE_ID_ANGLE_YAW_MASK = 1L << 14;
    /** Identify the fl_face.angle_pitch field (ordinal:16). */
    public static final int FL_FACE_ID_ANGLE_PITCH = 15;
    public static final long FL_FACE_ID_ANGLE_PITCH_MASK = 1L << 15;
    /** Identify the fl_face.angle_roll field (ordinal:17). */
    public static final int FL_FACE_ID_ANGLE_ROLL = 16;
    public static final long FL_FACE_ID_ANGLE_ROLL_MASK = 1L << 16;
    /** Identify the fl_face.ext_info field (ordinal:18). */
    public static final int FL_FACE_ID_EXT_INFO = 17;
    public static final long FL_FACE_ID_EXT_INFO_MASK = 1L << 17;
    /** Identify the fl_face.feature_md5 field (ordinal:19). */
    public static final int FL_FACE_ID_FEATURE_MD5 = 18;
    public static final long FL_FACE_ID_FEATURE_MD5_MASK = 1L << 18;
    /** Identify the fl_face.create_time field (ordinal:20). */
    public static final int FL_FACE_ID_CREATE_TIME = 19;
    public static final long FL_FACE_ID_CREATE_TIME_MASK = 1L << 19;
    /** Identify the fl_feature.md5 field (ordinal:1). */
    public static final int FL_FEATURE_ID_MD5 = 0;
    public static final long FL_FEATURE_ID_MD5_MASK = 1L << 0;
    /** Identify the fl_feature.person_id field (ordinal:2). */
    public static final int FL_FEATURE_ID_PERSON_ID = 1;
    public static final long FL_FEATURE_ID_PERSON_ID_MASK = 1L << 1;
    /** Identify the fl_feature.feature field (ordinal:3). */
    public static final int FL_FEATURE_ID_FEATURE = 2;
    public static final long FL_FEATURE_ID_FEATURE_MASK = 1L << 2;
    /** Identify the fl_feature.update_time field (ordinal:4). */
    public static final int FL_FEATURE_ID_UPDATE_TIME = 3;
    public static final long FL_FEATURE_ID_UPDATE_TIME_MASK = 1L << 3;
    /** Identify the fl_image.md5 field (ordinal:1). */
    public static final int FL_IMAGE_ID_MD5 = 0;
    public static final long FL_IMAGE_ID_MD5_MASK = 1L << 0;
    /** Identify the fl_image.format field (ordinal:2). */
    public static final int FL_IMAGE_ID_FORMAT = 1;
    public static final long FL_IMAGE_ID_FORMAT_MASK = 1L << 1;
    /** Identify the fl_image.width field (ordinal:3). */
    public static final int FL_IMAGE_ID_WIDTH = 2;
    public static final long FL_IMAGE_ID_WIDTH_MASK = 1L << 2;
    /** Identify the fl_image.height field (ordinal:4). */
    public static final int FL_IMAGE_ID_HEIGHT = 3;
    public static final long FL_IMAGE_ID_HEIGHT_MASK = 1L << 3;
    /** Identify the fl_image.depth field (ordinal:5). */
    public static final int FL_IMAGE_ID_DEPTH = 4;
    public static final long FL_IMAGE_ID_DEPTH_MASK = 1L << 4;
    /** Identify the fl_image.face_num field (ordinal:6). */
    public static final int FL_IMAGE_ID_FACE_NUM = 5;
    public static final long FL_IMAGE_ID_FACE_NUM_MASK = 1L << 5;
    /** Identify the fl_image.thumb_md5 field (ordinal:7). */
    public static final int FL_IMAGE_ID_THUMB_MD5 = 6;
    public static final long FL_IMAGE_ID_THUMB_MD5_MASK = 1L << 6;
    /** Identify the fl_image.device_id field (ordinal:8). */
    public static final int FL_IMAGE_ID_DEVICE_ID = 7;
    public static final long FL_IMAGE_ID_DEVICE_ID_MASK = 1L << 7;
    /** Identify the fl_log.id field (ordinal:1). */
    public static final int FL_LOG_ID_ID = 0;
    public static final long FL_LOG_ID_ID_MASK = 1L << 0;
    /** Identify the fl_log.person_id field (ordinal:2). */
    public static final int FL_LOG_ID_PERSON_ID = 1;
    public static final long FL_LOG_ID_PERSON_ID_MASK = 1L << 1;
    /** Identify the fl_log.device_id field (ordinal:3). */
    public static final int FL_LOG_ID_DEVICE_ID = 2;
    public static final long FL_LOG_ID_DEVICE_ID_MASK = 1L << 2;
    /** Identify the fl_log.verify_feature field (ordinal:4). */
    public static final int FL_LOG_ID_VERIFY_FEATURE = 3;
    public static final long FL_LOG_ID_VERIFY_FEATURE_MASK = 1L << 3;
    /** Identify the fl_log.compare_face field (ordinal:5). */
    public static final int FL_LOG_ID_COMPARE_FACE = 4;
    public static final long FL_LOG_ID_COMPARE_FACE_MASK = 1L << 4;
    /** Identify the fl_log.similarty field (ordinal:6). */
    public static final int FL_LOG_ID_SIMILARTY = 5;
    public static final long FL_LOG_ID_SIMILARTY_MASK = 1L << 5;
    /** Identify the fl_log.verify_time field (ordinal:7). */
    public static final int FL_LOG_ID_VERIFY_TIME = 6;
    public static final long FL_LOG_ID_VERIFY_TIME_MASK = 1L << 6;
    /** Identify the fl_log.create_time field (ordinal:8). */
    public static final int FL_LOG_ID_CREATE_TIME = 7;
    public static final long FL_LOG_ID_CREATE_TIME_MASK = 1L << 7;
    /** Identify the fl_person.id field (ordinal:1). */
    public static final int FL_PERSON_ID_ID = 0;
    public static final long FL_PERSON_ID_ID_MASK = 1L << 0;
    /** Identify the fl_person.group_id field (ordinal:2). */
    public static final int FL_PERSON_ID_GROUP_ID = 1;
    public static final long FL_PERSON_ID_GROUP_ID_MASK = 1L << 1;
    /** Identify the fl_person.name field (ordinal:3). */
    public static final int FL_PERSON_ID_NAME = 2;
    public static final long FL_PERSON_ID_NAME_MASK = 1L << 2;
    /** Identify the fl_person.sex field (ordinal:4). */
    public static final int FL_PERSON_ID_SEX = 3;
    public static final long FL_PERSON_ID_SEX_MASK = 1L << 3;
    /** Identify the fl_person.birthdate field (ordinal:5). */
    public static final int FL_PERSON_ID_BIRTHDATE = 4;
    public static final long FL_PERSON_ID_BIRTHDATE_MASK = 1L << 4;
    /** Identify the fl_person.papers_type field (ordinal:6). */
    public static final int FL_PERSON_ID_PAPERS_TYPE = 5;
    public static final long FL_PERSON_ID_PAPERS_TYPE_MASK = 1L << 5;
    /** Identify the fl_person.papers_num field (ordinal:7). */
    public static final int FL_PERSON_ID_PAPERS_NUM = 6;
    public static final long FL_PERSON_ID_PAPERS_NUM_MASK = 1L << 6;
    /** Identify the fl_person.image_md5 field (ordinal:8). */
    public static final int FL_PERSON_ID_IMAGE_MD5 = 7;
    public static final long FL_PERSON_ID_IMAGE_MD5_MASK = 1L << 7;
    /** Identify the fl_person.expiry_date field (ordinal:9). */
    public static final int FL_PERSON_ID_EXPIRY_DATE = 8;
    public static final long FL_PERSON_ID_EXPIRY_DATE_MASK = 1L << 8;
    /** Identify the fl_person.create_time field (ordinal:10). */
    public static final int FL_PERSON_ID_CREATE_TIME = 9;
    public static final long FL_PERSON_ID_CREATE_TIME_MASK = 1L << 9;
    /** Identify the fl_person.update_time field (ordinal:11). */
    public static final int FL_PERSON_ID_UPDATE_TIME = 10;
    public static final long FL_PERSON_ID_UPDATE_TIME_MASK = 1L << 10;
    /** Identify the fl_store.md5 field (ordinal:1). */
    public static final int FL_STORE_ID_MD5 = 0;
    public static final long FL_STORE_ID_MD5_MASK = 1L << 0;
    /** Identify the fl_store.encoding field (ordinal:2). */
    public static final int FL_STORE_ID_ENCODING = 1;
    public static final long FL_STORE_ID_ENCODING_MASK = 1L << 1;
    /** Identify the fl_store.data field (ordinal:3). */
    public static final int FL_STORE_ID_DATA = 2;
    public static final long FL_STORE_ID_DATA_MASK = 1L << 2;
    /** Identify the fl_log_light.id field (ordinal:1). */
    public static final int FL_LOG_LIGHT_ID_ID = 0;
    public static final long FL_LOG_LIGHT_ID_ID_MASK = 1L << 0;
    /** Identify the fl_log_light.person_id field (ordinal:2). */
    public static final int FL_LOG_LIGHT_ID_PERSON_ID = 1;
    public static final long FL_LOG_LIGHT_ID_PERSON_ID_MASK = 1L << 1;
    /** Identify the fl_log_light.name field (ordinal:3). */
    public static final int FL_LOG_LIGHT_ID_NAME = 2;
    public static final long FL_LOG_LIGHT_ID_NAME_MASK = 1L << 2;
    /** Identify the fl_log_light.papers_type field (ordinal:4). */
    public static final int FL_LOG_LIGHT_ID_PAPERS_TYPE = 3;
    public static final long FL_LOG_LIGHT_ID_PAPERS_TYPE_MASK = 1L << 3;
    /** Identify the fl_log_light.papers_num field (ordinal:5). */
    public static final int FL_LOG_LIGHT_ID_PAPERS_NUM = 4;
    public static final long FL_LOG_LIGHT_ID_PAPERS_NUM_MASK = 1L << 4;
    /** Identify the fl_log_light.verify_time field (ordinal:6). */
    public static final int FL_LOG_LIGHT_ID_VERIFY_TIME = 5;
    public static final long FL_LOG_LIGHT_ID_VERIFY_TIME_MASK = 1L << 5;
    //////////////////////////////////////
    // COLUMN NAME DECLARE
    //////////////////////////////////////    
    /////////////////// fl_device ////////////
    /** Contains all the full fields of the fl_device table.*/
    public static final String FL_DEVICE_FULL_FIELDS ="fl_device.id"
                            + ",fl_device.name"
                            + ",fl_device.group_id"
                            + ",fl_device.version"
                            + ",fl_device.serial_no"
                            + ",fl_device.mac"
                            + ",fl_device.create_time"
                            + ",fl_device.update_time";
    /** Field that contains the comma separated fields of the fl_device table. */
    public static final String FL_DEVICE_FIELDS = "id"
                            + ",name"
                            + ",group_id"
                            + ",version"
                            + ",serial_no"
                            + ",mac"
                            + ",create_time"
                            + ",update_time";
    public static final java.util.List<String> FL_DEVICE_FIELDS_LIST = java.util.Arrays.asList(FL_DEVICE_FIELDS.split(","));
    /** Field that contains the comma separated java fields of the fl_device table. */
    public static final String FL_DEVICE_JAVA_FIELDS = "id"
                            + ",name"
                            + ",groupId"
                            + ",version"
                            + ",serialNo"
                            + ",mac"
                            + ",createTime"
                            + ",updateTime";
    public static final java.util.List<String> FL_DEVICE_JAVA_FIELDS_LIST = java.util.Arrays.asList(FL_DEVICE_JAVA_FIELDS.split(","));
    /////////////////// fl_face ////////////
    /** Contains all the full fields of the fl_face table.*/
    public static final String FL_FACE_FULL_FIELDS ="fl_face.id"
                            + ",fl_face.image_md5"
                            + ",fl_face.face_left"
                            + ",fl_face.face_top"
                            + ",fl_face.face_width"
                            + ",fl_face.face_height"
                            + ",fl_face.eye_leftx"
                            + ",fl_face.eye_lefty"
                            + ",fl_face.eye_rightx"
                            + ",fl_face.eye_righty"
                            + ",fl_face.mouth_x"
                            + ",fl_face.mouth_y"
                            + ",fl_face.nose_x"
                            + ",fl_face.nose_y"
                            + ",fl_face.angle_yaw"
                            + ",fl_face.angle_pitch"
                            + ",fl_face.angle_roll"
                            + ",fl_face.ext_info"
                            + ",fl_face.feature_md5"
                            + ",fl_face.create_time";
    /** Field that contains the comma separated fields of the fl_face table. */
    public static final String FL_FACE_FIELDS = "id"
                            + ",image_md5"
                            + ",face_left"
                            + ",face_top"
                            + ",face_width"
                            + ",face_height"
                            + ",eye_leftx"
                            + ",eye_lefty"
                            + ",eye_rightx"
                            + ",eye_righty"
                            + ",mouth_x"
                            + ",mouth_y"
                            + ",nose_x"
                            + ",nose_y"
                            + ",angle_yaw"
                            + ",angle_pitch"
                            + ",angle_roll"
                            + ",ext_info"
                            + ",feature_md5"
                            + ",create_time";
    public static final java.util.List<String> FL_FACE_FIELDS_LIST = java.util.Arrays.asList(FL_FACE_FIELDS.split(","));
    /** Field that contains the comma separated java fields of the fl_face table. */
    public static final String FL_FACE_JAVA_FIELDS = "id"
                            + ",imageMd5"
                            + ",faceLeft"
                            + ",faceTop"
                            + ",faceWidth"
                            + ",faceHeight"
                            + ",eyeLeftx"
                            + ",eyeLefty"
                            + ",eyeRightx"
                            + ",eyeRighty"
                            + ",mouthX"
                            + ",mouthY"
                            + ",noseX"
                            + ",noseY"
                            + ",angleYaw"
                            + ",anglePitch"
                            + ",angleRoll"
                            + ",extInfo"
                            + ",featureMd5"
                            + ",createTime";
    public static final java.util.List<String> FL_FACE_JAVA_FIELDS_LIST = java.util.Arrays.asList(FL_FACE_JAVA_FIELDS.split(","));
    /////////////////// fl_feature ////////////
    /** Contains all the full fields of the fl_feature table.*/
    public static final String FL_FEATURE_FULL_FIELDS ="fl_feature.md5"
                            + ",fl_feature.person_id"
                            + ",fl_feature.feature"
                            + ",fl_feature.update_time";
    /** Field that contains the comma separated fields of the fl_feature table. */
    public static final String FL_FEATURE_FIELDS = "md5"
                            + ",person_id"
                            + ",feature"
                            + ",update_time";
    public static final java.util.List<String> FL_FEATURE_FIELDS_LIST = java.util.Arrays.asList(FL_FEATURE_FIELDS.split(","));
    /** Field that contains the comma separated java fields of the fl_feature table. */
    public static final String FL_FEATURE_JAVA_FIELDS = "md5"
                            + ",personId"
                            + ",feature"
                            + ",updateTime";
    public static final java.util.List<String> FL_FEATURE_JAVA_FIELDS_LIST = java.util.Arrays.asList(FL_FEATURE_JAVA_FIELDS.split(","));
    /////////////////// fl_image ////////////
    /** Contains all the full fields of the fl_image table.*/
    public static final String FL_IMAGE_FULL_FIELDS ="fl_image.md5"
                            + ",fl_image.format"
                            + ",fl_image.width"
                            + ",fl_image.height"
                            + ",fl_image.depth"
                            + ",fl_image.face_num"
                            + ",fl_image.thumb_md5"
                            + ",fl_image.device_id";
    /** Field that contains the comma separated fields of the fl_image table. */
    public static final String FL_IMAGE_FIELDS = "md5"
                            + ",format"
                            + ",width"
                            + ",height"
                            + ",depth"
                            + ",face_num"
                            + ",thumb_md5"
                            + ",device_id";
    public static final java.util.List<String> FL_IMAGE_FIELDS_LIST = java.util.Arrays.asList(FL_IMAGE_FIELDS.split(","));
    /** Field that contains the comma separated java fields of the fl_image table. */
    public static final String FL_IMAGE_JAVA_FIELDS = "md5"
                            + ",format"
                            + ",width"
                            + ",height"
                            + ",depth"
                            + ",faceNum"
                            + ",thumbMd5"
                            + ",deviceId";
    public static final java.util.List<String> FL_IMAGE_JAVA_FIELDS_LIST = java.util.Arrays.asList(FL_IMAGE_JAVA_FIELDS.split(","));
    /////////////////// fl_log ////////////
    /** Contains all the full fields of the fl_log table.*/
    public static final String FL_LOG_FULL_FIELDS ="fl_log.id"
                            + ",fl_log.person_id"
                            + ",fl_log.device_id"
                            + ",fl_log.verify_feature"
                            + ",fl_log.compare_face"
                            + ",fl_log.similarty"
                            + ",fl_log.verify_time"
                            + ",fl_log.create_time";
    /** Field that contains the comma separated fields of the fl_log table. */
    public static final String FL_LOG_FIELDS = "id"
                            + ",person_id"
                            + ",device_id"
                            + ",verify_feature"
                            + ",compare_face"
                            + ",similarty"
                            + ",verify_time"
                            + ",create_time";
    public static final java.util.List<String> FL_LOG_FIELDS_LIST = java.util.Arrays.asList(FL_LOG_FIELDS.split(","));
    /** Field that contains the comma separated java fields of the fl_log table. */
    public static final String FL_LOG_JAVA_FIELDS = "id"
                            + ",personId"
                            + ",deviceId"
                            + ",verifyFeature"
                            + ",compareFace"
                            + ",similarty"
                            + ",verifyTime"
                            + ",createTime";
    public static final java.util.List<String> FL_LOG_JAVA_FIELDS_LIST = java.util.Arrays.asList(FL_LOG_JAVA_FIELDS.split(","));
    /////////////////// fl_person ////////////
    /** Contains all the full fields of the fl_person table.*/
    public static final String FL_PERSON_FULL_FIELDS ="fl_person.id"
                            + ",fl_person.group_id"
                            + ",fl_person.name"
                            + ",fl_person.sex"
                            + ",fl_person.birthdate"
                            + ",fl_person.papers_type"
                            + ",fl_person.papers_num"
                            + ",fl_person.image_md5"
                            + ",fl_person.expiry_date"
                            + ",fl_person.create_time"
                            + ",fl_person.update_time";
    /** Field that contains the comma separated fields of the fl_person table. */
    public static final String FL_PERSON_FIELDS = "id"
                            + ",group_id"
                            + ",name"
                            + ",sex"
                            + ",birthdate"
                            + ",papers_type"
                            + ",papers_num"
                            + ",image_md5"
                            + ",expiry_date"
                            + ",create_time"
                            + ",update_time";
    public static final java.util.List<String> FL_PERSON_FIELDS_LIST = java.util.Arrays.asList(FL_PERSON_FIELDS.split(","));
    /** Field that contains the comma separated java fields of the fl_person table. */
    public static final String FL_PERSON_JAVA_FIELDS = "id"
                            + ",groupId"
                            + ",name"
                            + ",sex"
                            + ",birthdate"
                            + ",papersType"
                            + ",papersNum"
                            + ",imageMd5"
                            + ",expiryDate"
                            + ",createTime"
                            + ",updateTime";
    public static final java.util.List<String> FL_PERSON_JAVA_FIELDS_LIST = java.util.Arrays.asList(FL_PERSON_JAVA_FIELDS.split(","));
    /////////////////// fl_store ////////////
    /** Contains all the full fields of the fl_store table.*/
    public static final String FL_STORE_FULL_FIELDS ="fl_store.md5"
                            + ",fl_store.encoding"
                            + ",fl_store.data";
    /** Field that contains the comma separated fields of the fl_store table. */
    public static final String FL_STORE_FIELDS = "md5"
                            + ",encoding"
                            + ",data";
    public static final java.util.List<String> FL_STORE_FIELDS_LIST = java.util.Arrays.asList(FL_STORE_FIELDS.split(","));
    /** Field that contains the comma separated java fields of the fl_store table. */
    public static final String FL_STORE_JAVA_FIELDS = "md5"
                            + ",encoding"
                            + ",data";
    public static final java.util.List<String> FL_STORE_JAVA_FIELDS_LIST = java.util.Arrays.asList(FL_STORE_JAVA_FIELDS.split(","));
    /////////////////// fl_log_light ////////////
    /** Contains all the full fields of the fl_log_light table.*/
    public static final String FL_LOG_LIGHT_FULL_FIELDS ="fl_log_light.id"
                            + ",fl_log_light.person_id"
                            + ",fl_log_light.name"
                            + ",fl_log_light.papers_type"
                            + ",fl_log_light.papers_num"
                            + ",fl_log_light.verify_time";
    /** Field that contains the comma separated fields of the fl_log_light table. */
    public static final String FL_LOG_LIGHT_FIELDS = "id"
                            + ",person_id"
                            + ",name"
                            + ",papers_type"
                            + ",papers_num"
                            + ",verify_time";
    public static final java.util.List<String> FL_LOG_LIGHT_FIELDS_LIST = java.util.Arrays.asList(FL_LOG_LIGHT_FIELDS.split(","));
    /** Field that contains the comma separated java fields of the fl_log_light table. */
    public static final String FL_LOG_LIGHT_JAVA_FIELDS = "id"
                            + ",personId"
                            + ",name"
                            + ",papersType"
                            + ",papersNum"
                            + ",verifyTime";
    public static final java.util.List<String> FL_LOG_LIGHT_JAVA_FIELDS_LIST = java.util.Arrays.asList(FL_LOG_LIGHT_JAVA_FIELDS.split(","));
}
