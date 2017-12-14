package net.gdface.facelog.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import static com.google.common.base.Preconditions.*;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;

import net.gdface.facelog.db.BaseBean;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.DeviceGroupBean;
import net.gdface.facelog.db.PermitBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.PersonGroupBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.exception.RuntimeDaoException;
import net.gdface.facelog.service.Dao.WriteOp;

/**
 * 用于生成系统日志的侦听器抽象类<br>
 * 必须同步执行不可交线程池运行.
 * @author guyadong
 *
 * @param <B>
 */
public abstract class BaseSysLogLisener<B extends BaseBean<B>> 
	extends TableListener.Adapter<B> implements ServiceConstant {
	/** 表名 */
	private final String beanName;
	/** 需要记录日志的操作,从配置文件中读取 */
	private final ImmutableSet<WriteOp> logOp;
	/**
	 * 构造函数
	 * @param syslogOpDaoKey 配置文件中定义要记录日志的key
	 * @throws IllegalArgumentException {@code syslogOpDaoKey}为{@code null}或空
	 */
	private BaseSysLogLisener(String syslogOpDaoKey) {
		checkArgument(!Strings.isNullOrEmpty(syslogOpDaoKey),"key is null or empty");
		// 从配置文件中读取日志记录类型参数
		logOp = GlobalConfig.getEnumSet(WriteOp.class, syslogOpDaoKey);
        Type superClass = getClass().getGenericSuperclass();
        Class<?> type = TypeToken.of((Class<?>)((ParameterizedType) superClass).getActualTypeArguments()[0]).getRawType();
        this.beanName = type.getSimpleName();
	}

	@Override
	public void afterInsert(B bean) throws RuntimeDaoException {
		log(bean,WriteOp.insert);
	}

	@Override
	public void afterUpdate(B bean) throws RuntimeDaoException {
		log(bean,WriteOp.update);
	}

	@Override
	public void afterDelete(B bean) throws RuntimeDaoException {
		log(bean,WriteOp.delete);
	}
	/**
	 * 记录日志
	 * @param bean
	 * @param writeOp 写操作类型
	 */
	private void log(B bean,WriteOp writeOp){
		if(logOp.contains(writeOp)){
			logger.info("PORT:{} OP:{}: FROM:{} BY:{}: {}: {}",
					TokenContext.getCurrentTokenContext().getMethodName(),
					writeOp.name(),
					ServiceUtil.clientAddressAsString(),
					TokenContext.getCurrentTokenContext().getToken().owner(),
					beanName,
					bean.toString(true, false));
		}
	}
	/** fl_person 表日志记录侦听器实例 */
	public static final BaseSysLogLisener<PersonBean> 
		PERSON_LOG_LISTENER 
		= new BaseSysLogLisener<PersonBean>(SYSLOG_OP_DAO_PERSON){};
	/** fl_person_group 表日志记录侦听器实例 */
	public static final BaseSysLogLisener<PersonGroupBean> 
		PERSON_GROUP_LOG_LISTENER 
		= new BaseSysLogLisener<PersonGroupBean>(SYSLOG_OP_DAO_PERSONGROUP){};
	/** fl_device 表日志记录侦听器实例 */
	public static final BaseSysLogLisener<DeviceBean> 
		DEVICE_LOG_LISTENER 
		= new BaseSysLogLisener<DeviceBean>(SYSLOG_OP_DAO_DEVICE){};
	/** fl_device_group 表日志记录侦听器实例 */
	public static final BaseSysLogLisener<DeviceGroupBean> 
		DEVICE_GROUP_LOG_LISTENER 
		= new BaseSysLogLisener<DeviceGroupBean>(SYSLOG_OP_DAO_DEVICEGROUP){};
	/** fl_permit 表日志记录侦听器实例 */
	public static final BaseSysLogLisener<PermitBean> 
		PERMIT_LOG_LISTENER 
		= new BaseSysLogLisener<PermitBean>(SYSLOG_OP_DAO_PERMIT){};	
}
