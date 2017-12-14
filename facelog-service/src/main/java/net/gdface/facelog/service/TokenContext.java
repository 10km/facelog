package net.gdface.facelog.service;

import static com.google.common.base.Preconditions.*;

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
	private static final boolean errorDetail;
	static{
		TlsHandler.INSTANCE.registerTls(TOKEN_CONTEXT);
		errorDetail = CONFIG.getBoolean(SYSLOG_OP_TOKEN_ERRORDETAIL);
	}
	private Token token;
	private TokenOp tokenOp;
	private boolean ok;
	private ServiceSecurityException error;
	private TokenContext() {
		reset();
	}
	public void reset(){
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
	 */
	public void setToken(Token token) {
		this.token = checkNotNull(token);
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
	public TokenContext setTokenOp(TokenOp tokenOp) {
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
	public TokenContext setError(ServiceSecurityException error) {
		this.error = checkNotNull(error);
		this.ok = false;
		return this;
	}
	/**
	 * 记录令牌操作日志
	 */
	void onPortDone(Object context, String methodName) {
		if(TokenOp.UNINITIALIZED != tokenOp){
			if(this.ok){
				logger.info("PORT:{} OP:{}: FROM:{} BY:{}: OK",
						methodName,
						tokenOp.name(),
						ServiceUtil.clientAddressAsString(),
						getToken().owner());
			}else{
				logger.error("PORT:{} OP:{}: FROM:{} BY:{}: ERROR:{}",
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
	}
	/**
	 * 返回当前线程拥有的{@link TokenContext}对象
	 * @return
	 */
	public static TokenContext getCurrentTokenContext(){
		return TOKEN_CONTEXT.get();
	}
}
