package gu.simplemq.utils;

import java.lang.reflect.Type;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;

/**
 * @author guyadong
 *
 */
public class TypeUtils{

	public static final boolean isJavaBean(Type type){
		return null == type
				? false
				: ParserConfig.global.getDeserializer(type) instanceof JavaBeanDeserializer;
	}
}
