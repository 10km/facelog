package net.gdface.facelog.db;

import net.gdface.facelog.dborm.device.FlDeviceBeanBase;
import net.gdface.facelog.dborm.face.FlFaceBeanBase;
import net.gdface.facelog.dborm.face.FlFeatureBeanBase;
import net.gdface.facelog.dborm.image.FlImageBeanBase;
import net.gdface.facelog.dborm.image.FlStoreBeanBase;
import net.gdface.facelog.dborm.log.FlLogBeanBase;
import net.gdface.facelog.dborm.log.FlLogLightBeanBase;
import net.gdface.facelog.dborm.person.FlPersonBeanBase;

public interface IDbConverter {

	IBeanConverter<DeviceBean, FlDeviceBeanBase> getDeviceBeanConverter();

	IBeanConverter<FaceBean, FlFaceBeanBase> getFaceBeanConverter();

	IBeanConverter<FeatureBean, FlFeatureBeanBase> getFeatureBeanConverter();

	IBeanConverter<ImageBean, FlImageBeanBase> getImageBeanConverter();

	IBeanConverter<LogBean, FlLogBeanBase> getLogBeanConverter();

	IBeanConverter<LogLightBean, FlLogLightBeanBase> getLogLightBeanConverter();

	IBeanConverter<PersonBean, FlPersonBeanBase> getPersonBeanConverter();

	IBeanConverter<StoreBean, FlStoreBeanBase> getStoreBeanConverter();

	/**
	 * @param <G> general type
	 * @param <N> native type
	 * @param clazz
	 * @return
	 */
	<G extends BaseBean, N> IBeanConverter<G,N> getBeanConverter(Class<G> clazz);

}