package net.gdface.facelog.client.dtalk;

import gu.dtalk.IntOption;
import gu.dtalk.ItemBuilder;
import gu.dtalk.MACOption;
import gu.dtalk.MenuItem;
import gu.dtalk.NumberValidator;
import gu.dtalk.OptionType;
import gu.dtalk.PasswordOption;
import gu.dtalk.RootMenu;
import gu.dtalk.StringOption;
import gu.dtalk.event.ValueListener;
import net.gdface.facelog.client.location.ConnectConfigProvider;

import static gu.dtalk.engine.SampleConnector.DEVINFO_PROVIDER;

import gu.dtalk.CheckOption;
import gu.dtalk.IPv4Option;

public class FacelogMenu extends RootMenu{
	private final ConnectConfigProvider config;
	public FacelogMenu(ConnectConfigProvider configType) {
		this.config = configType;
	}
	public FacelogMenu init(){
		byte[] mac = DEVINFO_PROVIDER.getMac();
		byte[] ip = DEVINFO_PROVIDER.getIp();
		MenuItem device = 
			ItemBuilder.builder(MenuItem.class)
				.name("device")
				.uiName("设备")
				.addChilds(
						ItemBuilder.builder(StringOption.class).name("name").uiName("设备名称").instance(),
						ItemBuilder.builder(StringOption.class).name("sn").uiName("设备序列号").instance().asValue("001122334455"),
						ItemBuilder.builder(IPv4Option.class).name("IP").uiName("IP地址").instance().setValue(ip).setReadOnly(true),
						ItemBuilder.builder(MACOption.class).name("mac").uiName("物理地址").instance().setReadOnly(true).setValue(mac).setReadOnly(true),
						ItemBuilder.builder(StringOption.class).name("gps").uiName("位置(GPS)").instance().setReadOnly(true),
						ItemBuilder.builder(PasswordOption.class).name("password").uiName("连接密码").instance().setValue(DEVINFO_PROVIDER.getPassword()),
						ItemBuilder.builder(StringOption.class).name("version").uiName("版本号").instance().setReadOnly(true).setValue("unknow"))
				.instance();
		MenuItem redis = 
			ItemBuilder.builder(MenuItem.class)
				.name("facelog")
				.uiName("facelog 服务器")
				.addChilds(
						ItemBuilder.builder(StringOption.class).name("host").uiName("主机名称").instance().setValue(config.getHost()),
						ItemBuilder.builder(IntOption.class).name("port").uiName("端口号").instance().setValue(config.getPort()))
				.instance();
		MenuItem test = 
			ItemBuilder.builder(MenuItem.class)
				.name("test")
				.uiName("类型测试")
				.addChilds(
						ItemBuilder.builder(new CheckOption<String>()).name("check").uiName("CHECK测试").instance()
							.addOption("中国", "zero")
							.addOption("俄罗斯", "half")
							.addOption("美国", "full")
							.setValue(1),
						OptionType.INTEGER.builder().name("integer").uiName("数字测试").value(0)
											.validator(NumberValidator.makeValidator(1024,256,128,0)).instance(),
						OptionType.URL.builder().name("url").uiName("URL测试").asValue("https://gitee.com/l0km/dtalk.git").instance(),
						OptionType.IMAGE.builder().name("image").uiName("图像测试").instance()							
						)
				.instance();
		addChilds(device,redis,test);
		
		return this;
	}
	public FacelogMenu register(ValueListener<Object> listener){
		listener.registerTo(this);
		return this;
	}
}
