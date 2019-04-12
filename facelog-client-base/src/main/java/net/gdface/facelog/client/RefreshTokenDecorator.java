package net.gdface.facelog.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;

import net.gdface.facelog.CommonConstant.ExceptionType;
import net.gdface.facelog.IFaceLog;
import net.gdface.facelog.ServiceSecurityException;
import net.gdface.facelog.ServiceSecurityException.SecurityExceptionType;
import net.gdface.facelog.Token;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.thrift.exception.client.BaseServiceRuntimeException;
import net.gdface.utils.Delegator;

import static com.google.common.base.Preconditions.*;

/**
 * {@link IFaceLog}接口代理实现令牌失效后自动重发机制<br>
 * @author guyadong
 *
 */
public class RefreshTokenDecorator implements InvocationHandler,Delegator<IFaceLog>{
	private final Method applyPersonToken;
	private final Method applyRootToken;
	private final Method online;
	private TokenHelper helper = TokenHelper.DEFAULT_INSTANCE;

	private final IFaceLog delegate;
	private RefreshTokenDecorator(IFaceLog delegate) {
		this.delegate = checkNotNull(delegate,"delegate is null");
		try{
			applyPersonToken = IFaceLog.class.getMethod("applyPersonToken", int.class,String.class,boolean.class);
			applyRootToken =IFaceLog.class.getMethod("applyRootToken", String.class,boolean.class);
			online = IFaceLog.class.getMethod("online", DeviceBean.class);
		}catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(e);
		}
		
	}

	/**
	 * 排除申请令牌的方法
	 * @param method
	 * @return
	 */
	private boolean isExclude(Method method){
		return applyPersonToken.equals(method) || applyRootToken.equals(method) || online.equals(method);
	}
	/**
	 * 拦截接口方法抛出的{@link ServiceSecurityException}异常，当抛出令牌失效异常时自动执行一次申请令牌的动作
	 * 如果申请成功则再尝试执行上次调用失败的接口方法
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		boolean retry = false;
		do{
			try{
				return method.invoke(delegate, args);
			}catch (InvocationTargetException ite) {
				Throwable target = ite.getTargetException();
				if(!retry){
					boolean isInvalidTokenException = false;
					// 判断是否为无效令牌异常,无效令牌异常可能被服务端封装在ServiceRuntimeException中
					// 也可能被直接抛出，所以这里要分别判断
					if(target instanceof BaseServiceRuntimeException){
						// 封装在ServiceRuntimeException中的安全异常
						BaseServiceRuntimeException sre = (BaseServiceRuntimeException)target;
						if(sre.getType() == ExceptionType.SECURITY_ERROR.ordinal()){
							// 解析ServiceSecurityException异常的字段获取安全异常的错误代码(type)
							JSONObject sse = JSON.parseObject(sre.getCauseFields());	
							if(SecurityExceptionType.INVALID_TOKEN.name().equals(sse.getString("type"))){
								isInvalidTokenException = true;
							}
						}
					}else if(target instanceof ServiceSecurityException){
						// 直接抛出的安全异常
						ServiceSecurityException e = (ServiceSecurityException)target;
						if(SecurityExceptionType.INVALID_TOKEN.equals(e.getType())){
							isInvalidTokenException = true;
						}
					}
					if(isInvalidTokenException){
						if(args.length>0 && (args[args.length-1] instanceof Token)
								&& !isExclude(method)){
							// 最后一个参数为token
							Token invalidToken = (Token)args[args.length-1];
							Token freshToken = null;
							try{
								// 重新申请令牌
								switch(invalidToken.getType()){
								case DEVICE:{
									if(helper.deviceBean() != null){
										Object[] reqArgs = {helper.deviceBean()};
										freshToken = (Token) online.invoke(delegate,reqArgs);
									}
									break;
								}
								case PERSON:{
									String pwd = helper.passwordOf(invalidToken.getId());
									if(pwd != null){
										Object[] reqArgs = {invalidToken.getId(),pwd,helper.isHashedPwd()};
										freshToken = (Token) applyPersonToken.invoke(delegate, reqArgs);
									}
									break;
								}
								case ROOT:{
									String pwd = helper.passwordOf(-1);
									if(pwd != null){
										Object[] reqArgs = {pwd,helper.isHashedPwd()};
										freshToken = (Token) applyRootToken.invoke(delegate, reqArgs);
									}
									break;
								}
								default:
									break;
								}
								// 如果申请令牌成功则更新令牌参数，重新执行方法调用
								if(freshToken != null){
									// 用申请的新令牌更新参数
									args[args.length-1] = freshToken;
									helper.saveFreshedToken(freshToken);
									retry = true;
									continue;
								}
							}catch (Exception er) {
								// DO NOTHING
							}
						}
					}
				}
				throw target;
			}
		}while(retry);
		// DEAD CODE
		throw new IllegalStateException("CANNOT REACH HERE");
	}

	/**
	 * 设置{@link TokenHelper}实例，不可为{@code null}
	 * @param helper
	 * @return
	 */
	public RefreshTokenDecorator setHelper(TokenHelper helper) {
		this.helper = checkNotNull(helper,"helper is null");
		return this;
	}
	/**
	 * 根据当前对象创建新的接口实例{@link Proxy}
	 * @return
	 */
	public IFaceLog proxyInstance(){
		return IFaceLog.class.cast(Proxy.newProxyInstance(
				IFaceLog.class.getClassLoader(),
				new Class<?>[]{ IFaceLog.class},
				this));
	}


	/**
	 * 根据{@link IFaceLog}接口实例创建{@link RefreshTokenDecorator}实例
	 * @param instance 必须为{@link Proxy}代理实例
	 * @return
	 */
	public static RefreshTokenDecorator decoratorOf(IFaceLog instance){
		RefreshTokenDecorator decorator;
		if(instance instanceof RefreshTokenDecorator){
			decorator = (RefreshTokenDecorator) instance;
		}else{
			decorator = new RefreshTokenDecorator(instance);	
		}
		return decorator;
	}
	/**
	 * 创建一个将原{@link IFaceLog}接口实例转为{@link RefreshTokenDecorator}代理的接口实例的{@link Function}对象,
	 * 用于 {@link net.gdface.thrift.ClientFactory#setDecorator(Function)}
	 * @param helper
	 * @return
	 */
	public static Function<IFaceLog,IFaceLog> makeDecoratorFunction(final TokenHelper helper){
		checkArgument(helper != null);
		return new Function<IFaceLog, IFaceLog>(){
			@Override
			public IFaceLog apply(IFaceLog input) {
				return decoratorOf(input).setHelper(helper).proxyInstance();
			}};
	}

	@Override
	public IFaceLog delegate() {
		return delegate;
	}
}