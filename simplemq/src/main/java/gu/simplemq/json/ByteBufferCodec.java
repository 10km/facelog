package gu.simplemq.json;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.PrimitiveArraySerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * {@link ByteBuffer} 序列化反序列化支持
 * @author guyadong
 *
 */
public class ByteBufferCodec implements ObjectSerializer, ObjectDeserializer {
	public static ByteBufferCodec instance = new ByteBufferCodec();
	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        int token = parser.lexer.token();
        if (token == JSONToken.NULL) {
        	parser.lexer.nextToken();
            return null;
        } else if(token == JSONToken.HEX  || token == JSONToken.LITERAL_STRING){
            byte[] bytes = parser.lexer.bytesValue();
            parser.lexer.nextToken();
            return (T) ByteBuffer.wrap(bytes);
        }
        throw new JSONException(String.format("invalid '%s' for ByteBuffer",JSONToken.name(token)));
	}

	@Override
	public int getFastMatchToken() {
		return JSONToken.LITERAL_STRING;
	}
	/**
	 * 返回buffer中所有字节(0~limit)
	 * @param buffer
	 * @return
	 */
	private static final byte[] getAllBytesInBuffer(ByteBuffer buffer){
		if(buffer.hasArray())return buffer.array();
		int pos = buffer.position();
		try{
			buffer.position(0);
			byte[] bytes = new byte[buffer.remaining()];
			buffer.get(bytes);
			return bytes;
		}finally{
			buffer.position(pos);
		}
	}
	/** 
	 * 直接引用{@link PrimitiveArraySerializer}实现序列化
	 */
	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
        if ( (object instanceof ByteBuffer) ) {
        	PrimitiveArraySerializer.instance.write(serializer, getAllBytesInBuffer((ByteBuffer)object), fieldName, fieldType, features);
        }else{
        	serializer.out.writeNull(SerializerFeature.WriteNullListAsEmpty);
        }
	}

}
