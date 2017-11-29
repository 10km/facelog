// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: command.adapter.container.java.vm
// ______________________________________________________

package net.gdface.facelog.client;

import java.net.URL;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * 设备命令执行器容器对象<br>
 * 允许应用项目用不同的{@link CommandAdapter}对象分别实现设备命令
 * @author guyadong
 *
 */
public class CommandAdapterContainer extends CommandAdapter{
    /** 设备命令执行器对象映射,每一个设备命令对应一个执行器对象 */
    private final Map<Cmd, CommandAdapter> adapters= Collections.synchronizedMap(new EnumMap<Cmd, CommandAdapter>(Cmd.class));
    
    public CommandAdapterContainer() {
        this(null);
    }
    public CommandAdapterContainer(Map<Cmd, CommandAdapter> adapters) {
        if(null != adapters){
            this.adapters.putAll(adapters);
        }
    }
    /**
     * 返回{@code cmd}注册的命令执行器对象,如果没有返回{@code null}
     * @param cmd
     * @return
     */
    public CommandAdapter adapterOf(Cmd cmd) {
        return adapters.get(cmd);
    }
    /**
     * 注册指定命令({@code cmd})的命令执行器
     * @param cmd 设备命令类型,不可为{@code null}
     * @param adapter  命令执行器,不可为{@code null}
     * @return
     * @see {@link EnumMap#put(Enum, Object)}
     */
    public CommandAdapterContainer register(Cmd cmd, CommandAdapter adapter) {
    	checkArgument(null != adapter,"adapter is null");
    	// cycle check 不允许循环调用
    	checkArgument(! (adapter instanceof CommandAdapterContainer),"INVALID loop register");
        adapters.put(checkNotNull(cmd,"key is null"), adapter);
        return this;
    }
    /**
     * 注销{@code cmd}指定的命令执行器
     * @param cmd
     * @return 返回被删除的命令执行器
     * @see {@link EnumMap#remove(Object)}
     */
    public CommandAdapterContainer unregister(Cmd cmd) {
        adapters.remove(cmd);
        return this;
    }
    
    /**
     * 删除所有命令执行器
     * @see {@link EnumMap#clear()}
     */
    public void clear() {
        adapters.clear();
    }
    /** 
     * 调用 {@link #parameterAdapter} 命令执行器<br>
     * 如果 {@link #parameterAdapter} 为 {@code null},则调用父类方法抛出{@link UnsupportCmdExeption}异常
     */
    @Override
    public void parameter(String key,String value)throws DeviceCmdException{
        if(this.adapters.containsKey(Cmd.parameter)){
            this.adapters.get(Cmd.parameter).parameter(key,value);
        }else{
            super.parameter(key,value);
        }
    }
    /** 
     * 调用 {@link #configAdapter} 命令执行器<br>
     * 如果 {@link #configAdapter} 为 {@code null},则调用父类方法抛出{@link UnsupportCmdExeption}异常
     */
    @Override
    public void config(Map<String,String> properties)throws DeviceCmdException{
        if(this.adapters.containsKey(Cmd.config)){
            this.adapters.get(Cmd.config).config(properties);
        }else{
            super.config(properties);
        }
    }
    /** 
     * 调用 {@link #statusAdapter} 命令执行器<br>
     * 如果 {@link #statusAdapter} 为 {@code null},则调用父类方法抛出{@link UnsupportCmdExeption}异常
     */
    @Override
    public Object status(String name)throws DeviceCmdException{
        return this.adapters.containsKey(Cmd.status)
            ? super.status(name)
            : this.adapters.get(Cmd.status).status(name);
    }
    /** 
     * 调用 {@link #reportAdapter} 命令执行器<br>
     * 如果 {@link #reportAdapter} 为 {@code null},则调用父类方法抛出{@link UnsupportCmdExeption}异常
     */
    @Override
    public Map<String,Object> report(List<String> names)throws DeviceCmdException{
        return this.adapters.containsKey(Cmd.report)
            ? super.report(names)
            : this.adapters.get(Cmd.report).report(names);
    }
    /** 
     * 调用 {@link #versionAdapter} 命令执行器<br>
     * 如果 {@link #versionAdapter} 为 {@code null},则调用父类方法抛出{@link UnsupportCmdExeption}异常
     */
    @Override
    public String version()throws DeviceCmdException{
        return this.adapters.containsKey(Cmd.version)
            ? super.version()
            : this.adapters.get(Cmd.version).version();
    }
    /** 
     * 调用 {@link #enableAdapter} 命令执行器<br>
     * 如果 {@link #enableAdapter} 为 {@code null},则调用父类方法抛出{@link UnsupportCmdExeption}异常
     */
    @Override
    public void enable(Boolean enable)throws DeviceCmdException{
        if(this.adapters.containsKey(Cmd.enable)){
            this.adapters.get(Cmd.enable).enable(enable);
        }else{
            super.enable(enable);
        }
    }
    /** 
     * 调用 {@link #isEnableAdapter} 命令执行器<br>
     * 如果 {@link #isEnableAdapter} 为 {@code null},则调用父类方法抛出{@link UnsupportCmdExeption}异常
     */
    @Override
    public Boolean isEnable(String message)throws DeviceCmdException{
        return this.adapters.containsKey(Cmd.isEnable)
            ? super.isEnable(message)
            : this.adapters.get(Cmd.isEnable).isEnable(message);
    }
    /** 
     * 调用 {@link #resetAdapter} 命令执行器<br>
     * 如果 {@link #resetAdapter} 为 {@code null},则调用父类方法抛出{@link UnsupportCmdExeption}异常
     */
    @Override
    public void reset(Long schedule)throws DeviceCmdException{
        if(this.adapters.containsKey(Cmd.reset)){
            this.adapters.get(Cmd.reset).reset(schedule);
        }else{
            super.reset(schedule);
        }
    }
    /** 
     * 调用 {@link #timeAdapter} 命令执行器<br>
     * 如果 {@link #timeAdapter} 为 {@code null},则调用父类方法抛出{@link UnsupportCmdExeption}异常
     */
    @Override
    public void time(Long unixTimestamp)throws DeviceCmdException{
        if(this.adapters.containsKey(Cmd.time)){
            this.adapters.get(Cmd.time).time(unixTimestamp);
        }else{
            super.time(unixTimestamp);
        }
    }
    /** 
     * 调用 {@link #updateAdapter} 命令执行器<br>
     * 如果 {@link #updateAdapter} 为 {@code null},则调用父类方法抛出{@link UnsupportCmdExeption}异常
     */
    @Override
    public void update(URL url,String version,Long schedule)throws DeviceCmdException{
        if(this.adapters.containsKey(Cmd.update)){
            this.adapters.get(Cmd.update).update(url,version,schedule);
        }else{
            super.update(url,version,schedule);
        }
    }
    /** 
     * 调用 {@link #idleMessageAdapter} 命令执行器<br>
     * 如果 {@link #idleMessageAdapter} 为 {@code null},则调用父类方法抛出{@link UnsupportCmdExeption}异常
     */
    @Override
    public void idleMessage(String message,Long duration)throws DeviceCmdException{
        if(this.adapters.containsKey(Cmd.idleMessage)){
            this.adapters.get(Cmd.idleMessage).idleMessage(message,duration);
        }else{
            super.idleMessage(message,duration);
        }
    }
    /** 
     * 调用 {@link #personMessageAdapter} 命令执行器<br>
     * 如果 {@link #personMessageAdapter} 为 {@code null},则调用父类方法抛出{@link UnsupportCmdExeption}异常
     */
    @Override
    public void personMessage(String message,Integer id,Boolean group,Boolean onceOnly,Long duration)throws DeviceCmdException{
        if(this.adapters.containsKey(Cmd.personMessage)){
            this.adapters.get(Cmd.personMessage).personMessage(message,id,group,onceOnly,duration);
        }else{
            super.personMessage(message,id,group,onceOnly,duration);
        }
    }
    /** 
     * 调用 {@link #customAdapter} 命令执行器<br>
     * 如果 {@link #customAdapter} 为 {@code null},则调用父类方法抛出{@link UnsupportCmdExeption}异常
     */
    @Override
    public Object custom(String cmdName,Map<String,Object> parameters)throws DeviceCmdException{
        return this.adapters.containsKey(Cmd.custom)
            ? super.custom(cmdName,parameters)
            : this.adapters.get(Cmd.custom).custom(cmdName,parameters);
    }
}