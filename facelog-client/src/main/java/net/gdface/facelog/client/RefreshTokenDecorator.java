package net.gdface.facelog.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.gdface.facelog.client.thrift.ServiceSecurityException;

import static com.google.common.base.Preconditions.*;

public class RefreshTokenDecorator implements InvocationHandler {
	private InvocationHandler delegate;
	private TokenHelper helper = TokenHelper.DEFAULT_INSTANCE;
	public RefreshTokenDecorator(InvocationHandler delegate) {
		super();
		setDelegate(delegate);
	}

	public RefreshTokenDecorator() {
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		try{
			return delegate.invoke(proxy, method, args);
		}catch (ServiceSecurityException e) {
			return null;
		}
	}

	public InvocationHandler getDelegate() {
		return delegate;
	}

	public RefreshTokenDecorator setDelegate(InvocationHandler delegate) {
		this.delegate = checkNotNull(delegate,"delegate is null");
		return this;
	}

	public TokenHelper getHelper() {
		return helper;
	}

	public RefreshTokenDecorator setHelper(TokenHelper helper) {
		if(helper != null){
			this.helper = helper;
		}
		return this;
	}

}
