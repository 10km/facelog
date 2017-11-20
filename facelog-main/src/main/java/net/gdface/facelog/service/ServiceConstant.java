package net.gdface.facelog.service;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

import gu.simplemq.Channel;

/**
 * 服务端常量定义
 * @author guyadong
 *
 */
public interface ServiceConstant {
	/** 设备访问令牌表,{@code 设备ID -> token}  */
    public static final Channel<Token> TABLE_DEVICE_TOKEN = new Channel<Token>("DeviceToken"){} ;
    /** 人员访问令牌表 {@code 人员ID -> token} */
    public static final Channel<Token> TABLE_PERSON_TOKEN = new Channel<Token>("PersonToken"){} ;
    /** 人员令牌失效时间(分钟) */
    public static final int DEFAULT_PERSON_TOKEN_EXPIRE = 60;
}
