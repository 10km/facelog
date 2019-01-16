package net.gdface.service.facelog;

import java.util.LinkedHashMap;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.gdface.cli.ThriftServiceConfig;
import net.gdface.facelog.IFaceLog;
import net.gdface.utils.ReflectionUtils;

/**
 * 服务配置参数
 * @author guyadong
 *
 */
public class FacelogServiceConfig extends ThriftServiceConfig implements FacelogServiceConstants {

	private IFaceLog facelog;
	private static final  FacelogServiceConfig INSTANCE = new FacelogServiceConfig();
	public FacelogServiceConfig() {
		super(DEFAULT_PORT);
		options.addOption(Option.builder().longOpt(FACEAPI_CLASS_OPTION_LONG)
				.desc(FACEAPI_CLASS_OPTION_DESC).numberOfArgs(1).build());
		options.addOption(Option.builder().longOpt(FACEAPI_STATIC_OPTION_LONG)
				.desc(FACEAPI_STATIC_OPTION_DESC).numberOfArgs(1).build());
		options.addOption(Option.builder().longOpt(FACELOG_CLASS_OPTION_LONG)
				.desc(FACELOG_FULL_CLASS_OPTION_DESC).numberOfArgs(1).build());
		options.addOption(Option.builder().longOpt(FACELOG_STATIC_OPTION_LONG)
				.desc(FACELOG_STATIC_OPTION_DESC).numberOfArgs(1).build());

		defaultValue.setProperty(FACEAPI_CLASS_OPTION_LONG,"");
		defaultValue.setProperty(FACEAPI_STATIC_OPTION_LONG,DEFAULT_STATIC_METHOD);
		defaultValue.setProperty(FACELOG_CLASS_OPTION_LONG,"");
		defaultValue.setProperty(FACELOG_STATIC_OPTION_LONG,DEFAULT_STATIC_METHOD);
	}

	@Override
	public void loadConfig(Options options, CommandLine cmd) throws ParseException {
		super.loadConfig(options, cmd);
		this.facelog = getFacelogInstance();
	}
	private IFaceLog getFacelogInstance(){
		LinkedHashMap<Class<?>,Object>ctorArgs = Maps.newLinkedHashMap();
		String faceDbClassName = (String)getProperty(FACELOG_CLASS_OPTION_LONG);

		ImmutableMap<String, Object> params = ImmutableMap.<String, Object>of(ReflectionUtils.PROP_CLASSNAME, faceDbClassName, 
				ReflectionUtils.PROP_STATICMETHODNAME, (String)getProperty(FACELOG_STATIC_OPTION_LONG),
				ReflectionUtils.PROP_CONSTRUCTORPARAMS,ctorArgs);
		try{
			return ReflectionUtils.getInstance(IFaceLog.class, params);
		} catch (Exception e) {
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}


	public IFaceLog getFacelog() {
		return facelog;
	}

	@Override
	protected String getAppName() {
		return FacelogServiceMain.class.getName();
	}

	public static FacelogServiceConfig getInstance() {
		return INSTANCE;
	}
}
