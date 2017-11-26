package net.gdface.facelog.service;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import com.google.common.collect.Iterators;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;

import gu.simplemq.Channel;
import gu.simplemq.IMessageAdapter;
import gu.simplemq.exceptions.SmqUnsubscribeException;

public class CmdChannelAdapter implements IMessageAdapter<DeviceInstruction>{
	private final BaseCommandAdapter cmdAdapter;
	private final Dao dao;
	private final int deviceId;
	private List<Integer> groupIdList;
	public CmdChannelAdapter(BaseCommandAdapter cmdAdapter,Dao dao,int deviceId) {
		this.cmdAdapter = checkNotNull(cmdAdapter,"cmdAdapter is null");
		this.dao = checkNotNull(dao,"dao is null");
		checkArgument(this.dao.daoExistsDevice(deviceId),"INVALID deviceId");
		this.deviceId= deviceId;
		groupIdList= this.dao.daoToPrimaryKeyListFromDeviceGroups(
				this.dao.daoListOfParentForDeviceGroup(this.dao.daoGetDevice(deviceId).getGroupId()));
	}
	private boolean selfIncluded(boolean group,List<Integer> idList){
		if(group){
			return Iterators.tryFind(idList.iterator(), new Predicate<Integer>(){
				@Override
				public boolean apply(Integer input) {
					return groupIdList.contains(input);
				}}).isPresent();
		}else{
			return idList.contains(this.deviceId);
		}
	}
	@Override
	public void onSubscribe(DeviceInstruction t) throws SmqUnsubscribeException {
		if(selfIncluded(t.isGroup(),t.getTarget())){
			Ack<?> ack = t.getCmd().run(cmdAdapter, t.getParameters());
			// 如果指定了响应频道则向指定的频道发送响应消息
			if(!Strings.isNullOrEmpty(t.getAckChannel())){
				Channel<Ack<?>> ackChannel = new Channel<Ack<?>>(t.getAckChannel()){};
				RedisManagement.getRedisPublisher().publish(ackChannel, ack);
			}
		}
	}

}
