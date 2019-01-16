package net.gdface.facelog;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import net.gdface.facelog.ServiceSecurityException.FieldJsonTransformer;

public class ServiceSecurityExceptionHelper {
	/** 指定生成的json中field不带引号 */
	private static final int NO_FIELD_QUOTE_FEATURE=SerializerFeature.config(
			JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.QuoteFieldNames, false);
	private static final FieldJsonTransformer<ServiceSecurityException> transformer = new FieldJsonTransformer<ServiceSecurityException>(){

		@Override
		public String jsonOfDeclaredFields(ServiceSecurityException input) {
			Map<String,Object> map = Maps.newHashMap();
			map.put("type", input.getType());
			map.put("deviceID", input.getDeviceID());
			return JSON.toJSONString(
					map,
					NO_FIELD_QUOTE_FEATURE);
		}};
	static void initServiceSecurityException(){
		ServiceSecurityException.setTransformer(transformer);
	}

}
