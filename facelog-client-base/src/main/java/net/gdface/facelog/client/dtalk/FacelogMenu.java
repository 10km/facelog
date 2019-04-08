package net.gdface.facelog.client.dtalk;

import gu.dtalk.ItemBuilder;
import gu.dtalk.MenuItem;
import gu.dtalk.OptionType;
import gu.dtalk.RootMenu;
import gu.dtalk.event.ValueListener;
import net.gdface.facelog.client.location.ConnectConfigProvider;

import static gu.dtalk.engine.SampleConnector.DEVINFO_PROVIDER;
import com.google.common.base.MoreObjects;
import com.google.common.base.Supplier;

import static com.google.common.base.Preconditions.*;
import gu.dtalk.BaseItem;
import gu.dtalk.CmdItem;

/**
 * facelog 功能菜单
 * @author guyadong
 *
 */
public class FacelogMenu extends RootMenu{	
	// 定义基本命令名,扩展命令不在此列 
	public static final String CMD_PARAM = "parameter";
	public static final String CMD_STATUS = "status";
	public static final String CMD_VERSION = "version";
	public static final String CMD_ENABLE = "enable";
	public static final String CMD_ISENABLE = "isEnable";
	public static final String CMD_RESET="reset";
	public static final String CMD_TIME = "time";
	public static final String CMD_UPDATE = "update";
	public static final String CMD_IDLE_MSG = "idleMessage";
	public static final String CMD_PERSON_MSG = "personMessage";
	
	/** 基本命令所在菜单名 */
	private static final String MENU_CMD = "cmd";
	/* 扩展命令所在菜单名 */
	private static final String MENU_CMD_EXT = "cmdext";
	/* 单实例 */
	private static FacelogMenu activeInstance;
	/* 连接配置参数 */
	private final ConnectConfigProvider config;
	private MenuItem cmdext;
	private MenuItem commands;
	protected FacelogMenu(ConnectConfigProvider config) {
		this.config = config;
	}
	/**
	 * 初始化菜单
	 * @return
	 */
	public FacelogMenu init(){
		byte[] mac = DEVINFO_PROVIDER.getMac();
		byte[] ip = DEVINFO_PROVIDER.getIp();
		MenuItem device = 
			ItemBuilder.builder(MenuItem.class)
				.name("device")
				.uiName("设备")
				.addChilds(
						OptionType.STRING.builder().name("model").uiName("设备型号").readonly(true).instance(),
						OptionType.STRING.builder().name("name").uiName("设备名称").instance(),
						OptionType.STRING.builder().name("sn").uiName("设备序列号").instance().asValue("001122334455"),
						OptionType.IP.builder().name("IP").uiName("IP地址").readonly(true).instance().setValue(ip),
						OptionType.MAC.builder().name("mac").uiName("物理地址").readonly(true).instance().setValue(mac).setReadOnly(true),
						OptionType.STRING.builder().name("gps").uiName("位置(GPS)").readonly(true).instance(),
						OptionType.PASSWORD.builder().name("password").uiName("连接密码").instance().setValue(DEVINFO_PROVIDER.getPassword()),
						OptionType.STRING.builder().name("version").uiName("版本号").readonly(true).instance().setValue("unknow"))
				.instance();
		MenuItem facelog = 
			ItemBuilder.builder(MenuItem.class)
				.name("facelog")
				.uiName("facelog 服务器")
				.addChilds(
						OptionType.STRING.builder().name("host").uiName("主机名称").instance().setValue(config.getHost()),
						OptionType.INTEGER.builder().name("port").uiName("端口号").instance().setValue(config.getPort()))
				.instance();
		commands = 
			ItemBuilder.builder(MenuItem.class)
				.name(MENU_CMD)
				.uiName("基本设备命令")
				.addChilds(ItemBuilder.builder(CmdItem.class).name(CMD_PARAM).uiName("设置参数").addChilds(
						OptionType.STRING.builder().name("name").uiName("参数名称").description("option's full path start with '/'").instance(),
						OptionType.STRING.builder().name("value").uiName("参数值").instance()
						).instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_STATUS).uiName("获取参数").addChilds(
								OptionType.STRING.builder().name("name").uiName("参数名称").description("option's full path start with '/'").instance()
								).instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_VERSION).uiName("获取版本信息").instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_ENABLE).uiName("设备启用/禁用").addChilds(
								OptionType.BOOL.builder().name("enable").uiName("工作状态").description("true:工作状态,否则为非工作状态").instance(),
								OptionType.STRING.builder().name("message").uiName("附加消息").description("工作状态附加消息,比如'设备维修,禁止通行'").instance()
								).instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_ISENABLE).uiName("获取设备工作状态").instance(),
						ItemBuilder.builder(CmdItem.class).name("reset").uiName("设备重启").addChilds(
								OptionType.INTEGER.builder().name("schedule").uiName("延迟执行时间").description("指定执行时间(unix time[秒]),为null立即执行").instance()
								).instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_TIME).uiName("时间同步").addChilds(
								OptionType.INTEGER.builder().name("timestamp").uiName("服务器时间").description("服务器 unix 时间[秒]").instance()
								).instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_UPDATE).uiName("更新版本").addChilds(
								OptionType.URL.builder().name("url").uiName("更新版本的位置").instance(),
								OptionType.STRING.builder().name(CMD_VERSION).uiName("版本号").instance(),
								OptionType.INTEGER.builder().name("schedule").uiName("延迟执行时间").description("指定执行时间(unix time[秒]),为null立即执行").instance()
								).instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_IDLE_MSG).uiName("空闲消息").description("设置空闲时显示的消息").addChilds(
								OptionType.STRING.builder().name("message").uiName("显示的消息").instance(),
								OptionType.INTEGER.builder().name("duration").uiName("持续时间").description("持续时间[分钟],为null一直显示").instance()
								).instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_PERSON_MSG).uiName("定制消息").description("为指定人员通过时显示的临时消息").addChilds(
								OptionType.STRING.builder().name("message").uiName("显示的消息").instance(),
								OptionType.INTEGER.builder().name("id").uiName("人员/人员组ID").instance(),
								OptionType.BOOL.builder().name("isGroup").uiName("人员组标志").description("为true时,id参数为人员组ID,否则为人员ID").instance(),
								OptionType.BOOL.builder().name("onceOnly").uiName("只显示一次").description("为true时只在id指定的用户通过时显示一次").instance(),
								OptionType.INTEGER.builder().name("duration").uiName("持续时间").description("持续时间[分钟],为null一直显示").instance()
								).instance()
						)
				.instance();
		cmdext = 
				ItemBuilder.builder(MenuItem.class)
					.name(MENU_CMD_EXT)
					.uiName("扩展设备命令")
					.instance();
		addChilds(device,facelog,commands,cmdext);
		
		return this;
	}
	public FacelogMenu register(ValueListener<Object> listener){
		listener.registerTo(this);
		return this;
	}

	/**
	 * 返回{@code name}指定命令的全路径名，如果{@code name}指定命令不存在则返回{@code null}
	 * @param name
	 * @return
	 */
	public String cmdpath(String name){
		BaseItem item; 
		if((item = commands.getChild(name)) != null){
			return item.getPath();
		}else if((item = cmdext.getChild(name)) != null){
			return item.getPath();
		}
		return null;
	}
	public void addExtCmd(CmdItem... cmdItems){
		cmdItems = MoreObjects.firstNonNull(cmdItems, new CmdItem[0]);
		cmdext.addChilds(cmdItems);
	}
	/**
	 * 创建唯一实例
	 * @param config
	 * @return
	 */
	public synchronized static FacelogMenu makeActiveInstance(ConnectConfigProvider config){
		checkState(activeInstance == null,"activeInstance must be initialize only once");
		activeInstance = new FacelogMenu(config);
		return activeInstance;
	}
	/**
	 * 获取单实例，如果没有先调用{@link #makeActiveInstance(ConnectConfigProvider)}则抛出异常
	 * @return
	 */
	public static FacelogMenu getActiveInstance(){
		return checkNotNull(activeInstance,"activeInstance is null,must call makeInstance() firstly");
	}
	public static final Supplier<MenuItem> ROOT_SUPPLIER = new Supplier<MenuItem>(){

		@Override
		public MenuItem get() {
			return activeInstance;
		}};
	/**
	 * 返回指定命令的全路径名
	 * @param name
	 * @return
	 */
	public static final String pathOfCmd(String name){
		return new StringBuffer("/").append(MENU_CMD).append("/").append(name).toString();
	}
	/**
	 * 返回指定扩展命令的全路径名
	 * @param name
	 * @return
	 */
	public static final String pathOfCmdExt(String name){
		return new StringBuffer("/").append(MENU_CMD_EXT).append("/").append(name).toString();
	}
}
