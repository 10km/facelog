package net.gdface.facelog.service;

import static com.google.common.base.Preconditions.*;

import com.google.common.base.Strings;

import net.gdface.facelog.service.TokenMangement.TokenOp;


/**
 * 令牌状态上下文
 * @author guyadong
 *
 */
public class TokenContext implements ServiceConstant {
	private static final ThreadLocal<TokenContext> TOKEN_CONTEXT = new ThreadLocal<TokenContext>(){
		@Override
		protected TokenContext initialValue() {
			return new TokenContext();
		}};
	/**  是否记录详细异常信息  */
	private static final boolean errorDetail = CONFIG.getBoolean(SYSLOG_OP_TOKEN_ERRORDETAIL);
	private Token token;
	private TokenOp tokenOp;
	private boolean ok;
	private ServiceSecurityException error;
	private String methodName;
	private TokenContext() {
		reset();
	}
	void reset(){
		token = new Token();
		tokenOp = TokenOp.UNINITIALIZED;
		ok = true;
		error = null;
	}
	/** 返回当前令牌 */
	public Token getToken() {
		return token;
	}
	/**
	 * 设置当前令牌
	 * @param token 不可为{@code null} 
	 * @return 
	 */
	TokenContext setToken(Token token) {
		this.token = checkNotNull(token);
		return this;
	}
	/** 返回当前令牌操作类型 */
	public TokenOp getTokenOp() {
		return tokenOp;
	}
	/**
	 * 设置当前令牌操作类型
	 * @param tokenOp 不可为{@code null}
	 * @return
	 */
	TokenContext setTokenOp(TokenOp tokenOp) {
		this.tokenOp = checkNotNull(tokenOp);
		return this;
	}
	/** 返回当前令牌操作{@link #tokenOp}执行状态,{@code true}表示正常 */
	public boolean isOk() {
		return ok;
	}
	/**
	 * 返回当前异常
	 * @return
	 */
	public ServiceSecurityException getError() {
		return error;
	}
	/**
	 * 设置当前令牌操作{@link #tokenOp}异常，并将{@link #isOk()}置为{@code false}
	 * @param error
	 * @return
	 */
	TokenContext setError(ServiceSecurityException error) {
		this.error = checkNotNull(error);
		this.ok = false;
		return this;
	}
	/**
	 * 返回当前服务方法名
	 * @return
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * 设置当前方法名
	 * @param methodName
	 * @return 
	 * @throws IllegalStateException {@link #methodName}字段已经初始化
	 * @throws IllegalArgumentException {@code methodName}为空或{@code null}
	 */
	TokenContext setMethodName(String methodName) {
		checkState(null == this.methodName,"methodName be initialized already");
		checkArgument(!Strings.isNullOrEmpty(methodName),"methodName is null or empty");
		this.methodName = methodName;
		return this;
	}
	/**
	 * 上下文对象生命期结束时调用<br>
	 * 记录令牌操作日志
	 */
	void contextDone() {
		if(TokenOp.UNINITIALIZED != tokenOp){
			if(this.ok){
				logger.info("PORT:{} OP:{}: FROM:{} BY:{}: OK",
						methodName,
						tokenOp.name(),
						ServiceUtil.clientAddressAsString(),
						getToken().owner());
			}else{
				logger.error("PORT:{} OP:{}: FROM:{} BY:{}: MESSAGE:{}",
						methodName,
						tokenOp.name(),
						ServiceUtil.clientAddressAsString(),
						getToken().owner(),
						error.getMessage());
				if(errorDetail){
					logger.error(error.getMessage(),error);
				}
			}
		}
		// 释放TLS变量
		TOKEN_CONTEXT.remove();
	}
	/**
	 * 返回当前线程拥有的{@link TokenContext}对象
	 * @return
	 */
	public static TokenContext getCurrentTokenContext(){
		return TOKEN_CONTEXT.get();
	}
}
