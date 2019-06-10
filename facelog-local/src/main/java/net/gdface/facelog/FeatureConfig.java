package net.gdface.facelog;

import static com.google.common.base.Preconditions.checkArgument;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Maps;

import static net.gdface.utils.FaceUtilits.*;

/**
 * feature相关配置参数管理类<br>
 * @author guyadong
 *
 */
public class FeatureConfig implements ServiceConstant{

	public static final FeatureConfig FEATURE_CONFIG = new FeatureConfig(); 
	private final LinkedHashMap<String, Integer> sdkVersionWhiteList = Maps.newLinkedHashMap();
	private final int defaultFeatureLimitPerPerson;
	private final boolean featureAutoUpdate;

	private FeatureConfig() {
		this.defaultFeatureLimitPerPerson = CONFIG.getInt(FEATURE_PERSON_LIMIT_DEFAULT, DEFAULT_FEATURE_PERSON_LIMIT);
		this.featureAutoUpdate = CONFIG.getBoolean(FEATURE_PERSON_UPDATE_AUTO ,true);
		for(String name : elementsOf(CONFIG.getString(FEATURE_SDKVERSION_WHITELIST,""))){
			sdkVersionWhiteList.put(name, CONFIG.getInt(FEATURE_PERSON_LIMIT_PREFIX + name, defaultFeatureLimitPerPerson));
		}

	}
	/** 
	 * 允许的SDK版本列表.<br>
	 * 如果设备的SDK版本号不在名单内不允许注册 
	 */
	public Set<String> getSdkVersionWhiteList(){
		return sdkVersionWhiteList.keySet();
	}

	/**
	 * 验证{@code sdkVersion}是否为有效的SDK版本号
	 * @param sdkVersion
	 * @return {@code sdkVersion}有效返回{@code true},否则返回{@code false}
	 */
	public boolean validateSdkVersion(String sdkVersion){
		return sdkVersionWhiteList.containsKey(sdkVersion);
	}
	/**
	 * 验证{@code sdkVersions}包含的SDK版本号是否都有效
	 * @param sdkVersions ','号分隔的SDK版本号的名字列表
	 * @return {@code sdkVersions}不为空且都有效返回{@code true},否则返回{@code false}
	 */
	public boolean allValidSdkVersions(String sdkVersions){
		List<String> list = elementsOf(sdkVersions);
		return list.isEmpty() ? false : sdkVersionWhiteList.keySet().containsAll(list);
	}
	/**
	 * 验证{@code sdkVersion}是否为有效的SDK版本号,不是则抛出异常
	 * @param sdkVersion
	 * @return always {@code sdkVersion}
	 * @throws IllegalArgumentException 无效的SDK版本号
	 */
	public String checkSdkVersion(String sdkVersion) throws IllegalArgumentException{
		checkArgument(validateSdkVersion(sdkVersion),
				"UNKNOW sdk version : [%s]",sdkVersion);
		return sdkVersion;
	}

	/**
	 * @param sdkVersion
	 * @return 返回{@code sdkVersion}指定版本号的特征码数量限制
	 */
	public int getFeatureLimitPerPerson(String sdkVersion) {
		Integer limit = sdkVersionWhiteList.get(sdkVersion);
		return limit == null ? defaultFeatureLimitPerPerson : limit.intValue();
	}

	/**
	 * 
	 * @return 是否开启特征数据自动更新机制
	 */
	public boolean featureAutoUpdateEnabled() {
		return featureAutoUpdate;
	}
	
}
