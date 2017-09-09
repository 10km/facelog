package net.gdface.facelog.message;

import java.lang.reflect.Type;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;

public class TypeUtils<T>extends TypeReference<T> {

	public  boolean isJavaBean(){
		return isJavaBean(getType());
	}
	public static final boolean isJavaBean(Type type){
		if(null == type )
			throw new NullPointerException();
		return ParserConfig.global.getDeserializer(type) instanceof JavaBeanDeserializer;
	}
}
