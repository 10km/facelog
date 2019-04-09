package net.gdface.facelog.client;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import net.gdface.cli.BaseAppConfig;

import static net.gdface.facelog.CommonConstant.*;
/**
 * demo引擎命令行配置参数
 * @author guyadong
 *
 */
public class DemoConfig extends BaseAppConfig implements DemoConstants {
	static final DemoConfig CONSOLE_CONFIG = new DemoConfig();
	/**
	 * 服务主机名
	 */
	private String serviceHost;
	/**
	 * 服务端口号
	 */
	private int servicePort;

	public DemoConfig() {
		options.addOption(Option.builder().longOpt(SERVICE_HOST_OPTION_LONG)
				.desc(SERVICE_HOST_OPTION_DESC + DEFAULT_HOST).numberOfArgs(1).build());

		options.addOption(Option.builder().longOpt(SERVICE_PORT_OPTION_LONG)
				.desc(SERVICE_PORT_OPTION_DESC + DEFAULT_PORT).numberOfArgs(1).type(Number.class).build());
		
		defaultValue.setProperty(SERVICE_HOST_OPTION_LONG, DEFAULT_HOST);
		defaultValue.setProperty(SERVICE_PORT_OPTION_LONG, DEFAULT_PORT);

	}
	@Override
	public void loadConfig(Options options, CommandLine cmd) throws ParseException {
		super.loadConfig(options, cmd);
		this.serviceHost = getProperty(SERVICE_HOST_OPTION_LONG); 
		this.servicePort = ((Number)getProperty(SERVICE_PORT_OPTION_LONG)).intValue(); 
		
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

	@Override
	protected String getAppName() {
		return DtalkDemo.class.getSimpleName();
	}
	@Override
	protected String getHeader() {
		return "Dtalk simulator for Facelog Device(facelog设备dtalk模拟器)";
	}
}
