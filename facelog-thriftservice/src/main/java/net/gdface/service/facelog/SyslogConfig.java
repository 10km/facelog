package net.gdface.service.facelog;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.google.common.base.Joiner;

import net.gdface.facelog.GlobalConfig;
import net.gdface.facelog.ServiceConstant;

/**
 * log4j配置
 * @author guyadong
 *
 */
public class SyslogConfig implements ServiceConstant{
	private static final String LOG4J_ROOTCATEGORY = "log4j.rootCategory";
	private static final String LOG4J_LOGFILE = "log4j.appender.LOGFILE.File";
	private static final String PROPERTIES_FILE = "/log4j.properties";

	/** log4j log level */
	private enum LogLevel{
		/** log4j log level: OFF */OFF,
		/** log4j log level: FATAL */FATAL,
		/** log4j log level: ERROR */ERROR,
		/** log4j log level: WARN */WARN,
		/** log4j log level: INFO */INFO,
		/** log4j log level: DEBUG */DEBUG,
		/** log4j log level: ALL */ALL
	}
	private SyslogConfig() {
	}
	/**
	 * 配置log4j日志参数<br>
	 * 将用户配置文中的日志参数({@code SYSLOG_LEVEL},{@code SYSLOG_LOCATION})更新到{@value #PROPERTIES_FILE}
	 * @see PropertyConfigurator#configure(Properties)
	 */
	public static void log4jConfig(){
		
		try {
			boolean modified = false;
			Properties properties = new Properties();
			properties.load(SyslogConfig.class.getResourceAsStream(PROPERTIES_FILE));
			// 读取日志级别参数，如果没有定义就用log4j.properties中的默认值
			LogLevel level = CONFIG.get(LogLevel.class, SYSLOG_LEVEL);
			if(null != level){
				String rootcategory =properties.getProperty(LOG4J_ROOTCATEGORY).
						replaceFirst(Joiner.on("|").join(LogLevel.values()), level.name());
				properties.setProperty(LOG4J_ROOTCATEGORY, rootcategory);
				modified = true;
			}
			level = LogLevel.valueOf(properties.getProperty(LOG4J_ROOTCATEGORY).replaceAll("^("+Joiner.on("|").join(LogLevel.values())+").*", "$1"));
			logger.info("{}({}) = {}",SYSLOG_LEVEL,GlobalConfig.descriptionOf(SYSLOG_LEVEL),level);
			// 读取日志文件位置参数，如果没有定义就用log4j.properties中的默认值
			String location = CONFIG.getString(SYSLOG_LOCATION);
			if(null != location){
				properties.setProperty(LOG4J_LOGFILE, location.toString());
				modified = true;
			}
			logger.info("{}({}) = {}",SYSLOG_LOCATION,GlobalConfig.descriptionOf(SYSLOG_LOCATION),properties.getProperty(LOG4J_LOGFILE));
			if(modified){
				PropertyConfigurator.configure(properties);
			}
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}
}
