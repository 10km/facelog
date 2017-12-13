package net.gdface.facelog.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketAddress;

import com.google.common.reflect.TypeToken;

import net.gdface.facelog.db.BaseBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.exception.RuntimeDaoException;

/**
 * 用于生成系统日志的侦听器抽象类
 * @author guyadong
 *
 * @param <B>
 */
public abstract class BaseSysLogLisener<B extends BaseBean<B>> extends TableListener.Adapter<B> implements CommonConstant {
	private final Class<B> type;
	private final String beanName;
	@SuppressWarnings("unchecked")
	protected BaseSysLogLisener() {
        Type superClass = getClass().getGenericSuperclass();
        this.type = (Class<B>) TypeToken.of((Class<B>)((ParameterizedType) superClass).getActualTypeArguments()[0]).getRawType();
        this.beanName = this.type.getSimpleName();
	}

	@Override
	public void afterInsert(B bean) throws RuntimeDaoException {
		logger.info("{}:insert {}: {}",clientAddress(),bean.toString());
	}

	@Override
	public void afterUpdate(B bean) throws RuntimeDaoException {
		logger.info("{}:update {}: {}",clientAddress(),beanName,bean.toString());
	}

	@Override
	public void afterDelete(B bean) throws RuntimeDaoException {
		logger.info("{}:delete {}: {}",clientAddress(),beanName,bean.toString());
	}
	private String clientAddress(){
		SocketAddress address = ServiceUtil.niftyClientAddress();
		return null == address ? "unknow" :address.toString();
	}
}
