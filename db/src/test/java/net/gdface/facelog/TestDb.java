package net.gdface.facelog;

import static org.junit.Assert.*;

import org.junit.Test;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.DeviceGroupBean;
import net.gdface.facelog.db.mysql.DeviceGroupManager;
import net.gdface.facelog.db.mysql.DeviceManager;

public class TestDb {
	private static DeviceManager deviceManager = DeviceManager.getInstance();
	private static DeviceGroupManager deviceGroupManager = DeviceGroupManager.getInstance();
	private DeviceBean makeDeviceBean(Integer id,String name){
		
		DeviceBean deviceBean = new DeviceBean();
		if(null !=id)
			deviceBean.setId(id);
		deviceBean.setName(name);
		deviceBean.setGroupId(1);
		return deviceBean;
	}
	@Test
	public void testExistsPrimaryKey() {
		if(!deviceGroupManager.existsPrimaryKey(1)){
			DeviceGroupBean deviceGroup = new DeviceGroupBean(1);
			deviceGroup.setName("default_group");
			deviceGroupManager.save(deviceGroup);
		}
		DeviceBean b1 = makeDeviceBean(null,"hello");
		deviceManager.save(b1);		
		System.out.printf("device id[%d] exists:%b\n",b1.getId(), deviceManager.existsPrimaryKey(b1.getId()));
		System.out.printf("device id[null] exists:%b\n",deviceManager.existsPrimaryKey((Integer)null));

	}

}
