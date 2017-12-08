package net.gdface.facelog.service;

import java.util.HashSet;
import java.util.Set;

import com.facebook.swift.service.ThriftEventHandler;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

/**
 * 用于捕获服务方法中的{@link Token}参数,存入TLS变量,供后续读取,
 * 服务初始化时加入事件侦听器列表才有效，
 * 参见{@link Server#Server(java.util.List, java.util.List, com.facebook.swift.service.ThriftServerConfig)}
 * @author guyadong
 *
 */
class TokenHandler extends ThriftEventHandler {
	static final TokenHandler INSTANCE = new TokenHandler();
	/** 保存当前服务方法调用中{@link Token}参数的TLS变量 */
	private static InheritableThreadLocal<Token> tlsToken = new InheritableThreadLocal<Token>(){
		@Override
		protected Token initialValue() {
			// 返回一个未初始化状态的token,确保TLS变量不为null
			return new Token();
		}
	};
	private Set<ThreadLocal<?>> tlsVariables = Sets.newConcurrentHashSet();
	private TokenHandler() {
	}
	
	/**
	 * 服务方法被调用前从方法参数中查找{@link Token}参数对象存入TLS变量 {@link #tlsToken}
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
	public void registTls(ThreadLocal<?> tls){
		this.tlsVariables.add(Preconditions.checkNotNull(tls,"tls is null"));
	}
	public void unregistTls(ThreadLocal<?> tls){
		this.tlsVariables.remove(Preconditions.checkNotNull(tls,"tls is null"));
	}
}
