package net.gdface.facelog;

import net.gdface.facelog.client.TokenHelper;

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