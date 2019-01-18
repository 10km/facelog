package net.gdface.facelog;

class LocalTokenContextOp extends CurrentTokenContextOp {
	private static final LocalTokenContextOp INSTANCE = new LocalTokenContextOp();
    static void initCurrentTokenContextOp(){
    	CurrentTokenContextOp.setInstance(LocalTokenContextOp.INSTANCE);
    }
	private  LocalTokenContextOp() {
	}

	@Override
	void currentToken(Token token) {
		TokenContext.getCurrentTokenContext().setToken(token);
	}

	@Override
	void currentTokenOp(TokenOp tokenOp) {
		TokenContext.getCurrentTokenContext().setTokenOp(tokenOp);
	}

	@Override
	void currentError(ServiceSecurityException error) {
		TokenContext.getCurrentTokenContext().setError(error);
	}

}
