package net.gdface.facelog;

import static org.junit.Assert.*;

import org.junit.Test;

import com.facebook.swift.codec.metadata.ThriftCatalog;
import com.facebook.swift.codec.metadata.ThriftFieldMetadata;
import com.facebook.swift.service.metadata.ThriftMethodMetadata;
import com.facebook.swift.service.metadata.ThriftServiceMetadata;
import com.google.common.base.Preconditions;

public class TestGetServiceMetadata {
	public static ThriftServiceMetadata getServiceMetadata(Class<?>type){
		Preconditions.checkNotNull(type, "type is null");
		return  new ThriftServiceMetadata(type,new ThriftCatalog());
		
	}
	@Test
	public void test2() {
		ThriftServiceMetadata metadata = getServiceMetadata(FaceLogDefinition.class);
		int mcount=0;
		System.out.println(metadata.getName());
		for( ThriftMethodMetadata method: metadata.getDeclaredMethods().values()){
			System.out.printf("%d name: %s ", mcount++, method.getName());
			if(!method.getMethod().getName().equals(method.getName()))
				System.out.printf("original name: %s ", method.getMethod().getName());
			System.out.println();
			int pcount = 0;
			for(ThriftFieldMetadata parameter:method.getParameters()){
				System.out.printf("\tparam %d: %s %s\n",pcount++, parameter.getName(),parameter.getThriftType().getJavaType());
			}
		}
	}
}
