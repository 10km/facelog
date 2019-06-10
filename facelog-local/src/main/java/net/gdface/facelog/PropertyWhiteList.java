package net.gdface.facelog;

import java.util.HashSet;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import net.gdface.facelog.ServiceSecurityException.SecurityExceptionType;

import static net.gdface.facelog.CommonConstant.*;
/**
 * 系统参数访问白名单管理<br>
 * @author guyadong
 *
 */
public class PropertyWhiteList implements Predicate<String> {
	public static final PropertyWhiteList INSTANCE = new PropertyWhiteList();
	private static final HashSet<String> whitelist = Sets.newHashSet(
			HEARTBEAT_INTERVAL,
			FEATURE_PREFIX + ".*"
			);
	private PropertyWhiteList() {
	}

	/**
	 * 不在白名单内的key返回{@code false} 
	 * @param key 系统参数名,为{@code null}或空返回{@code false}
	 */
	@Override
	public boolean apply(final String key) {
		if(Strings.isNullOrEmpty(key)){
			return false;
		}
		if(whitelist.contains(key)){
			return true;
		}
		return Iterables.tryFind(whitelist,new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				// 对于通配符(*)结尾的元素,只要前面部分匹配即返回true
				if(input.endsWith("*") && key.startsWith(input.substring(0, input.length()-1))){
					return true;
				}
				return false;
			}
		}).isPresent();
	}
	/**
	 * 检查{@code key}是否在许可访问的白名单内，是则返回{@code key}，否则抛出异常
	 * @param key 系统参数名,为{@code null}或空抛出异常
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
