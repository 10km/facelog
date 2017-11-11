package net.gdface.facelog.client;

import static org.junit.Assert.*;

import org.junit.Test;

import com.facebook.swift.codec.metadata.ThriftCatalog;
import com.facebook.swift.codec.metadata.ThriftFieldMetadata;
import com.facebook.swift.service.metadata.ThriftMethodMetadata;
import com.facebook.swift.service.metadata.ThriftServiceMetadata;
import com.google.common.base.Preconditions;

import net.gdface.facelog.client.thrift.IFaceLog;

/**
 * @author guyadong
 *
 */
public class GetServiceMetadataTest {
	public static ThriftServiceMetadata getServiceMetadata(Class<?>type){
		Preconditions.checkNotNull(type, "type is null");
		return  new ThriftServiceMetadata(type,new ThriftCatalog());
		
	}
	@Test
	public void test() {
		ThriftServiceMetadata metadata = getServiceMetadata(IFaceLog.class);
		System.out.println(metadata.getName());
		for( ThriftMethodMetadata method: metadata.getMethods().values()){
			System.out.printf("name: %s \n", method.getName());
			int count = 0;
			for(ThriftFieldMetadata parameter:method.getParameters()){
				System.out.printf("\tparam %d: %s %s\n",count++, parameter.getName(),parameter.getThriftType().getJavaType());
			}
		}
	}

}
