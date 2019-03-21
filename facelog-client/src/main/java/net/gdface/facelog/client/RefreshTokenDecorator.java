package net.gdface.facelog.client;

import java.io.Closeable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.gdface.facelog.client.thrift.IFaceLog;
import net.gdface.facelog.client.thrift.SecurityExceptionType;
import net.gdface.facelog.client.thrift.ServiceSecurityException;
import net.gdface.facelog.client.thrift.Token;

import static com.google.common.base.Preconditions.*;

public class RefreshTokenDecorator implements InvocationHandler {
	private InvocationHandler delegate;
	private TokenHelper helper = TokenHelper.DEFAULT_INSTANCE;
	private final Method applyPersonToken;
	private final Method applyRootToken;
	private final Method online;
	private RefreshTokenDecorator(IFaceLog proxyInstance) {
		this.delegate = Proxy.getInvocationHandler(checkNotNull(proxyInstance,"proxyInstance is null"));
		try{
			applyPersonToken = IFaceLog.class.getMethod("applyPersonToken", int.class,String.class,boolean.class);
			applyRootToken =IFaceLog.class.getMethod("applyRootToken", String.class,boolean.class);
			online = IFaceLog.class.getMethod("online", net.gdface.facelog.client.thrift.DeviceBean.class);		
		}catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(e);
		}
		
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		boolean retry = false;
		do{
			try{
				return delegate.invoke(proxy, method, args);
			}catch (ServiceSecurityException e) {
				if(!retry 
						&& SecurityExceptionType.INVALID_TOKEN.equals(e.getType())
						&& args.length>0 && (args[args.length-1] instanceof Token)){
					// 最后一个参数为token
					Token token = (Token)args[args.length-1];
					Token token2 = null;
					try{
						switch(token.getType()){
						case DEVICE:{
							if(helper.deviceBean()!=null){
								Object[] reqArgs = {helper.deviceBean()};
								token2 = (Token) delegate.invoke(proxy,online, reqArgs);
							}
							break;
						}
						case PERSON:{
							String pwd = helper.passwordOf(token.getId());
							if(pwd!=null){
								Object[] reqArgs = {token.getId(),helper.passwordOf(token.getId()),helper.isMd5()};
								token2 = (Token) delegate.invoke(proxy,applyPersonToken, reqArgs);
							}
							break;
						}
						case ROOT:{
							if(helper.deviceBean()!=null){
								Object[] reqArgs = {helper.passwordOf(-1),helper.isMd5()};
								token2 = (Token) delegate.invoke(proxy,applyRootToken, reqArgs);
							}
							break;
						}
						default:
							throw new IllegalStateException("UNKNOW ERROR TYPE:" + token.getType().name());
						}
						// 用申请的新令牌更新参数
						args[args.length-1] = token2;
						retry = true;
						continue;
					}catch (Exception er) {
					}					

				}
				throw e;
			}
		}while(retry);
		throw new IllegalStateException();
	}

	public RefreshTokenDecorator setHelper(TokenHelper helper) {
		if(helper != null){
			this.helper = helper;
		}
		return this;
	}
	public IFaceLog build(){
		return IFaceLog.class.cast(Proxy.newProxyInstance(
				IFaceLog.class.getClassLoader(),
				new Class<?>[]{ IFaceLog.class, Closeable.class },
				this
				));
	}
	public static RefreshTokenDecorator decoratorOf(IFaceLog proxyInstance){
		InvocationHandler handler = Proxy.getInvocationHandler(checkNotNull(proxyInstance,"proxyInstance is null"));
		RefreshTokenDecorator decorator;
		if(handler instanceof RefreshTokenDecorator){
			decorator = (RefreshTokenDecorator) handler;
		}else{
			decorator = new RefreshTokenDecorator(proxyInstance);	
		}		
		return decorator;
	}
}
