package net.gdface.facelog;

class CurrentTokenContextOp {
	private static final CurrentTokenContextOp NULL_INSTANCE = new CurrentTokenContextOp();
	private static CurrentTokenContextOp instance = NULL_INSTANCE;
	protected CurrentTokenContextOp() {
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
	
	static CurrentTokenContextOp getInstance() {
		return instance;
	}
	synchronized static void setInstance(CurrentTokenContextOp instance) {
		if(instance != null){
			CurrentTokenContextOp.instance = instance;
		}
	}
}
