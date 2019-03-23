package net.gdface.facelog;

import java.beans.PropertyDescriptor;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;

import net.gdface.facelog.ServiceSecurityException.FieldJsonTransformer;
import net.gdface.utils.BeanPropertyUtils;

/**
 * 实现{@link FieldJsonTransformer}接口,将异常类型自定义字段转为json
 * @author guyadong
 *
 * @param <T>
 */
public class ExceptionFieldTransformer<T extends Exception> implements FieldJsonTransformer<T> {
	/** 指定生成的json中field不带引号 */
	private static final int NO_FIELD_QUOTE_FEATURE=SerializerFeature.config(
			JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.QuoteFieldNames, false);
	public ExceptionFieldTransformer() {
	}

	@Override
	public String jsonOfDeclaredFields(final T input) {
		Map<String, PropertyDescriptor> fields = BeanPropertyUtils.getProperties(input.getClass(), 3, true);
		fields = Maps.filterValues(fields, new Predicate<PropertyDescriptor>() {
			@Override
			public boolean apply(PropertyDescriptor descriptor) {
				return descriptor.getWriteMethod().getDeclaringClass().equals(input.getClass());
			}
		});
		Map<String,Object> map = Maps.transformValues(fields, new Function<PropertyDescriptor, Object>(){

			@Override
			public Object apply(PropertyDescriptor descriptor) {
				try {
					return descriptor.getReadMethod().invoke(input);
				} catch (Exception e) {
					Throwables.throwIfUnchecked(e);
					throw new RuntimeException(e);
				}
			}});
		return JSON.toJSONString(
				map,
				NO_FIELD_QUOTE_FEATURE);
	}

}
