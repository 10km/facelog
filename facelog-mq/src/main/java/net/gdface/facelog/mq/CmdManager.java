package net.gdface.facelog.mq;

import static com.google.common.base.Preconditions.*;

import gu.simplemq.Channel;
import gu.simplemq.redis.JedisPoolLazy;

/**
 * 
 * (多目标)设备命令发送管理模块<br>
 * @author guyadong
 *
 */
public class CmdManager extends BaseCmdManager{
    private final Channel<DeviceInstruction> cmdChannel;
    /**
     * 构造方法
     * @param poolLazy 
     * @param cmdChannelName 命令频道名
     */
    public CmdManager(JedisPoolLazy poolLazy, String cmdChannelName) {
    	super(poolLazy);
        this.cmdChannel = new Channel<DeviceInstruction>(cmdChannelName){};
    }

    /**
     * 发送前检查target是否有定义，未定义则抛出异常
     */
    @Override
    protected long doSendCmd(DeviceInstruction cmd){
        checkArgument(null != cmd.getTarget() && !cmd.getTarget().isEmpty(),"DeviceInstruction.target field must not be null");

        return publisher.publish(this.cmdChannel, cmd);
    }

}
