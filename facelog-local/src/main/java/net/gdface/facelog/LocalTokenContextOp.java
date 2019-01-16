package net.gdface.facelog;

class LocalTokenContextOp extends CurrentTokenContextOp {

	LocalTokenContextOp() {
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
