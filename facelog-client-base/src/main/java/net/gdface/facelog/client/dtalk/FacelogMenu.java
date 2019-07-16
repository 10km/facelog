package net.gdface.facelog.client.dtalk;

import gu.dtalk.ItemBuilder;
import gu.dtalk.MenuItem;
import gu.dtalk.OptionBuilder;
import gu.dtalk.OptionType;
import gu.dtalk.RootMenu;
import gu.dtalk.StringOption;
import gu.dtalk.SwitchOption;
import gu.dtalk.event.ValueChangeEvent;
import gu.dtalk.event.ValueListener;
import gu.dtalk.exception.CmdExecutionException;
import net.gdface.facelog.client.location.ConnectConfigProvider;
import net.gdface.facelog.hb.DeviceHeartbeat;
import net.gdface.sdk.FaceApi;

import static gu.dtalk.engine.DeviceUtils.DEVINFO_PROVIDER;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;

import static com.google.common.base.Preconditions.*;
import gu.dtalk.BaseItem;
import gu.dtalk.BaseOption;
import gu.dtalk.CmdItem;
import gu.dtalk.ICmdImmediateAdapter;
import gu.dtalk.ICmdUnionAdapter;
import gu.dtalk.IntOption;
import static net.gdface.facelog.CommonConstant.*;
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
	public static final String CMD_SET_STATUS_PARAM1 = "status";
	public static final String CMD_SET_STATUS_PARAM2 = "message";
	public static final String CMD_GET_STATUS = "getStatus";
	public static final String CMD_RESET="reset";
	public static final String CMD_TIME = "time";
	public static final String CMD_UPDATE = "update";
	public static final String CMD_UPDATE_URL = "url";
	public static final String CMD_UPDATE_VERSION = "version";
	public static final String CMD_UPDATE_FORCE = "force";
	public static final String CMD_UPDATE_MD5 = "md5";
	public static final String CMD_IDLE_MSG = "idleMessage";
	public static final String CMD_PERSON_MSG = "personMessage";
	/** 算法授权 */
	public static final String CMD_LICENSE = "license";
	/** 调用faceapi接口 */
	public static final String CMD_FACEAPI = "faceapi";
	/** 调用faceapi接口参数：sdk版本号 */
	public static final String CMD_FACEAPI_SDKVERSION = "sdkVersion";
	/** 调用faceapi接口参数：接口方法名 */
	public static final String CMD_FACEAPI_METHOD = "method";
	/** 调用faceapi接口参数：接口方法参数 */
	public static final String CMD_FACEAPI_PARAMETERS = "parameters";
	/** 基本命令所在菜单名 */
	private static final String MENU_CMD = "cmd";
	/* 扩展命令所在菜单名 */
	private static final String MENU_CMD_EXT = "cmdext";
	public static final String OPTION__DEVICE_STATUS = "/device/status";
	
	public static final String OPTION_FACELOG_HOST = "/facelog/host";
	public static final String OPTION_FACELOG_PORT = "/facelog/port";
	public static final String OPTION_SDK_VERSION = "/device/sdkVersion";
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
							OptionBuilder.builder(IntOption.class).name("interval").uiName("心跳包间隔[秒],<=0时使用默认值")
							.addListener(new HeartbeatIntervalListener()).value(0).instance())
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
						OptionBuilder.builder(StringOption.class).name("password").uiName("连接密码").instance().setValue(DEVINFO_PROVIDER.getPassword())
								/** 添加侦听器，当password修改时保存 */
								.addListener(new PasswordChangeListener()),
						OptionType.STRING.builder().name("version").uiName("版本号").readonly(true).value("unknow").instance(),
						OptionType.STRING.builder().name("sdkVersion").uiName("SDK版本号").readonly(true).value("unknow").instance(),
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
						OptionType.STRING.builder().name("host").uiName("主机[:端口]").description("不指定端口号或<=0使用默认值").needReset().instance())
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
								OptionBuilder.builder(new SwitchOption<Integer>()).name(CMD_SET_STATUS_PARAM1).uiName("状态值").required().description("0:工作状态,否则为非工作状态").instance()
									.addOption(0, "正常").setValue(0),
								OptionType.STRING.builder().name(CMD_SET_STATUS_PARAM2).uiName("附加消息").description("工作状态附加消息,比如'设备维修,禁止通行'").instance()
								).instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_GET_STATUS).uiName("获取设备工作状态").hide().instance()
								.setCmdAdapter(new CmdGetStatusAdapter()),
						ItemBuilder.builder(CmdItem.class).name("reset").uiName("设备重启").addChilds(
								OptionType.INTEGER.builder().name("schedule").uiName("延迟执行时间").description("指定执行时间(unix time[秒]),为null立即执行").instance()
								).needReset().instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_TIME).uiName("时间同步").addChilds(
								OptionType.STRING.builder().name("timestamp").uiName("服务器时间").required().description("服务器时间ISO8601格式 '"+ISO8601_FORMATTER_STR +"'").instance()
								).instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_UPDATE).uiName("更新版本").addChilds(
								OptionType.URL.builder().name(CMD_UPDATE_URL).uiName("更新版本的位置").description("下载更新版本的url").required().instance(),
								OptionType.STRING.builder().name(CMD_UPDATE_VERSION).uiName("版本号").required().instance(),
								OptionType.STRING.builder().name(CMD_UPDATE_MD5).uiName("md5校验码").required().description("url下载版本的MD5校验码(32 char)").instance(),
								OptionType.BOOL.builder().name(CMD_UPDATE_FORCE).uiName("强制更新").description("是否强制更新版本,不论当前版本是否高于url下载的版本号").instance(),
								OptionType.INTEGER.builder().name("schedule").uiName("延迟执行时间").description("指定执行时间(unix time[秒]),为null立即执行").instance()
								).needReset().instance(),
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
						ItemBuilder.builder(CmdItem.class).name(CMD_LICENSE).uiName("人脸识别算法授权").addChilds(
								OptionType.STRING.builder().name("sdkVersion").uiName("算法类型").required().instance(),
								OptionType.STRING.builder().name("licenseKey").uiName("授权关键字").required().instance(),
								OptionType.STRING.builder().name("licenseCode").uiName("授权码").instance()
								).instance(),
						ItemBuilder.builder(CmdItem.class).name(CMD_FACEAPI).uiName("调用FaceApi接口").hide().addChilds(
								OptionType.STRING.builder().name(CMD_FACEAPI_SDKVERSION).uiName("算法类型").required().instance(),
								OptionType.STRING.builder().name(CMD_FACEAPI_METHOD).uiName("接口方法名").required().instance(),
								OptionType.STRING.builder().name(CMD_FACEAPI_PARAMETERS).uiName("参数列表(JSON array)").required().instance()
								).instance().setImmediateCmdAdapter(FaceApiCmdAdapter.INSTANCE)
						)
				.instance();
		cmdext = 
				ItemBuilder.builder(MenuItem.class)
					.name(MENU_CMD_EXT)
					.uiName("扩展设备命令")
					.addChilds()
					.instance();
		addChilds(device,facelog,commands,cmdext);
		registerSetStatusAdapter(new CmdSetStatusAdapter(this));
		if(config != null){
			setFacelogLocation(config.getHost(),config.getPort());
		}
		return this;
	}
	
	/**
	 * 
	 * @param host 主机名
	 * @param port 端口号
	 * @return 当前{@link FacelogMenu}对象
	 */
	public FacelogMenu setFacelogLocation(String host,Integer port){
		if(!Strings.isNullOrEmpty(host)){
			if(port != null){
				host = host.concat(":" + port);
			}
			findStringOption(OPTION_FACELOG_HOST).setValue(host);
		}
		return this;		
	}
	/**
	 * 设置facelog服务器位置
	 * @param hostAndPort 主机名[:端口号]
	 * @return
	 */
	public FacelogMenu setFacelogLocation(String hostAndPort){		
		return setFacelogLocation(hostAndPort, null);
	}
	/**
	 * only for test
	 * @param listener
	 * @return
	 */
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
	 * 注册设置设备状态({@value #CMD_SET_STATUS})的命令执行器，
	 * 设置{@value #CMD_SET_STATUS}的命令执行器务必要通过此方法执行
	 * @param adapter
	 */
	public void registerSetStatusAdapter(CmdSetStatusAdapter adapter){
		checkArgument(null != adapter,"adapter is null");
		findIntOption(OPTION__DEVICE_STATUS).addListener(adapter);
		findCmd(pathOfCmd(CMD_SET_STATUS)).setCmdAdapter(adapter);
	}
	
	/**
	 * @return 返回'cmd/faceapi'设备命令绑定的命令执行器{@link FaceApiCmdAdapter}对象
	 */
	private FaceApiCmdAdapter getFaceApiCmdAdapter(){
		ICmdUnionAdapter adapter = findCmd(pathOfCmd(CMD_FACEAPI)).getCmdAdapter();
		checkState(adapter instanceof FaceApiCmdAdapter,"MISMATCH ADAPTER TYPE, %s required",
				FaceApiCmdAdapter.class.getSimpleName());
		return (FaceApiCmdAdapter) adapter;
	}
	
	/**
	 * 为指定的sdk版本绑定{@link FaceApi}实例
	 * @param sdkVersion
	 * @param faceApi
	 * @return 当前对象
	 */
	public FacelogMenu bindFaceapi(String sdkVersion,FaceApi faceApi){
		getFaceApiCmdAdapter().bindFaceApi(sdkVersion, faceApi);
		return this;
	}
	/**
	 * 卸载指定sdk版本的{@link FaceApi}实例
	 * @param sdkVersion
	 * @return 当前对象
	 */
	public FacelogMenu unbindFaceapi(String sdkVersion){
		getFaceApiCmdAdapter().unbindFaceApi(sdkVersion);
		return this;
	}
	/**
	 * 卸载{@link FaceApi}实例
	 * @param faceApi
	 * @return 当前对象
	 */
	public FacelogMenu unbindFaceapi(FaceApi faceApi){
		getFaceApiCmdAdapter().unbindFaceApi(faceApi);
		return this;
	}
	/**
	 * {@value #CMD_SET_PARAM}命令实现
	 * @author guyadong
	 *
	 */
	private class CmdParamAdapter implements ICmdImmediateAdapter{
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
	private class CmdGetParameterAdapter implements ICmdImmediateAdapter{
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
	private class CmdVersionAdapter implements ICmdImmediateAdapter{
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
	private class CmdGetStatusAdapter implements ICmdImmediateAdapter{

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
	 * 应用层可继承重写{@link CmdSetStatusAdapter#doSetStatus(int, String)}方法实现改变状态的业务逻辑
	 * @author guyadong
	 */
	public static class CmdSetStatusAdapter extends ValueListener<Integer> implements ICmdImmediateAdapter{

		private final BaseOption<Integer> opt;
		private String message;
		public CmdSetStatusAdapter(FacelogMenu facelogMenu) {
			this.opt = checkNotNull(facelogMenu,"facelogMenu is null").findIntOption(OPTION__DEVICE_STATUS);
		}
		/**
		 * 应用层重写此方法实现设置设备状态的业务逻辑
		 * @param status 设备状态值
		 * @param message 附加消息
		 */
		public void doSetStatus(int status, String message){
			
		}
		@Override
		public final Object apply(Map<String, Object> input) throws CmdExecutionException {
			try{
				@SuppressWarnings("unchecked")
				Set<Integer> status = (Set<Integer>) input.get(CMD_SET_STATUS_PARAM1);
				checkArgument(opt.validate(status),"INVALID STATUS VALUE");				
				message = (String) input.get(CMD_SET_STATUS_PARAM2);
				opt.setValue(status.toArray(new Integer[0])[0]);
				return null;
			} catch (Exception e) {
				throw new CmdExecutionException(e);
			}
		}
		@Override
		protected final void doUpdate(ValueChangeEvent<BaseOption<Integer>> event) {
			doSetStatus(event.option().getValue(), message);
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
	/**
	 * 心跳间隔('/device/heartbeat/interval')参数侦听器
	 * @author guyadong
	 *
	 */
	private class HeartbeatIntervalListener extends ValueListener<Integer>{
		private HeartbeatIntervalListener() {
		}

		@Override
		protected void doUpdate(ValueChangeEvent<BaseOption<Integer>> event) {
			try {
				Integer interval = event.option().getValue();
				interval = MoreObjects.firstNonNull(interval, DEFAULT_HEARTBEAT_PERIOD);
				if(interval <= 0){
					interval = DEFAULT_HEARTBEAT_PERIOD;					
				}
				DeviceHeartbeat.getInstance().setInterval(interval, TimeUnit.SECONDS).start();
			
			} catch (IllegalStateException e) {
				// DeviceHeartbeat 实例还没有创建则跳过
			}
		}
	}
	/**
	 * 当密码修改时保存密码
	 * @author guyadong
	 *
	 */
	private class PasswordChangeListener extends ValueListener<String>{

		private PasswordChangeListener() {
		}

		@Override
		protected void doUpdate(ValueChangeEvent<BaseOption<String>> event) {
			DEVINFO_PROVIDER.savePassword(event.option().getValue());
		}}
}
