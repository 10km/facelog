package net.gdface.facelog.client;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import net.gdface.cli.BaseAppConfig;

import static net.gdface.facelog.CommonConstant.*;
/**
 * 服务基本配置参数
 * @author guyadong
 *
 */
public class ConsoleConfig extends BaseAppConfig implements ConsoleConstants {
	static final ConsoleConfig CONSOLE_CONFIG = new ConsoleConfig();
	/**
	 * 服务主机名
	 */
	private String serviceHost;
	/**
	 * 服务端口号
	 */
	private int servicePort;

	private String user;
	private String password;
	public ConsoleConfig() {
		options.addOption(Option.builder().longOpt(SERVICE_HOST_OPTION_LONG)
				.desc(SERVICE_HOST_OPTION_DESC + DEFAULT_HOST).numberOfArgs(1).build());

		options.addOption(Option.builder().longOpt(SERVICE_PORT_OPTION_LONG)
				.desc(SERVICE_PORT_OPTION_DESC + DEFAULT_PORT).numberOfArgs(1).type(Number.class).build());

		options.addOption(Option.builder().longOpt(SERVICE_USER_OPTION_LONG)
				.desc(SERVICE_USER_OPTION_DESC + DEFAULT_USER).numberOfArgs(1).build());

		options.addOption(Option.builder().longOpt(SERVICE_PWD_OPTION_LONG)
				.desc(SERVICE_PWD_OPTION_DESC + DEFAULT_PWD).numberOfArgs(1).build());

		
		defaultValue.setProperty(SERVICE_HOST_OPTION_LONG, DEFAULT_HOST);
		defaultValue.setProperty(SERVICE_PORT_OPTION_LONG, DEFAULT_PORT);
		defaultValue.setProperty(SERVICE_USER_OPTION_LONG, DEFAULT_USER);
		defaultValue.setProperty(SERVICE_PWD_OPTION_LONG, DEFAULT_PWD);
	}
	@Override
	public void loadConfig(Options options, CommandLine cmd) throws ParseException {
		super.loadConfig(options, cmd);
		this.serviceHost = getProperty(SERVICE_HOST_OPTION_LONG); 
		this.servicePort = ((Number)getProperty(SERVICE_PORT_OPTION_LONG)).intValue(); 
		this.user = getProperty(SERVICE_USER_OPTION_LONG); 
		this.password = getProperty(SERVICE_PWD_OPTION_LONG); 

	}
	/**
	 * @return 服务端口号
	 */
	public int getServicePort() {
		return servicePort;
	}

	/**
	 * @return 服务主机名
	 */
	public String getServiceHost() {
		return serviceHost;
	}
	public String getUser() {
		return user;
	}
	public String getPassword() {
		return password;
	}
	
}
