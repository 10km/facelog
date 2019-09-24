package net.gdface.service.facelog;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.facebook.swift.service.ThriftEventHandler;
import com.facebook.swift.service.ThriftServerService;
import com.google.common.collect.Sets;

import net.gdface.facelog.ServiceSecurityException;
import net.gdface.facelog.ServiceUtil;
import net.gdface.facelog.TokenContext;
import net.gdface.facelog.decorator.Token;

/**
 * TLS变量管理器<br>
 * 1.用于捕获服务方法中的{@link Token}参数,存入TLS变量,供后续读取<p>
 * 2.提供RPC调用过程中TLS变量的自动释放机制,参见{@link #registerTls(ThreadLocal)},{@link #unregisterTls(ThreadLocal)}.<p>
 * <b>NOTE:</b>服务初始化时加入事件侦听器列表才有效, 参见{@link ThriftServerService#ThriftServerService(java.util.List, java.util.List, com.facebook.swift.service.ThriftServerConfig)}.<p>
 * @author guyadong
 *
 */
public class TlsHandler extends ThriftEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(TlsHandler.class);

	public static final TlsHandler INSTANCE = new TlsHandler();
	/** 注册到当前对象的TLS变量集合,当RPC调用结束时调用{@link ThreadLocal#remove()}释放TLS变量 */
	private final Set<ThreadLocal<?>> tlsVariables = Sets.newConcurrentHashSet();
	private final Set<IPortDone> portDones = Sets.newConcurrentHashSet();
	private final Set<IPortError> portErrors = Sets.newConcurrentHashSet();

	private TlsHandler() {
		registerErrorHandler(new OnTableOpException());
	}
	
	/**
	 * called by swift only<br>
	 * 服务方法被调用前从方法参数中查找最后一个类型为{@link Token}的参数对象存入TLS变量
	 */
	@Override
	public void postRead(Object context, String methodName, Object[] args) {
		// 服务方法调用开始前初始化 TokenContext 
		TokenContext.getCurrentTokenContext().setMethodName(methodName);
		if(args.length > 0){
			// 捕获最后一个类型为Token的参数存入TLS
			Object last = args[args.length-1];
			// service层拦截token对象是decorator,
			if( last instanceof Token){
				TokenContext.getCurrentTokenContext().setToken(((Token)last).delegate());
			}
		}
	}
	/** called by swift only<br>  */
	@Override
	public void done(Object context, String methodName) {
		// 服务方法结束
		TokenContext.getCurrentTokenContext().contextDone();
		for( IPortDone done : portDones){
			done.onDone(context, methodName);
		}
		// 释放所有注册的tls变量
		for(ThreadLocal<?> tls:this.tlsVariables){
			tls.remove();
		}
	}

	@Override
	public void postWriteException(Object context, String methodName, Throwable t) {
		for( IPortError error : portErrors){
			error.onError(context, methodName,t);
		}
	}

	/**
	 * {@link Set#add(Object)}代理方法<br>
	 * 将{@code tls}交给{@link TlsHandler}管理,当前对象会在RPC调用结束时释放TLS变量
	 * @param tls 为{@code null}时无效
	 * @return 成功返回{@code true},否则返回{@code false}
	 */
	public boolean registerTls(ThreadLocal<?> tls){		
		return null == tls ? false : this.tlsVariables.add(tls);
	}
	/**
	 * {@link Set#remove(Object)}代理方法<br>
	 * 解除当前对象对{@code tls}的管理
	 * @param tls 为{@code null}时无效
	 * @return 成功返回{@code true},否则返回{@code false}
	 */
	public boolean unregisterTls(ThreadLocal<?> tls){
		return null == tls ? false : this.tlsVariables.remove(tls);
	}
	
	/**
	 * {@link Set#add(Object)}代理方法<br>
	 * 注册一个{@link IPortDone}接口实例,当服务方法结束时调用该实例
	 * @param done 为{@code null}时无效
	 * @return 注册是否成功
	 */
	public boolean registerDoneHandler(IPortDone done) {
		return null == done ? false : portDones.add(done);
	}

	/**
	 * {@link Set#remove(Object)}代理方法<br>
	 * 从当前管理的{@link IPortDone}接口实例中删除{@code done}指定的实例
	 * @param done 为{@code null}时无效
	 * @return 删除是否成功
	 */
	public boolean unregisterDoneHandler(IPortDone done) {
		return null == done ? false : portDones.remove(done);
	}
	/**
	 * {@link Set#add(Object)}代理方法<br>
	 * 注册一个{@link IPortError}接口实例,当服务方法结束时调用该实例
	 * @param error 为{@code null}时无效
	 * @return 注册是否成功
	 */
	public boolean registerErrorHandler(IPortError error) {
		return null == error ? false : portErrors.add(error);
	}

	/**
	 * {@link Set#remove(Object)}代理方法<br>
	 * 从当前管理的{@link IPortError}接口实例中删除{@code error}指定的实例
	 * @param error 为{@code null}时无效
	 * @return 删除是否成功
	 */
	public boolean unregisterErrorHandler(IPortError error) {
		return null == error ? false : portErrors.remove(error);
	}
	/**
	 * 服务方法调用结束时调用接口
	 * @author guyadong
	 *
	 */
	public interface IPortDone{
		/**
		 * 服务接口结束时调用方法
		 * @param context
		 * @param methodName
		 */
		public void onDone(Object context, String methodName);
	}
	/**
	 * 服务方法异常时调用接口
	 * @author guyadong
	 *
	 */
	public interface IPortError{
		/**
		 * 服务接口结束时调用方法
		 * @param context
		 * @param methodName
		 * @param error
		 */
		public void onError(Object context, String methodName, Throwable error);
	}
	
	/**
	 * 当数据库表操作被拒绝时服务端输出日志
	 * @author guyadong
	 *
	 */
	private class OnTableOpException implements IPortError{

		@Override
		public void onError(Object context, String methodName, Throwable error) {
			if(error instanceof ServiceSecurityException){
				ServiceSecurityException securityException = (ServiceSecurityException)error;
				switch(securityException.getType()){
				case TABLE_INSERT_DENIED:
				case TABLE_UPDATE_DENIED:
				case TABLE_DELETE_DENIED:
					logger.error("PORT:{} FROM:{} BY:{}: MESSAGE:{}",
							methodName,
							ServiceUtil.clientAddressAsString(),
							TokenContext.getCurrentTokenContext().getToken().owner(),
							error.getMessage());
					break;
				default:
					break;
				}
			}
		}		
	}
}
