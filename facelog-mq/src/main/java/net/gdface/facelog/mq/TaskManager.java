package net.gdface.facelog.mq;

import static com.google.common.base.Preconditions.*;

import com.google.common.base.Supplier;

import gu.simplemq.Channel;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisConsumer;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisProducer;

/**
 * 
 * (任务队列)设备命令发送管理模块<br>
 * @author guyadong
 *
 */
public class TaskManager extends BaseCmdManager {
    private final Supplier<Channel<DeviceInstruction>> taskChannelSupplier;
    private final Supplier<String> taskQueueSupplier;
    private final RedisProducer producer;
    /**
     * 构造方法
     * @param poolLazy 
     * @param taskQueueSupplier
     */
    public TaskManager(JedisPoolLazy poolLazy, Supplier<String> taskQueueSupplier) {
    	super(poolLazy);
        this.producer = RedisFactory.getProducer(poolLazy);
        this.taskQueueSupplier = checkNotNull(taskQueueSupplier,"taskQueueSupplier is null");
        this.taskChannelSupplier = new TaskChannelSupplier();
    }

    /**
     * 发送设备命令
     * @param cmd
     * @return 收到命令的客户端数目
     */
    @Override
    protected long doSendCmd(DeviceInstruction cmd){
    	String taskChannelName = taskChannelSupplier.toString();
    	int numSub = RedisConsumer.countOf(taskChannelName);;
    	if(numSub >0){
    		producer.produce(this.taskChannelSupplier.get(), cmd);
    		return 1;
    	}
    	return 0;
    }
    private class TaskChannelSupplier implements Supplier<Channel<DeviceInstruction>>{
		@Override
		public Channel<DeviceInstruction> get() {
			return  new Channel<DeviceInstruction>(taskQueueSupplier.get()){};
		}    	
    }
}
