package net.gdface.facelog;

class CurrentTokenContextOp {
	private static final CurrentTokenContextOp NULL_INSTANCE = new CurrentTokenContextOp();
	private static CurrentTokenContextOp defaultInstance = NULL_INSTANCE;
	CurrentTokenContextOp() {
	}
	/**
	 * 设置当前令牌
	 * @param token 不可为{@code null} 
	 * @return 
	 */
	void currentToken(Token token) {}
	/**
	 * 设置当前令牌操作类型
	 * @param tokenOp 不可为{@code null}
	 * @return
	 */
	void currentTokenOp(TokenOp tokenOp) {}
	/**
	 * 设置当前令牌操作{@link #tokenOp}异常，并将{@link #isOk()}置为{@code false}
	 * @param error
	 * @return
	 */
	void currentError(ServiceSecurityException error) {}
	
	static CurrentTokenContextOp getDefaultInstance() {
		return defaultInstance;
	}
	synchronized static void setDefaultInstance(CurrentTokenContextOp defaultInstance) {
		if(defaultInstance != null){
			CurrentTokenContextOp.defaultInstance = defaultInstance;
		}
	}
}
