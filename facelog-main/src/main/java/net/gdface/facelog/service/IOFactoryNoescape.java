package net.gdface.facelog.service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.commons.configuration2.PropertiesConfiguration.DefaultIOFactory;
import org.apache.commons.configuration2.convert.ListDelimiterHandler;
import org.apache.commons.configuration2.convert.ValueTransformer;

/**
 * 
 * 提供unicode字符不转义(escape)直接输出的{@code PropertiesWriter}实例的{@code IOFactory}实现<br>
 * properties 是java标准支持的配置文件格式,默认编码ISO 8859-1,unicode字符会被转义(Unicode escapes)<br>
 * 本类的作用就不避免unicode字符被转义直接输出
 * @author guyadong
 *
 */
public class IOFactoryNoescape extends DefaultIOFactory {
	public static final IOFactoryNoescape INSTANCE = new IOFactoryNoescape();
	@Override
	public PropertiesWriterNoescape createPropertiesWriter(Writer out, ListDelimiterHandler handler) {
		return new PropertiesWriterNoescape(out, handler);
	}
	/**
	 * 实现properties文件中unicode字符不转义(escape)直接输出
	 * @author guyadong
	 *
	 */
	public class PropertiesWriterNoescape 
		extends org.apache.commons.configuration2.PropertiesConfiguration.PropertiesWriter {
		private final ValueTransformer TRANSFORMER = new ValueTransformer(){

			@Override
			public Object transformValue(Object value) {
				return String.valueOf(value);
			}};
		public PropertiesWriterNoescape(Writer writer, ListDelimiterHandler delHandler) {
			super(writer, delHandler);
		}

		/**
		 * 代码从父类方法中原样复制
		 */
		@Override
		public void writeProperty(String key, Object value, boolean forceSingleLine) throws IOException {
	        String v;

	        if (value instanceof List)
	        {
	            v = null;
	            List<?> values = (List<?>) value;
	            if (forceSingleLine)
	            {
	                try
	                {
	                    v = String.valueOf(getDelimiterHandler()
	                                    .escapeList(values, TRANSFORMER));
	                }
	                catch (UnsupportedOperationException uoex)
	                {
	                    // the handler may not support escaping lists,
	                    // then the list is written in multiple lines
	                }
	            }
	            if (v == null)
	            {
	                writeProperty(key, values);
	                return;
	            }
	        }
	        else
	        {
	            v = String.valueOf(getDelimiterHandler().escape(value, TRANSFORMER));
	        }

	        write(escapeKey(key));
	        write(fetchSeparator(key, value));
	        write(v);

	        writeln(null);
	    }

	}
}
