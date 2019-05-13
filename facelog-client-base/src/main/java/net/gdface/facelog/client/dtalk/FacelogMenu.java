package net.gdface.facelog.client.dtalk;

import gu.dtalk.ItemBuilder;
import gu.dtalk.MenuItem;
import gu.dtalk.OptionBuilder;
import gu.dtalk.OptionType;
import gu.dtalk.RootMenu;
import gu.dtalk.SwitchOption;
import gu.dtalk.event.ValueChangeEvent;
import gu.dtalk.event.ValueListener;
import gu.dtalk.exception.CmdExecutionException;
import net.gdface.facelog.client.location.ConnectConfigProvider;
import net.gdface.facelog.hb.DeviceHeartbeat;

import static gu.dtalk.engine.DeviceUtils.DEVINFO_PROVIDER;
import java.util.Map;

import com.google.common.base.MoreObjects;
import com.google.common.base.Supplier;

import static com.google.common.base.Preconditions.*;
import gu.dtalk.BaseItem;
import gu.dtalk.BaseOption;
import gu.dtalk.CmdItem;
import gu.dtalk.CmdItem.ICmdAdapter;
import gu.dtalk.IntOption;

/**
 * facelog 功能菜单<br>
 * 为{@value #CMD_SET_PARAM},{@value #CMD_GET_PARAM},{@value #CMD_VERSION}基本设备命令提供了默认实现
 * @author guyadong
 *
 */
public class FacelogMenu extends RootMenu{	
	// 定义基本命令名,扩展命令不在此列 
	public static final String CMD_SET_PARAM = "setParameter";
	public static final String CMD_GET_PARAM = "getParameter";
	public static final String CMD_VERSION = "version";
	public static final String CMD_SET_STATUS = "setStatus";
	public static final String CMD_SET_STATUS_PARAM = "status";
	public static final String CMD_GET_STATUS = "getStatus";
	public static final String CMD_RESET="reset";
	public static final String CMD_TIME = "time";
	public static final String CMD_UPDATE = "update";
	public static final String CMD_IDLE_MSG = "idleMessage";
	public static final String CMD_PERSON_MSG = "personMessage";
	/** 对图像检测人脸提取人脸特征 */
	public static final String CMD_FEATURE = "feature";

	/** 基本命令所在菜单名 */
	private static final String MENU_CMD = "cmd";
	/* 扩展命令所在菜单名 */
	private static final String MENU_CMD_EXT = "cmdext";
	public static final String OPTION__DEVICE_STATUS = "/device/status";
	/* 单实例 */
	private static volatile FacelogMenu activeInstance;
	/* 连接配置参数 */
	private final ConnectConfigProvider config;
	/**
	 * 基本命令菜单
	 */
	private MenuItem commands;
	/**
	 * 扩展命令菜单
	 */
	private MenuItem cmdext;
	protected FacelogMenu(ConnectConfigProvider config) {
		this.config = config;
	}
	/**
	 * 初始化菜单
	 * @return {@link FacelogMenu}实例
	 */
	public FacelogMenu init(){
		byte[] mac = DEVINFO_PROVIDER.getMac();
		byte[] ip = DEVINFO_PROVIDER.getIp();
		MenuItem heartbeat = 
				ItemBuilder.builder(MenuItem.class)
					.name("heartbeat")
					.uiName("设备心跳")
					.hide()
					.addChilds(
							OptionType.INTEGER.builder().name("interval").uiName("心跳包间隔[秒],<=0时使用默认值").instance())
					.instance();
		MenuItem device = 
			ItemBuilder.builder(MenuItem.class)
				.name("device")
				.uiName("设备")
				.addChilds(
						OptionType.STRING.builder().name("name").uiName("设备名称").instance(),
						OptionType.STRING.builder().name("remark").uiName("备注").instance(),
						OptionType.STRING.builder().name("productName").uiName("产品名称").readonly(true).instance(),
						OptionType.STRING.builder().name("model").uiName("设备型号").readonly(true).instance(),
						OptionType.STRING.builder().name("vendor").uiName("设备供应商").readonly(true).instance(),
						OptionType.STRING.builder().name("manufacturer").uiName("设备制造商").readonly(true).instance(),
						OptionType.DATE.builder().name("madeDate").uiName("设备生产日期").instance(),
						OptionType.STRING.builder().name("sn").uiName("设备序列号").instance(),
						OptionType.IP.builder().name("IP").uiName("IP地址").readonly(true).instance().setValue(ip),
						OptionType.MAC.builder().name("mac").uiName("物理地址").readonly(true).instance().setValue(mac).setReadOnly(true),
						OptionType.STRING.builder().name("gps").uiName("位置(GPS)").readonly(true).instance(),
						OptionType.PASSWORD.builder().name("password").uiName("连接密码").instance().setValue(DEVINFO_PROVIDER.getPassword()),
						OptionType.STRING.builder().name("version").uiName("版本号").readonly(true).instance().setValue("unknow"),
						OptionBuilder.builder(IntOption.class).name("status").uiName("当前设备状态").readonly(true).value(0).hide().instance()
								/** 添加侦听器，当设备状态值改变时同步修改心跳包的状态值 */
								.addListener(new StatusListener()),
						heartbeat)
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
				.addChilds(ItemBuilder.builder(CmdItem.class).name(CMD_SET_PARAM).uiName("设置参数").hide().addChilds(
						OptionType.STRING.builder().name(CmdParamAdapter.P_NAME).uiName("参数名称").required().description("option's full path start with '/'").instance(),
						OptionType.STRING.builder().name(CmdParamAdapter.P_VALUE).uiName("参数值").required().instance()
						).instance().setCmdAdapter(new CmdParamAdapter()),
						ItemBuilder.builder(CmdItem.class).name(CMD_GET_PARAM).uiName("获取参数").hide().addChilds(
								OptionType.STRING.builder().name(CmdGetParameterAdapter.P_NAME).uiName("参数名称").required().description("option's full path start with '/'").instance()
								).instance().setCmdAdapter(new CmdGetParameterAdapter()),
						ItemBuilder.builder(CmdItem.class).name(CMD_VERSION).uiName("获取版本信息").hide().instance().setCmdAdapter(new CmdVersionAdapter()),
						ItemBuilder.builder(CmdItem.class).name(CMD_SET_STATUS).uiName("设置设备状态(启用/禁用)").addChilds(								
								OptionBuilder.builder(new SwitchOption<Integer>()).name(CMD_SET_STATUS_PARAM).uiName("状态值").required().description("0:工作状态,否则为非工作状态").instance()
									.addOption(0, "正常").setValue(0),
								OptionType.STRING.builder().name("message").uiName("附加消息").description("工作状态附加消息,比如'设备维修,禁止通行'").instance()
								).instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_GET_STATUS).uiName("获取设备工作状态").hide().instance()
								.setCmdAdapter(new CmdGetStatusAdapter()),
						ItemBuilder.builder(CmdItem.class).name("reset").uiName("设备重启").addChilds(
								OptionType.INTEGER.builder().name("schedule").uiName("延迟执行时间").description("指定执行时间(unix time[秒]),为null立即执行").instance()
								).instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_TIME).uiName("时间同步").addChilds(
								OptionType.INTEGER.builder().name("timestamp").uiName("服务器时间").required().description("服务器 unix 时间[秒]").instance()
								).instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_UPDATE).uiName("更新版本").addChilds(
								OptionType.URL.builder().name("url").uiName("更新版本的位置").required().instance(),
								OptionType.STRING.builder().name(CMD_VERSION).uiName("版本号").required().instance(),
								OptionType.INTEGER.builder().name("schedule").uiName("延迟执行时间").description("指定执行时间(unix time[秒]),为null立即执行").instance()
								).instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_IDLE_MSG).uiName("空闲消息").description("设置空闲时显示的消息").addChilds(
								OptionType.STRING.builder().name("message").uiName("显示的消息").required().instance(),
								OptionType.INTEGER.builder().name("duration").uiName("持续时间").description("持续时间[分钟],为null一直显示").instance()
								).instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_PERSON_MSG).uiName("定制消息").description("为指定人员通过时显示的临时消息").addChilds(
								OptionType.STRING.builder().name("message").uiName("显示的消息").required().instance(),
								OptionType.INTEGER.builder().name("id").uiName("人员/人员组ID").required().instance(),
								OptionType.BOOL.builder().name("isGroup").uiName("人员组标志").description("为true时,id参数为人员组ID,否则为人员ID").instance(),
								OptionType.BOOL.builder().name("onceOnly").uiName("只显示一次").description("为true时只在id指定的用户通过时显示一次").instance(),
								OptionType.INTEGER.builder().name("duration").uiName("持续时间").description("持续时间[分钟],为null一直显示").instance()
								).instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_FEATURE).uiName("提取人脸特征").hide().addChilds(
								OptionType.IMAGE.builder().name("image").uiName("人脸图像").required().instance()
								).instance()
						)
				.instance();
		cmdext = 
				ItemBuilder.builder(MenuItem.class)
					.name(MENU_CMD_EXT)
					.uiName("扩展设备命令")
					.instance();
		addChilds(device,facelog,commands,cmdext);
		registerSetStatusAdapter(new CmdSetStatusAdapter(this));
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
	/**
	 * 向{@link #cmdext}添加扩展命令
	 * @param cmdItems
	 */
	public void addExtCmd(CmdItem... cmdItems){
		cmdItems = MoreObjects.firstNonNull(cmdItems, new CmdItem[0]);
		cmdext.addChilds(cmdItems);
	}
	/**
	 * 创建根菜单唯一实例<br>
	 * 只能调用一次
	 * @param config
	 * @return
	 * @throw IllegalStateException 该方法被重复调用
	 */
	public synchronized static FacelogMenu makeActiveInstance(ConnectConfigProvider config){
		checkState(activeInstance == null,"activeInstance must be initialize only once");
		activeInstance = new FacelogMenu(config);
		return activeInstance;
	}
	/**
	 * 获取根菜单的单实例，如果没有先调用{@link #makeActiveInstance(ConnectConfigProvider)}则抛出异常
	 * @return
	 * @throw NullPointerException 还没有创建根菜单的实例
	 */
	public static FacelogMenu getActiveInstance(){
		return checkNotNull(activeInstance,"activeInstance is null,must call makeInstance() firstly");
	}
	/**
	 * 提供{@link Supplier}接口提供根单实例，适用延迟加载场景使用
	 */
	public static final Supplier<MenuItem> ROOT_SUPPLIER = new Supplier<MenuItem>(){

		/**
		 * 返回根菜单的实例，如果实例还没有创建，则返回{@code null}
		 * @see com.google.common.base.Supplier#get()
		 */
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
	public final String pathOfCmdExt(String name){
		return new StringBuffer("/").append(MENU_CMD_EXT).append("/").append(name).toString();
	}
	
	
	/**
	 * 注册设置设备状态({@value #CMD_SET_STATUS})的命令执行器
	 * @param adapter
	 */
	public void registerSetStatusAdapter(CmdSetStatusAdapter adapter){
		checkArgument(null != adapter,"adapter is null");
		findIntOption(OPTION__DEVICE_STATUS).addListener(adapter);
		findCmd(pathOfCmd(CMD_SET_STATUS)).setCmdAdapter(adapter);
	}
	/**
	 * {@value #CMD_SET_PARAM}命令实现
	 * @author guyadong
	 *
	 */
	private class CmdParamAdapter implements ICmdAdapter{
		static final String P_NAME = "name";
		static final String P_VALUE = "value";
		private CmdParamAdapter() {
		}
		@Override
		public Object apply(Map<String, Object> input) throws CmdExecutionException {
			try {
				String key = (String) input.get(P_NAME);
				Object value = input.get(P_VALUE);
				BaseOption<Object> opt = findOptionChecked(key);
				value = CmdItem.cast(value, opt.javaType());
				opt.updateFrom(value);
				return null;
			} catch (Exception e) {
				throw new CmdExecutionException(e);
			}
		}		
	}
	/**
	 * {@value #CMD_GET_PARAM}命令实现
	 * @author guyadong
	 *
	 */
	private class CmdGetParameterAdapter implements ICmdAdapter{
		static final String P_NAME = "name";
		private CmdGetParameterAdapter() {
		}
		@Override
		public Object apply(Map<String, Object> input) throws CmdExecutionException {
			try {
				String key = (String) input.get(P_NAME);
				return findOptionChecked(key).getValue();
			} catch (Exception e) {
				throw new CmdExecutionException(e);
			}
		}		
	}
	/**
	 * {@value #CMD_VERSION}命令实现
	 * @author guyadong
	 *
	 */
	private class CmdVersionAdapter implements ICmdAdapter{
		private CmdVersionAdapter() {
		}
		@Override
		public Object apply(Map<String, Object> input) throws CmdExecutionException {
			try {
				Object v = findOptionChecked("/device/version").getValue();
				return v == null ? "unknow" : v;
			} catch (Exception e) {
				throw new CmdExecutionException(e);
			}
		}		
	}
	/**
	 * {@value #CMD_GET_STATUS}命令实现
	 * @author guyadong
	 *
	 */
	private class CmdGetStatusAdapter implements ICmdAdapter{

		private CmdGetStatusAdapter() {
		}

		@Override
		public Object apply(Map<String, Object> input) throws CmdExecutionException {
			try{
				return findIntOption(OPTION__DEVICE_STATUS).getValue();
			} catch (Exception e) {
				throw new CmdExecutionException(e);
			}
		}		
	}
	/**
	 * {@value #CMD_SET_STATUS}命令实现,
	 * 应用层可继承重写{@link CmdSetStatusAdapter#doSetStatus(int)}方法实现改变状态的业务逻辑
	 * @author guyadong
	 */
	public static class CmdSetStatusAdapter extends ValueListener<Integer> implements ICmdAdapter{

		private final BaseOption<Integer> opt;
		public CmdSetStatusAdapter(FacelogMenu facelogMenu) {
			this.opt = checkNotNull(facelogMenu,"facelogMenu is null").findIntOption(OPTION__DEVICE_STATUS);
		}
		/**
		 * 应用层重写此方法实现设置设备状态的业务逻辑
		 * @param status
		 */
		public void doSetStatus(int status){
			
		}
		@Override
		public final Object apply(Map<String, Object> input) throws CmdExecutionException {
			try{
				Integer value = (Integer) input.get(CMD_SET_STATUS_PARAM);
				checkArgument(opt.validate(value),"INVALID STATUS VALUE");	
				opt.setValue(value);
				return null;
			} catch (Exception e) {
				throw new CmdExecutionException(e);
			}
		}
		@Override
		protected final void doUpdate(ValueChangeEvent<BaseOption<Integer>> event) {
			doSetStatus(event.option().getValue());
		}		
	}
	/**
	 * 设备状态参数({@link FacelogMenu#OPTION__DEVICE_STATUS})侦听器，
	 * 当设备状态值改变时同步修改心跳包的状态值
	 * @author guyadong
	 *
	 */
	private class StatusListener extends ValueListener<Integer>{
		private StatusListener() {
		}

		@Override
		protected void doUpdate(ValueChangeEvent<BaseOption<Integer>> event) {
			try {
				DeviceHeartbeat.getInstance().setStatus(event.option().getValue());				
			} catch (IllegalStateException e) {
				// DeviceHeartbeat 实例还没有创建则跳过
			}
		}
	}
}
