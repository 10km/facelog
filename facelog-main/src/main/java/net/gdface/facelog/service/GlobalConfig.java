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
	private static final String ROOT_XML = "root.xml";
	private static final CombinedConfiguration CONFIG = init();
	private GlobalConfig() {
	}
	private static CombinedConfiguration init(){
		try
		{
			DefaultExpressionEngine engine = new DefaultExpressionEngine(DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS);
			Configurations configs = new Configurations();
			CombinedConfiguration config = configs.combined(GlobalConfig.class.getClassLoader().getResource(ROOT_XML));
			config.setExpressionEngine(engine);
			return config;
		}
		catch(Exception e)
		{
			throw new ExceptionInInitializerError(e);
		}
	}
	/** 全局配置参数对象 */
	public static CombinedConfiguration getConfig() {
		return CONFIG;
	}
	
}
