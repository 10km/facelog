// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: base.command.adapter.java.vm
// ______________________________________________________
package net.gdface.facelog.service;

import java.net.URL;
import java.util.Map;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;

/**
 * 命令执行基类
 * @author guyadong
 *
 */
public class BaseCommandAdapter {
    public static enum Cmd{
        /** 设备重启 */reset,
        /** 发送消息 */message,
        /** 更新版本 */update,
        /** 自定义命令 */custom;
        /**
         * 执行当前设备命令
         * @param adapter
         * @param parameters
         * @return
         */
        @SuppressWarnings("serial")
        public Ack<?> run(BaseCommandAdapter adapter,Map<String,Object> parameters){
            if(null == parameters){
                parameters = ImmutableMap.of();
            }
            switch(this){
            case reset:{
                    Ack<Void> ack = new Ack<Void>().setStatus(Ack.Status.OK);
                    try{
                        adapter.reset();
                    }catch(Exception e){
                        // 填入异常状态,设置错误信息
                        ack.setStatus(Ack.Status.ERROR).setErrorMessage(e.getMessage());
                    }                
                    return ack;
                }
            case message:{
                    Ack<Void> ack = new Ack<Void>().setStatus(Ack.Status.OK);
                    try{
                        adapter.message(
                                cast(parameters.get("message"),new TypeToken<String>(){}));
                    }catch(Exception e){
                        // 填入异常状态,设置错误信息
                        ack.setStatus(Ack.Status.ERROR).setErrorMessage(e.getMessage());
                    }                
                    return ack;
                }
            case update:{
                    Ack<Void> ack = new Ack<Void>().setStatus(Ack.Status.OK);
                    try{
                        adapter.update(
                                cast(parameters.get("url"),new TypeToken<URL>(){}),
                                cast(parameters.get("version"),new TypeToken<String>(){}));
                    }catch(Exception e){
                        // 填入异常状态,设置错误信息
                        ack.setStatus(Ack.Status.ERROR).setErrorMessage(e.getMessage());
                    }                
                    return ack;
                }
            case custom:{
                    Ack<Object> ack = new Ack<Object>().setStatus(Ack.Status.OK);
                    try{
                        Object res = adapter.custom(
                                cast(parameters.get("cmdName"),new TypeToken<String>(){}),
                                cast(parameters.get("parameters"),new TypeToken<Map<String,Object>>(){}));
                        // 填入返回值
                        ack.setValue(res);
                    }catch(Exception e){
                        // 填入异常状态,设置错误信息
                        ack.setStatus(Ack.Status.ERROR).setErrorMessage(e.getMessage());
                    }                
                    return ack;
                }
            default:
                // dead code 不会执行到这里
                throw new IllegalArgumentException();
            }
        }
    }
    @SuppressWarnings("unchecked")
    static private<T> T cast(Object value,TypeToken<T> typeToken){
        return (T)TypeUtils.cast(value,
                typeToken.getType(),
                ParserConfig.getGlobalInstance());
    }
    /**
     * 设备重启<br>
     *
     */
    public void reset(){
    }
    /**
     * 发送消息<br>
     * @param message 发送到设备的消息
     *
     */
    public void message(String message){
    }
    /**
     * 更新版本<br>
     * @param url 更新版本的位置
     * @param version 版本号
     *
     */
    public void update(URL url,String version){
    }
    /**
     * 自定义命令<br>
     * @param cmdName 自定义命令名称
     * @param parameters 自定义参数表
     *
     */
    public Object custom(String cmdName,Map<String,Object> parameters){
        return null;
    }
}