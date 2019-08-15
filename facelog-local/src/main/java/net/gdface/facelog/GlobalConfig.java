package net.gdface.facelog;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;
import org.apache.commons.configuration2.sync.ReadWriteSynchronizer;
import org.apache.commons.configuration2.sync.Synchronizer;
import org.apache.commons.configuration2.tree.DefaultExpressionEngine;
import org.apache.commons.configuration2.tree.DefaultExpressionEngineSymbols;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;

import gu.simplemq.redis.JedisPoolLazy.PropName;
import gu.simplemq.redis.JedisUtils;

import static com.google.common.base.Preconditions.checkArgument;

import net.gdface.facelog.db.Constant.JdbcProperty;
import net.gdface.utils.FaceUtilits;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 配置参数管理
 * @author guyadong
 *
 */
public class GlobalConfig implements ServiceConstant{
	/** 必须为public static final,{@value #ROOT_XML}会引用  */
	public static final String HOME_FOLDER = ".facelog";
	/** 必须为public static final,{@value #ROOT_XML}会引用  */
	public static final String USER_PROPERTIES= "config.properties";
	private static final String ENCODING = "UTF-8";
	private static final String ROOT_XML = "root.xml";
	private static final URL ROOT_URL = GlobalConfig.class.getClassLoader().getResource(ROOT_XML);
	private static final String ATTR_DESCRIPTION ="description"; 
	/** 用户自定义文件位置 ${user.home}/{@value #HOME_FOLDER}/{@value #USER_PROPERTIES} */
	private static final File USER_CONFIG_FILE = Paths.get(System.getProperty("user.home"),HOME_FOLDER,USER_PROPERTIES).toFile();
	/** 全局配置参数对象(immutable,修改无效) */
	private static final CombinedConfiguration CONFIG =readConfig();
	/** 用户定义配置对象(mutable),所有对参数的修改都基于此对象 */
	private static final PropertiesConfiguration USER_CONFIG = createUserConfig();
	private GlobalConfig() {
	}
	private static CombinedConfiguration readConfig(){
		try{
			// 指定文件编码方式,否则properties文件读取中文会是乱码,要求文件编码是UTF-8
		    FileBasedConfigurationBuilder.setDefaultEncoding(PropertiesConfiguration.class, ENCODING);
		    // 使用默认表达式引擎
			DefaultExpressionEngine engine = new DefaultExpressionEngine(DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS);
			Configurations configs = new Configurations();
			CombinedConfiguration config = configs.combined(ROOT_URL);
			config.setExpressionEngine(engine);
			// 设置同步器
			config.setSynchronizer(new ReadWriteSynchronizer());
			config.setConversionHandler(ConversionHandlerWithURI.INSTANCE);
			return config;
		}catch(Exception e){
			throw new ExceptionInInitializerError(e);
		}
	}
	private static PropertiesConfiguration createUserConfig(){
		PropertiesConfiguration userConfig ;
		if(CONFIG.getNumberOfConfigurations()>1){
			// 有自定义配置文件
			userConfig = (PropertiesConfiguration) CONFIG.getConfiguration(0);	
		}else{
			// root.xml中对用户配置文件指定为 config-forceCreate 就不会抛出此异常
			throw new IllegalStateException("NOT FOUND user config file,please check " + ROOT_URL );
		}
		// 设置同步器
		userConfig.setSynchronizer(new ReadWriteSynchronizer());
		userConfig.setIOFactory(IoFactoryNoescape.INSTANCE);
		return userConfig;
	}
	/**
	 * 返回{@code key}的attribute的表达式
	 * @param key
	 * @param attr attribute name
	 * @return
	 * @throws IllegalArgumentException 输入参数为{@code null}或空
	 */
	public static final String expressionOfAttribute(String key,String attr){
		checkArgument(!Strings.isNullOrEmpty(key) && !Strings.isNullOrEmpty(attr),"input argument must not be null or empty");
		return String.format("%s[@%s]", key,attr);		
	}
	/**
	 * 返回{@code key}的描述({@code 'description'})
	 * @param key
	 * @return
	 * @see #expressionOfAttribute(String, String)
	 */
	public static final String descriptionOf(String key){
		return CONFIG.getString(expressionOfAttribute(key,ATTR_DESCRIPTION),"");
	}
	/** 全局配置参数对象 */
	public static CombinedConfiguration getConfig() {
		return CONFIG;
	}

	/** 从配置文件中读取REDIS参数 */
	static Map<PropName,Object> makeRedisParameters(){
		HashMap<PropName, Object> params = new HashMap<PropName,Object>(16);
		// 优先使用REDIS_URI
		if(CONFIG.containsKey(REDIS_URI)){
			params.put(PropName.uri, CONFIG.get(URI.class,REDIS_URI));
		}else{
			String host = CONFIG.getString(REDIS_HOST,null);
			if(!Strings.isNullOrEmpty(host)){
				params.put(PropName.host, host);
			}
			params.put(PropName.port, CONFIG.getInteger(REDIS_PORT,null));
			params.put(PropName.database, CONFIG.getInteger(REDIS_DATABASE,null));
			String pwd = CONFIG.getString(REDIS_PASSWORD,null);
			if(!Strings.isNullOrEmpty(pwd)){
				params.put(PropName.password, pwd);
			}
		}
		params.put(PropName.timeout, CONFIG.getInteger(REDIS_TIMEOUT,null));

		if(CONFIG.containsKey(REDIS_POOL_MAXTOTAL)){
			JedisPoolConfig poolConfig = new JedisPoolConfig();
			poolConfig.setMaxTotal(CONFIG.getInt(REDIS_POOL_MAXTOTAL));
			params.put(PropName.jedisPoolConfig, poolConfig);
		}
		// 过滤掉所有为null的参数
		return Maps.filterValues(params, Predicates.notNull());
	}
	/**
	 * 从配置文件中读取WEBREDIS参数
	 * @return 参数名vs参数值映射
	 */
	static Map<String,Object> makeWebredisParameters(){
		HashMap<String, Object> params = new HashMap<String,Object>(16);
		params.put(NODEJS_EXE, valueOf(String.class,NODEJS_EXE,null));
		params.put(WEBREDIS_FILE, valueOf(String.class,WEBREDIS_FILE,null));
		params.put(WEBREDIS_HOST, valueOf(String.class,WEBREDIS_HOST,DEFAULT_WEBREDIS_HOST));
		params.put(WEBREDIS_PORT, valueOf(Integer.class,WEBREDIS_PORT,DEFAULT_WEBREDIS_PORT));
	
		// 优先使用REDIS_URI
		if(defined(WEBREDIS_RURI)){
			params.put(WEBREDIS_RURI, valueOf(URI.class,WEBREDIS_RURI));
		}else{
			// 用redis host,port,database,password值创建redis uri
			URI uri = JedisUtils.createJedisURI(
					"redis",
					valueOf(String.class,WEBREDIS_RHOST,null), 
					valueOf(Integer.class,WEBREDIS_RPORT,null), 
					valueOf(String.class,WEBREDIS_RAUTH,null), 
					valueOf(Integer.class,WEBREDIS_RDB,null));
			params.put(WEBREDIS_RURI, uri);
		}
		// 过滤掉所有为null的参数
		return Maps.filterValues(params, Predicates.notNull());
	
	}
	private static boolean defined(String key){
		if(Strings.isNullOrEmpty(key)){
			return false;
		}
		return !CONFIG.getString(key,"").matches(".*\\$\\{.+\\}.*");
	}
	private static <T>T valueOf(Class<T> clazz,String key){
		return defined(key) ? CONFIG.get(clazz, key) : null;
	} 
	private static <T>T valueOf(Class<T> clazz,String key,T defaultValue){
		return defined(key)  ? CONFIG.get(clazz, key,defaultValue) : null;
	} 
	/** log 输出REDIS参数 */
	static final void logRedisParameters(Map<PropName,Object>params){
		logger.info("redis 服务器参数:");
		if(params.containsKey(PropName.uri)){
			logger.info("{}({}):{}", PropName.uri,descriptionOf(REDIS_URI),params.get(PropName.uri));
		}else{
			if(params.containsKey(PropName.host)){
				logger.info("{}({}):{}", PropName.host,descriptionOf(REDIS_HOST),params.get(PropName.host));
			}
			if(params.containsKey(PropName.port)){
				logger.info("{}({}):{}", PropName.port,descriptionOf(REDIS_PORT),params.get(PropName.port));
			}
			if(params.containsKey(PropName.database)){
				logger.info("{}({}):{}", PropName.database,descriptionOf(REDIS_DATABASE),params.get(PropName.database));
			}
			if(params.containsKey(PropName.password)){
				Object password = params.get(PropName.password); 
				logger.info("{}({}):{}", PropName.password,descriptionOf(REDIS_PASSWORD),
						null == password ? password :"*****");
			}
		}
		if(params.containsKey(PropName.timeout)){
			logger.info("{}({}):{}", PropName.timeout,descriptionOf(REDIS_TIMEOUT),params.get(PropName.timeout));
		}

		if(params.containsKey(PropName.jedisPoolConfig)){
			logger.info("{}({}):{}", "jedisPoolConfig.maxTotal",descriptionOf(REDIS_POOL_MAXTOTAL),
					((JedisPoolConfig)params.get(PropName.jedisPoolConfig)).getMaxTotal());
		}
		if(CONFIG.containsKey(REDIS_EXE)){
			logger.info("{}:{}",descriptionOf(REDIS_EXE),CONFIG.getString(REDIS_EXE));
		}
	}
	/** log 输出令牌管理参数 */
	static void logTokenParameters(){
		logger.info("令牌管理参数:");
		logger.info("{}:{}",
				descriptionOf(TOKEN_DEVICE_VALIDATE),
				CONFIG.getBoolean(TOKEN_DEVICE_VALIDATE));
		logger.info("{}:{}",
				descriptionOf(TOKEN_PERSON_VALIDATE),
				CONFIG.getBoolean(TOKEN_PERSON_VALIDATE));
		logger.info("{}:{}",
				descriptionOf(TOKEN_PERSON_EXPIRE),
				CONFIG.getInt(TOKEN_PERSON_EXPIRE));		
	}
	
	/**
	 * 从配置文件中读取数据库配置参数
	 * @return
	 */
	static EnumMap<JdbcProperty,String> makeDatabaseConfig(){
		EnumMap<JdbcProperty, String> params = new EnumMap<JdbcProperty,String>(JdbcProperty.class);
		for(JdbcProperty prop: JdbcProperty.values()){
			String value = CONFIG.getString(prop.withPrefix(PREFIX_DATABASE), null);
			if(null !=value){
				params.put(prop, value);
			}
		}
		return params;		
	}
	static Map<String,String> toStringKey(Map<JdbcProperty,String> params){
		ImmutableMap<String, Entry<JdbcProperty, String>> m1 = Maps.uniqueIndex(params.entrySet(), new Function<Entry<JdbcProperty,String>,String>(){
			@Override
			public String apply(Entry<JdbcProperty, String> input) {
				return input.getKey().key;
			}});
		return Maps.transformValues(m1, new Function<Entry<JdbcProperty, String>,String>(){
			@Override
			public String apply(Entry<JdbcProperty, String> input) {
				return input.getValue();
			}});
	}
	/** log 输出数据库配置参数 */
	static void logDatabaseProperties(Map<JdbcProperty,String> params){
		for(Entry<JdbcProperty, String> entry:params.entrySet()){
			String value = entry.getValue();
			if(null != value){
				logger.info("{}({}):{}",entry.getKey().key,descriptionOf(entry.getKey().withPrefix(PREFIX_DATABASE)),value);
			}
		}
	}
	/**
	 * 返回指定的参数,如果参数没有定义则返回{@code null}
	 */
	public static String getProperty(String key){
		return getConfig().getString(key,null);
	}
	
	public static Map<String, String> getProperties(String prefix){
		checkArgument(!Strings.isNullOrEmpty(prefix),"prefix is null or empty");
		return toMap(getConfig().subset(prefix));
	}
	
	/**
	 * 修改指定的配置参数<br>
	 * {@link #CONFIG}是组合配置对象,是一组配置文件的镜像视图,不具备持久化能力,对其进行参数修改无效,
	 * 只有这个方法设置参数才能被正确保存的用户自定义配置文件{@link #USER_CONFIG_FILE}<br>
	 * @param key
	 * @param value
	 */
	public static void setProperty(String key,Object value){
		USER_CONFIG.setProperty(key, value);
	}
	/**
	 * 修改一组配置参数
	 * @param config
	 * @see #setProperty(String, Object)
	 */
	public static void setProperties(Map<String,? extends Object> config){
		Synchronizer sync = USER_CONFIG.getSynchronizer();
		sync.beginWrite();
		try{
			for(Entry<String, ? extends Object> entry:config.entrySet()){
				setProperty(entry.getKey(),entry.getValue());
			}
		}finally{
			sync.endWrite();
		}
	}
	/**
	 * 配置参数持久化<br>
	 * 保存修改的配置到自定义配置文件({@link #USER_CONFIG_FILE})
	 */
	public static void persistence() {
		try {
			// readConfig 方法中已经指定了文件默认编码方式为UTF-8,这里不需要再指定 
			FileHandler handler = new FileHandler(USER_CONFIG);
			handler.save(USER_CONFIG_FILE);
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		} 
	}
	/**
	 * 将指定的{@link Configuration}转为{@code Map<String,String>}
	 * @param config
	 * @return
	 */
	static Map<String,String> toMap(final Configuration config){
		Synchronizer sync = config.getSynchronizer();
		sync.beginRead();
		try{
			return Maps.asMap(ImmutableSet.copyOf(config.getKeys()),new Function<String,String>(){
				@Override
				public String apply(String input) {
					return config.getString(input);
				}});
		}finally{
			sync.endRead();
		}
	}
	/**
	 * 将{@code value}用分隔符{@code ;,\t\r\f\n}切分为不含空格和分隔符的一组字符串
	 * @param value
	 * @return {@code value}为{@code null}时返回空表
	 * @deprecated replace by {@link net.gdface.utils.FaceUtilits#elementsOf(String)}
	 */
	public static ImmutableList<String> getExplodedStringAsList(String value) {
		Builder<String> builder = ImmutableList.builder();
		if (value != null) {
			StringTokenizer st = new StringTokenizer(value, " ,;\t\n\r\f");
			while (st.hasMoreTokens()) {
				builder = builder.add(st.nextToken());
			}
		}
		return builder.build();
	}
	/**
	 * 将{@code key}指定的字符串分割转换为{@code enumType}枚举对象列表
	 * @param enumType
	 * @param key
	 * @return
	 */
	public static final <E extends Enum<E>> ImmutableList<E> 
		getEnumList(final Class<E> enumType,String key){
		checkArgument(null != enumType,"enumType is null");
		checkArgument(!Strings.isNullOrEmpty(key),"key is null or empty");
		List<String> strList = FaceUtilits.elementsOf(CONFIG.getString(key,""));
		List<E> enumList = Lists.transform(strList, new Function<String,E>(){
			@Override
			public E apply(String input) {
				try{
					return Enum.valueOf(enumType, input);
				}catch(RuntimeException e){
					return null;
				}
			}});
		return ImmutableList.copyOf(Iterators.filter(enumList.iterator(), Predicates.notNull()));
	}
	/**
	 * 将{@code key}指定的字符串分割转换为{@code enumType}枚举对象集
	 * @param enumType
	 * @param key
	 * @return
	 * @see #getEnumList(Class, String)
	 */
	public static final <E extends Enum<E>> ImmutableSet<E> 
		getEnumSet(final Class<E> enumType,String key){
		return ImmutableSet.copyOf(getEnumList(enumType, key));
	}
}
