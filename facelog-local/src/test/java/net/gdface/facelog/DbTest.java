package net.gdface.facelog;

import static org.junit.Assert.*;

import org.junit.Test;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.IDeviceManager;
import net.gdface.facelog.db.mysql.DeviceManager;
import net.gdface.facelog.TableManagerInitializer;

/**
 * @author guyadong
 *
 */
public class DbTest implements ServiceConstant {
	private static IDeviceManager deviceManager = TableManagerInitializer.INSTANCE.deviceManager;
	@Test
	public void testExistsPrimaryKey() {
		
		DeviceBean b1 = DeviceBean.builder().name("顾亚东").build();
		DeviceBean saved = deviceManager.save(b1);
		logger.info(saved.toString(true, false));
		//saved.setName("ahha");
		//deviceManager.save(saved);
		logger.info(deviceManager.loadByPrimaryKey(saved.getId()).toString(true, false));
		logger.info("device id[{}] exists:{}",b1.getId(), deviceManager.existsPrimaryKey(b1.getId()));
		logger.info("device id[null] exists:{}",deviceManager.existsPrimaryKey((Integer)null));
		DeviceBean reload = deviceManager.loadByPrimaryKey(b1.getId());
		logger.info(reload.toString(true, false));
		reload.setName("大家好");
		deviceManager.save(reload);
		logger.info(reload.toString(true, false));
	}

}
