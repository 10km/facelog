package net.gdface.service.facelog;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.configuration2.CombinedConfiguration;

import net.gdface.cli.ThriftServiceConfig;
import net.gdface.facelog.CommonConstant;
import net.gdface.facelog.GlobalConfig;

/**
 * 服务配置参数
 * @author guyadong
 *
 */
public class FacelogServiceConfig extends ThriftServiceConfig implements CommonConstant {

	private static final  FacelogServiceConfig INSTANCE = new FacelogServiceConfig();
	public FacelogServiceConfig() {
		super(DEFAULT_PORT);
	}

	@Override
	public void loadConfig(Options options, CommandLine cmd) throws ParseException {
		super.loadConfig(options, cmd);
		CombinedConfiguration config = GlobalConfig.getConfig();
		/**  根据命令行参数修改内存中的全局配置参数，不写入配置文件 */
		config.setProperty(SERVER_PORT, getServicePort());
		config.setProperty(SERVER_CONNECTION_LIMIT, getConnectionLimit());
		config.setProperty(SERVER_IDLE_CONNECTION_TIMEMOUT, getIdleConnectionTimeout());
		config.setProperty(SERVER_WORKER_THREAD_COUNT, getWorkThreads());
	}

	@Override
	protected String getAppName() {
		return FacelogServiceMain.class.getName();
	}

	public static FacelogServiceConfig getInstance() {
		return INSTANCE;
	}
}
