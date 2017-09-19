package gu.simplemq.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;

public class TypeUtils<T>extends TypeReference<T> {

	public  boolean isJavaBean(){
		return isJavaBean(getType());
	}
	public static final boolean isJavaBean(Type type){
		return null == type?false:ParserConfig.global.getDeserializer(type) instanceof JavaBeanDeserializer;
	}
	
    public static Class<?> getRawClass(Type type){
        if(type instanceof Class<?>){
            return (Class<?>) type;
        } else if(type instanceof ParameterizedType){
            return getRawClass(((ParameterizedType) type).getRawType());
        } else{
            throw new IllegalArgumentException("invalid type");
        }
    }
}
