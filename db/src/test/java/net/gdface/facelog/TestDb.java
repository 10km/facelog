package net.gdface.facelog;

import static org.junit.Assert.*;

import org.junit.Test;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.mysql.DeviceManager;

public class TestDb {
	private static DeviceManager deviceManager = DeviceManager.getInstance();
	private DeviceBean makeDeviceBean(Integer id,String name){
		
		DeviceBean deviceBean = new DeviceBean();
		if(null !=id)
			deviceBean.setId(id);
		deviceBean.setName(name);
		return deviceBean;
	}
	@Test
	public void testExistsPrimaryKey() {
		DeviceBean b1 = makeDeviceBean(null,"hello");
		deviceManager.save(b1);		
		System.out.printf("device id[%d] exists:%b\n",b1.getId(), deviceManager.existsPrimaryKey(b1.getId()));
		System.out.printf("device id[null] exists:%b\n",deviceManager.existsPrimaryKey((Integer)null));

	}

}
