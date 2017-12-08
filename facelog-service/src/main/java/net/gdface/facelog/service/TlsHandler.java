package net.gdface.facelog.service;

import java.util.Set;

import com.facebook.swift.service.ThriftEventHandler;
import com.google.common.collect.Sets;

/**
 * TLS变量管理器<br>
 * 1.用于捕获服务方法中的{@link Token}参数,存入TLS变量,供后续读取<p>
 * 2.提供RPC调用过程中TLS变量的自动释放机制,参见{@link #registerTls(ThreadLocal)},{@link #unregisterTls(ThreadLocal)}.<p>
 * <b>NOTE:</b>服务初始化时加入事件侦听器列表才有效, 参见{@link Server#Server(java.util.List, java.util.List, com.facebook.swift.service.ThriftServerConfig)}.<p>
 * @author guyadong
 *
 */
public class TlsHandler extends ThriftEventHandler {
	static final TlsHandler INSTANCE = new TlsHandler();
	/** 保存当前服务方法调用中{@link Token}参数的TLS变量 */
	private static InheritableThreadLocal<Token> tlsToken = new InheritableThreadLocal<Token>(){
		@Override
		protected Token initialValue() {
			// 返回一个未初始化状态的token,确保TLS变量不为null
			return new Token();
		}
	};
	/** 注册到当前对象的TLS变量集合,当RPC调用结束时调用{@link ThreadLocal#remove()}释放TLS变量 */
	private final Set<ThreadLocal<?>> tlsVariables = Sets.newConcurrentHashSet();
	private TlsHandler() {
	}
	
	/**
	 * called by swift only<br>
	 * 服务方法被调用前从方法参数中查找最后一个类型为{@link Token}的参数对象存入TLS变量 {@link #tlsToken}
	 */
	@Override
	public void postRead(Object context, String methodName, Object[] args) {
		if(args.length > 0){
			// 捕获最后一个类型为Token的参数存入TLS
			Object last = args[args.length-1];
			if( last instanceof Token){
				tlsToken.set((Token) last);
			}
		}
	}
	/** called by swift only<br>  */
	@Override
	public void done(Object context, String methodName) {
		// 服务方法调用结束时释放TLS
		tlsToken.remove();
		// 释放所有注册的tls变量
		for(ThreadLocal<?> tls:this.tlsVariables){
			tls.remove();
		}
	}
	/** 返回TLS变量存储的{@link Token}对象 */
	Token get(){
		return tlsToken.get();
	}
	/**
	 * {@link Set#add(Object)}代理方法<br>
	 * 将{@code tls}交给当前对象管理,当前对象会在RPC调用结束时释放TLS变量
	 * @param tls 为{@code null}时无效
	 * @return 
	 */
	public boolean registerTls(ThreadLocal<?> tls){		
		return null == tls ? false : this.tlsVariables.add(tls);
	}
	/**
	 * {@link Set#remove(Object)}代理方法<br>
	 * 解除当前对象对{@code tls}的管理
	 * @param tls 为{@code null}时无效
	 * @return
	 */
	public boolean unregisterTls(ThreadLocal<?> tls){
		return null == tls ? false : this.tlsVariables.remove(tls);
	}
}
