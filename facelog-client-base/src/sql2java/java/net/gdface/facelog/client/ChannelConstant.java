// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: channel.constant.java.vm
// ______________________________________________________
package net.gdface.facelog.client;

import gu.simplemq.Channel;
import net.gdface.facelog.CommonConstant;

import net.gdface.facelog.db.PermitBean;
import net.gdface.facelog.db.LogBean;
/**
 * 消息频道常量定义
 * @author guyadong
 *
 */
public interface ChannelConstant extends CommonConstant{
   /** 设备心跳包表 {@code 设备MAC地址 -> HeadbeatPackage} */
    public static final Channel<HeadbeatPackage> TABLE_HEARTBEAT = new Channel<HeadbeatPackage>("DeviceHeartbeat"){}.immutable();

    //////////////定义数据库实时更新频道/////////

    public static final Channel<Integer> PUBSUB_PERSON_INSERT = new Channel<Integer>("PersonInsert"){}.immutable();
    public static final Channel<Integer> PUBSUB_PERSON_UPDATE = new Channel<Integer>("PersonUpdate"){}.immutable();
    public static final Channel<Integer> PUBSUB_PERSON_DELETE = new Channel<Integer>("PersonDelete"){}.immutable();

    public static final Channel<String> PUBSUB_FEATURE_INSERT = new Channel<String>("FeatureInsert"){}.immutable();
    public static final Channel<String> PUBSUB_FEATURE_UPDATE = new Channel<String>("FeatureUpdate"){}.immutable();
    public static final Channel<String> PUBSUB_FEATURE_DELETE = new Channel<String>("FeatureDelete"){}.immutable();

    public static final Channel<PermitBean> PUBSUB_PERMIT_INSERT = new Channel<PermitBean>("PermitInsert"){}.immutable();
    public static final Channel<PermitBean> PUBSUB_PERMIT_UPDATE = new Channel<PermitBean>("PermitUpdate"){}.immutable();
    public static final Channel<PermitBean> PUBSUB_PERMIT_DELETE = new Channel<PermitBean>("PermitDelete"){}.immutable();

    public static final Channel<LogBean> QUEUE_LOG = new Channel<LogBean>("queueLog"){}.immutable();

}