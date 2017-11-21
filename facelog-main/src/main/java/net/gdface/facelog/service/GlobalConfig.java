package net.gdface.facelog.service;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.tree.DefaultExpressionEngine;
import org.apache.commons.configuration2.tree.DefaultExpressionEngineSymbols;

/**
 * 配置参数管理
 * @author guyadong
 *
 */
public class GlobalConfig implements ServiceConstant{
	
	public static final CombinedConfiguration config = load();
	private GlobalConfig() {
	}
	private static CombinedConfiguration load(){
		try
		{
			DefaultExpressionEngine engine = new DefaultExpressionEngine(DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS);
			Configurations configs = new Configurations();
			CombinedConfiguration config = configs.combined(GlobalConfig.class.getClassLoader().getResource("root.xml"));
			config.setExpressionEngine(engine);
			return config;
		}
		catch(Exception e)
		{
			throw new ExceptionInInitializerError(e);
		}
	}
}
