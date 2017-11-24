package net.gdface.facelog.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.collect.Iterators;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import static com.google.common.base.Preconditions.checkArgument;

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
	private boolean onCommand(boolean group,List<Integer> idList){
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
		if(onCommand(t.isGroup(),t.getTarget())){
			
		}
	}

}
