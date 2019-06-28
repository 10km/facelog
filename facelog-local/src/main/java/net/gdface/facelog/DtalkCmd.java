package net.gdface.facelog;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import gu.dtalk.client.CmdManager;
import gu.dtalk.client.TaskManager;
import gu.simplemq.json.BaseJsonEncoder;
import gu.simplemq.redis.JedisPoolLazy;

import static com.google.common.base.Preconditions.*;;

class DtalkCmd {
	private final TokenMangement tm;
	private final CmdManager cmdManager;
	private final TaskManager taskManager;
	DtalkCmd(Map<MQParam, String> redisParameters,TokenMangement tm) {
		this.tm = tm;
		JedisPoolLazy jedisPoolLazy = JedisPoolLazy.getInstanceByURI(redisParameters.get(MQParam.REDIS_URI));
		this.cmdManager = new CmdManager(
				jedisPoolLazy, 
				redisParameters.get(MQParam.CMD_CHANNEL));
		this.taskManager = new TaskManager(jedisPoolLazy);
	}

	/**
	 * (异步)执行cmdpath指定的设备命令<br>
	 * @param target 命令目标设备/设备组id
	 * @param group target中的元素是否为设备组id
	 * @param cmdpath 设备命令的dtalk路径
	 * @param jsonArgs 设备命令参数(JSON)
	 * @param ackChannel 设备命令响应频道,可为{@code null}
	 * @param id 执行设备命令的用户id
	 * @return 收到命令的客户端数目
	 */
	int doRunCmd(List<Integer>target,boolean group,String cmdpath,String jsonArgs,String ackChannel,int id){
		checkArgument(target != null,"target is null");
		// 过滤所有空元素
		target = Lists.newArrayList(Iterables.filter(target, Predicates.notNull()));
		checkArgument(!target.isEmpty(),"target list is empty");
		cmdManager.targetBuilder()
			.setTarget(target, group)
			.setCmdSn(tm.applyCmdSn(id))
			.setAckChannel(ackChannel);
		return cmdManager.runCmd(cmdpath, BaseJsonEncoder.getEncoder().fromJson(jsonArgs,JSONObject.class));
	}
	/**
	 * (异步)执行cmdpath指定的任务<br>
	 * @param taskQueue 任务队列名称
	 * @param cmdpath 设备命令的dtalk路径
	 * @param jsonArgs 设备命令参数(JSON)
	 * @param ackChannel 设备命令响应频道,可为{@code null}
	 * @param id 执行设备命令的用户id
	 * @return 成功提交任务返回{@code true},否则返回{@code false}
	 */
	boolean doRunTask(String taskQueue,String cmdpath,String jsonArgs,String ackChannel,int id){
		taskManager.targetBuilder()
			.setCmdSn(tm.applyCmdSn(id))
			.setAckChannel(ackChannel);
		return taskManager
				.setTaskQueue(taskQueue)
				.setCmdpath(cmdpath)
				.runCmd(BaseJsonEncoder.getEncoder().fromJson(jsonArgs,JSONObject.class));
	}
}
