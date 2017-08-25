package net.gdface.facelog.db.mysql;

import java.util.HashMap;
import java.util.Map;

import net.gdface.facelog.db.BaseBean;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.IBeanConverter;
import net.gdface.facelog.db.IDbConverter;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.LogLightBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.StoreBean;
import net.gdface.facelog.dborm.device.FlDeviceBeanBase;
import net.gdface.facelog.dborm.face.FlFaceBeanBase;
import net.gdface.facelog.dborm.face.FlFeatureBeanBase;
import net.gdface.facelog.dborm.image.FlImageBeanBase;
import net.gdface.facelog.dborm.image.FlStoreBeanBase;
import net.gdface.facelog.dborm.log.FlLogBeanBase;
import net.gdface.facelog.dborm.log.FlLogLightBeanBase;
import net.gdface.facelog.dborm.person.FlPersonBeanBase;

public class MySqlConverter implements IDbConverter {
	public final IBeanConverter<DeviceBean,FlDeviceBeanBase> deviceBeanConverter=new IBeanConverter.AbstractHandle<DeviceBean,FlDeviceBeanBase>(){

		@Override
		public DeviceBean fromNative(FlDeviceBeanBase bean) {
			// TODO 自动生成的方法存根
			return null;
		}

		@Override
		public FlDeviceBeanBase toNative(DeviceBean bean) {
			// TODO 自动生成的方法存根
			return null;
		}};
		public final IBeanConverter<FaceBean,FlFaceBeanBase> faceBeanConverter=new IBeanConverter.AbstractHandle<FaceBean,FlFaceBeanBase>(){

			@Override
			public FaceBean fromNative(FlFaceBeanBase bean) {
				// TODO 自动生成的方法存根
				return null;
			}

			@Override
			public FlFaceBeanBase toNative(FaceBean bean) {
				// TODO 自动生成的方法存根
				return null;
			}};
		public final IBeanConverter<FeatureBean,FlFeatureBeanBase> featureBeanConverter=new IBeanConverter.AbstractHandle<FeatureBean,FlFeatureBeanBase>(){

				@Override
				public FeatureBean fromNative(FlFeatureBeanBase bean) {
					// TODO 自动生成的方法存根
					return null;
				}

				@Override
				public FlFeatureBeanBase toNative(FeatureBean bean) {
					// TODO 自动生成的方法存根
					return null;
				}};
		public final IBeanConverter<ImageBean,FlImageBeanBase> imageBeanConverter=new IBeanConverter.AbstractHandle<ImageBean,FlImageBeanBase>(){

				@Override
				public ImageBean fromNative(FlImageBeanBase bean) {
					// TODO 自动生成的方法存根
					return null;
				}

				@Override
				public FlImageBeanBase toNative(ImageBean bean) {
					// TODO 自动生成的方法存根
					return null;
				}};
		public final IBeanConverter<LogBean,FlLogBeanBase> logBeanConverter=new IBeanConverter.AbstractHandle<LogBean,FlLogBeanBase>(){

				@Override
				public LogBean fromNative(FlLogBeanBase bean) {
					// TODO 自动生成的方法存根
					return null;
				}

				@Override
				public FlLogBeanBase toNative(LogBean bean) {
					// TODO 自动生成的方法存根
					return null;
				}};
		public final IBeanConverter<LogLightBean,FlLogLightBeanBase> logLightBeanConverter=new IBeanConverter.AbstractHandle<LogLightBean,FlLogLightBeanBase>(){

			@Override
			public LogLightBean fromNative(FlLogLightBeanBase bean) {
				// TODO 自动生成的方法存根
				return null;
			}

			@Override
			public FlLogLightBeanBase toNative(LogLightBean bean) {
				// TODO 自动生成的方法存根
				return null;
			}};
		public final IBeanConverter<PersonBean,FlPersonBeanBase> personBeanConverter=new IBeanConverter.AbstractHandle<PersonBean,FlPersonBeanBase>(){

			@Override
			public PersonBean fromNative(FlPersonBeanBase bean) {
				// TODO 自动生成的方法存根
				return null;
			}

			@Override
			public FlPersonBeanBase toNative(PersonBean bean) {
				// TODO 自动生成的方法存根
				return null;
			}};
		public final IBeanConverter<StoreBean,FlStoreBeanBase> storeBeanConverter=new IBeanConverter.AbstractHandle<StoreBean,FlStoreBeanBase>(){

				@Override
				public StoreBean fromNative(FlStoreBeanBase bean) {
					// TODO 自动生成的方法存根
					return null;
				}

				@Override
				public FlStoreBeanBase toNative(StoreBean bean) {
					// TODO 自动生成的方法存根
					return null;
				}};
		private final Map<Class<? extends BaseBean>,IBeanConverter<? extends BaseBean,?>> converters= new HashMap<Class<? extends BaseBean>,IBeanConverter<?extends BaseBean,?>>(){
			private static final long serialVersionUID = 1L;
			{
				put(DeviceBean.class,deviceBeanConverter);
				put(FaceBean.class,faceBeanConverter);
				put(FeatureBean.class,featureBeanConverter);
				put(ImageBean.class,imageBeanConverter);
				put(LogBean.class,logBeanConverter);
				put(LogLightBean.class,logLightBeanConverter);
				put(PersonBean.class,personBeanConverter);
				put(StoreBean.class,storeBeanConverter);
			}};
	public MySqlConverter() {
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <G extends BaseBean,N>IBeanConverter<G,N>getBeanConverter(Class<G> clazz){
		return (IBeanConverter<G, N>) converters.get(clazz);
	}
	@Override
	public IBeanConverter<DeviceBean, FlDeviceBeanBase> getDeviceBeanConverter() {
		return deviceBeanConverter;
	}

	@Override
	public IBeanConverter<FaceBean, FlFaceBeanBase> getFaceBeanConverter() {
		return faceBeanConverter;
	}

	@Override
	public IBeanConverter<FeatureBean, FlFeatureBeanBase> getFeatureBeanConverter() {
		return featureBeanConverter;
	}

	@Override
	public IBeanConverter<ImageBean, FlImageBeanBase> getImageBeanConverter() {
		return imageBeanConverter;
	}

	@Override
	public IBeanConverter<LogBean, FlLogBeanBase> getLogBeanConverter() {
		return logBeanConverter;
	}

	@Override
	public IBeanConverter<LogLightBean, FlLogLightBeanBase> getLogLightBeanConverter() {
		return logLightBeanConverter;
	}

	@Override
	public IBeanConverter<PersonBean, FlPersonBeanBase> getPersonBeanConverter() {
		return personBeanConverter;
	}

	@Override
	public IBeanConverter<StoreBean, FlStoreBeanBase> getStoreBeanConverter() {
		return storeBeanConverter;
	}
}
