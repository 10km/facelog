package net.gdface.facelog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.DefaultExpressionEngine;
import org.apache.commons.configuration2.tree.DefaultExpressionEngineSymbols;
import org.apache.commons.configuration2.tree.xpath.XPathExpressionEngine;
import org.junit.Test;

import net.gdface.facelog.db.Constant.JdbcProperty;
import net.gdface.facelog.service.GlobalConfig;
import net.gdface.facelog.service.IOFactoryNoescape;
import net.gdface.facelog.service.ServiceConstant;

/**
 * @author guyadong
 *
 */
public class ConfigTest implements ServiceConstant{

	@Test
	public void test() {
		try
		{
			DefaultExpressionEngineSymbols symbols =
				    new DefaultExpressionEngineSymbols.Builder(
				        DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS)
				        // Use a slash as property delimiter
				        .setPropertyDelimiter(".")
				        // Indices should be specified in curly brackets
				        .setIndexStart("{")
				        .setIndexEnd("}")
				        // For attributes use simply a @
				        .setAttributeStart("@")
				        .setAttributeEnd(null)
				        // A Backslash is used for escaping property delimiters
				        .setEscapedDelimiter("\\/")
				        .create();
			DefaultExpressionEngine engine = new DefaultExpressionEngine(symbols);
			// Now create a configuration using this expression engine
			Parameters params = new Parameters();
			FileBasedConfigurationBuilder<XMLConfiguration> builder =
			    new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
			    .configure(params.xml()
			        .setFileName("config.xml")
			        .setExpressionEngine(engine));
			XMLConfiguration config = builder.getConfiguration();
			Configurations configs = new Configurations();
			XMLConfiguration config2 = configs.xml(this.getClass().getClassLoader().getResource("config.xml"));
			config2.setExpressionEngine(engine);
		    System.out.println(config.getBoolean("token.device.validate"));
		    System.out.println(config.getInt("token.person.expire"));
		    System.out.println(config.getProperty("token.person.expire@description"));
		    // access configuration properties
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}
	@Test
	public void test2(){
		try
		{
			Configurations configs = new Configurations();
			XMLConfiguration config = configs.xml(this.getClass().getClassLoader().getResource("config.xml"));
			{
				DefaultExpressionEngine engine = new DefaultExpressionEngine(DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS);
				config.setExpressionEngine(engine);
				System.out.println(config.getBoolean("token.device.validate"));
				System.out.println(config.getInt("token.person.expire"));
				System.out.println(config.getString("token.person.expire[@description]"));
				System.out.println(config.getString("token.validate[@description]"));
			}
		    {
		    	// 需要 commons-jxpath 支持
		    	XPathExpressionEngine xpathEngine = new XPathExpressionEngine();

		    	config.setExpressionEngine(xpathEngine);
		    	System.out.println(config.getBoolean("token/device/validate"));
		    	System.out.println(config.getInt("token/person/expire"));
		    	System.out.println(config.getString("token/person/expire/@description"));
		    }
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}
	@Test
	public void test3(){
		try
		{
			Configurations configs = new Configurations();
		    FileBasedConfigurationBuilder.setDefaultEncoding(PropertiesConfiguration.class, "UTF-8");
		    PropertiesConfiguration propConfig = configs.properties(this.getClass().getClassLoader().getResource("log4j.properties"));
		    System.out.println(propConfig.getString("log4j.appender.CONSOLE.Target"));
		    System.out.println(propConfig.getBoolean("log4j.appender.LOGFILE.Append"));
		    System.out.println(propConfig.getString("test"));
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}
	@Test
	public void test4(){
		try
		{
			System.out.println("user.home:"+ System.getProperty("user.home"));
			System.out.println("user.dir:"+ System.getProperty("user.dir"));
		    FileBasedConfigurationBuilder.setDefaultEncoding(PropertiesConfiguration.class, "UTF-8");
			DefaultExpressionEngine engine = new DefaultExpressionEngine(DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS);
			Configurations configs = new Configurations();
			CombinedConfiguration config = configs.combined(this.getClass().getClassLoader().getResource("root.xml"));
			config.setExpressionEngine(engine);
		    System.out.println(config.getBoolean("token.device.validate"));
		    System.out.println(config.getInt("token.person.expire"));
		    System.out.println(config.getString("token.person.expire[@description]"));
		    System.out.println(config.getString("root.password"));
		    System.out.println(config.getString("root.password.description"));
		    System.out.println(config.getString("root.password[@description]"));
		    System.out.println(GlobalConfig.descriptionOf("root.password"));
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}
	@Test
	public void test5() throws ConfigurationException, IOException{
		CombinedConfiguration config = GlobalConfig.getConfig();
		for(String name : config.getConfigurationNameList()){
			System.out.println(name);
		}
		PropertiesConfiguration firstConfig = (PropertiesConfiguration) config.getConfiguration(0);
		firstConfig.setIOFactory(new IOFactoryNoescape());
		firstConfig.setProperty(JdbcProperty.JDBC_USERNAME.withPrefix(PREFIX_DATABASE), "中文测试");
		OutputStreamWriter wirter = new OutputStreamWriter(
				new FileOutputStream(new File("d:\\tmp\\test.properties")), "UTF-8");
		firstConfig.write(wirter);
		wirter.close();
		for(Iterator<String> itor = firstConfig.getKeys();itor.hasNext();){
			String key = itor.next();
			System.out.println(key + ":" + firstConfig.getString(key));
		}
		XMLConfiguration xmlConfig = new XMLConfiguration(config);
		xmlConfig.setProperty(JdbcProperty.JDBC_USERNAME.withPrefix(PREFIX_DATABASE), "hello");
		
		System.out.println(xmlConfig.getProperty(JdbcProperty.JDBC_USERNAME.withPrefix(PREFIX_DATABASE)));
		
	}
	@Test
	public void test6() {
		try{
			GlobalConfig.setProperty(JdbcProperty.JDBC_USERNAME.withPrefix(PREFIX_DATABASE), "大家好");
			GlobalConfig.persistence();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
