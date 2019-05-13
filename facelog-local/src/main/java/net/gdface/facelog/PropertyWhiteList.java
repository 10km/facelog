package net.gdface.facelog;

import java.util.HashSet;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

import net.gdface.facelog.ServiceSecurityException.SecurityExceptionType;

import static net.gdface.facelog.CommonConstant.*;
/**
 * 系统参数访问白名单
 * @author guyadong
 *
 */
public class PropertyWhiteList implements Predicate<String> {
	public static final PropertyWhiteList INSTANCE = new PropertyWhiteList();
	private final HashSet<String> whitelist = Sets.newHashSet(
			HEARTBEAT_INTERVAL
			);
	private PropertyWhiteList() {
	}

	@Override
	public boolean apply(String key) {
		return whitelist.contains(key);
	}
	/**
	 * 检查{@code key}是否在许可访问的白名单内，是则返回{@code key}，否则抛出异常
	 * @param key
	 * @return always {@code key}
	 * @throws ServiceSecurityException 不允许访问key值
	 */
	public String checkAccess(String key) throws ServiceSecurityException{
		if(apply(key)){
			return key;			
		}
		throw new ServiceSecurityException(SecurityExceptionType.ACCESS_DENIED);
	}
}
