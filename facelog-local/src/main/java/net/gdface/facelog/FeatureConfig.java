package net.gdface.facelog;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import java.util.LinkedHashMap;
import java.util.Set;

import com.google.common.collect.Maps;

/**
 * feature相关配置参数管理类
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
		for(String name : GlobalConfig.getExplodedStringAsList(CONFIG.getString(FEATURE_SDKVERSION_WHITELIST,""))){
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
	 * 验证{@code sdkVersion}是否为有效的SDK版本号,不是则抛出异常
	 * @param sdkVersion
	 * @return always {@code sdkVersion}
	 */
	public String checkSdkVersion(String sdkVersion){
		checkArgument(validateSdkVersion(sdkVersion),
				"UNKNOW sdk version : [%s]",sdkVersion);
		return sdkVersion;
	}
	public void checkNotExceedLimit(String sdkVersion,int count){
		checkState(count < getFeatureLimitPerPerson(sdkVersion) || isFeatureAutoUpdate(),
				"feature count  exceed max limit for %s ",sdkVersion);
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
	 * @return featureAutoUpdate
	 */
	public boolean isFeatureAutoUpdate() {
		return featureAutoUpdate;
	}
	
}
