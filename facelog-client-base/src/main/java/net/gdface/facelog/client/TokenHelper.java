package net.gdface.facelog.client;

import net.gdface.facelog.Token;
import net.gdface.facelog.db.DeviceBean;

/**
 * 用于为令牌超时自动刷新机制申请令牌时提供必要的参数<br>
 * 应用层可以继承此类根据需要重写对应的方法提供参数，以让自动刷新机制能正确运行<br>
 * <b>NOTE</b>该实例会在多线程环境被调用，继承类在实现方法时须确保线程安全
 * @author guyadong
 *
 */
public class TokenHelper {
	public static final TokenHelper DEFAULT_INSTANCE = new TokenHelper();
	protected TokenHelper() {
	}
	/**
	 * 返回用户密码(适用于web管理端)
	 * @param id 用户id({@link net.gdface.facelog.db.PersonBean}的id字段), -1 for root.<br>
	 * @return 返回{@code null}将不会执行令牌自动刷新
	 */
	public String passwordOf(int id){
		return null;
	}
	/**
	 * 返回设备信息对象(适用于设备端)
	 * @return 返回{@code null}将不会执行令牌自动刷新
	 */
	public DeviceBean deviceBean(){
		return null;
	}
	/**
	 * 密码是否为MD5校验码(适用于web管理端)
	 * @return 
	 */
	public boolean isHashedPwd(){
		return true;
	}
	/**
	 * 保存刷新后的令牌<br>
	 * 自动刷新机制获取新令牌后会调用此方法将新令牌传递给应用层
	 * @param token
	 */
	public void saveFreshedToken(Token token) {
	}
}
