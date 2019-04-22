package net.gdface.facelog;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.configuration2.convert.DefaultConversionHandler;
import org.apache.commons.configuration2.ex.ConversionException;
import org.apache.commons.configuration2.interpol.ConfigurationInterpolator;

/**
 * 增加对{@link URI}类型的转换支持<br>
 * 用法：<br>
 * 调用 {@link org.apache.commons.configuration2.AbstractConfiguration#setConversionHandler(org.apache.commons.configuration2.convert.ConversionHandler)}
 * 设置此实例<br>
 * 用于解决 commons-configuration 2.1.1不支持{@link URI}类型自动转换问题.2.1.1以后的版本已经支持{@link URI},
 * 不需要此类
 * @author guyadong
 *
 */
public class ConversionHandlerWithURI extends DefaultConversionHandler {
	public static final ConversionHandlerWithURI INSTANCE = new ConversionHandlerWithURI();

	@SuppressWarnings("unchecked")
	@Override
	protected <T> T convertValue(Object src, Class<T> targetCls, ConfigurationInterpolator ci) {
        if (src == null)
        {
            return null;
        }
        if (targetCls.isInstance(src))
        {
            return (T) src; // no conversion needed
        }
		if(src instanceof String && URI.class.equals(targetCls)){
			try {
				return (T) new URI((String) src);
			} catch (URISyntaxException e) {
		        throw new ConversionException("The value '" + src + "' (" + src.getClass() + ")"
		                + " can't be converted to a " + targetCls.getName() + " object"); 
			}
		}
		return super.convertValue(src, targetCls, ci);
	}


}
