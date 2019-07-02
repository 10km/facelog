package net.gdface.facelog;

import java.util.List;

import org.junit.Test;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.IDeviceManager;
import net.gdface.facelog.TableManagerInitializer;
import static net.gdface.facelog.db.Constant.*;


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
	@Test
	public void testSelectDistinct(){
		FL_DEVICE_FIELDS_LIST.indexOf("hello");
		List<DeviceBean> devices = deviceManager.loadBySqlAsList("select DISTINCT used_sdks from fl_device", null, new int[]{FL_DEVICE_ID_USED_SDKS});
		
		logger.info("devices:{}",devices);
		List<String> sdks = deviceManager.loadColumnAsList("used_sdks", true, null, 1, -1);
		logger.info("sdks:{}",sdks);
	}

}
