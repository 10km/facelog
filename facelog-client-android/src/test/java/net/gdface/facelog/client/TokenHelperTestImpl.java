package net.gdface.facelog.client;

public class TokenHelperTestImpl extends TokenHelper {

	public TokenHelperTestImpl() {
	}

	@Override
	public String passwordOf(int id) {
		return "guyadong";
	}

	@Override
	public boolean isHashedPwd() {
		return false;
	}

}