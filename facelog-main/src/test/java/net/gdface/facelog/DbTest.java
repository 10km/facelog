package net.gdface.facelog;

import static org.junit.Assert.*;

import org.junit.Test;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.IDeviceManager;
import net.gdface.facelog.db.mysql.DeviceManager;
import net.gdface.facelog.service.TableManagerInitializer;

/**
 * @author guyadong
 *
 */
public class DbTest {
	private static IDeviceManager deviceManager = TableManagerInitializer.INSTANCE.deviceManager;
	private DeviceBean makeDeviceBean(Integer id,String name){
		
		DeviceBean deviceBean = new DeviceBean();
		if(null !=id){
			deviceBean.setId(id);
		}
		deviceBean.setName(name);
		return deviceBean;
	}
	@Test
	public void testExistsPrimaryKey() {
		DeviceBean b1 = makeDeviceBean(null,"hello");
		DeviceBean saved = deviceManager.save(b1);
		//saved.setName("ahha");
		//deviceManager.save(saved);
		System.out.println(deviceManager.loadByPrimaryKey(saved.getId()));
		System.out.printf("device id[%d] exists:%b\n",b1.getId(), deviceManager.existsPrimaryKey(b1.getId()));
		System.out.printf("device id[null] exists:%b\n",deviceManager.existsPrimaryKey((Integer)null));

	}

}
