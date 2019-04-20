package net.gdface.facelog;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.configuration2.convert.DefaultConversionHandler;
import org.apache.commons.configuration2.ex.ConversionException;
import org.apache.commons.configuration2.interpol.ConfigurationInterpolator;

/**
 * 增加对{@link URI}类型的转换支持
 * @author guyadong
 *
 */
public class CustomConversionHandler extends DefaultConversionHandler {
	public static final CustomConversionHandler INSTANCE = new CustomConversionHandler();

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
