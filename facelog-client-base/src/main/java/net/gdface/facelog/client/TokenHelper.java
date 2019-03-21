package net.gdface.facelog.client;

import net.gdface.facelog.db.DeviceBean;

/**
 * 用于提供令牌超时自动刷新机制
 * @author guyadong
 *
 */
public class TokenHelper {
	public static final TokenHelper DEFAULT_INSTANCE = new TokenHelper();
	protected TokenHelper() {
	}
	/**
	 * @param id 用户id({@link net.gdface.facelog.db.PersonBean}的id字段), -1 for root, 
	 * @return
	 */
	public String passwordOf(int id){
		return null;
	}
	public DeviceBean deviceBean(){
		return null;
		
	}
}
