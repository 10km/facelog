package net.gdface.facelog.client;
import com.facebook.swift.codec.metadata.ThriftCatalog;
import com.facebook.swift.service.metadata.ThriftServiceMetadata;
import com.google.common.base.Preconditions;

public class GetService {

	public GetService() {
	}
	public static ThriftServiceMetadata getServiceMetadata(Class<?>type){
		Preconditions.checkNotNull(type, "type is null");
		return  new ThriftServiceMetadata(type,new ThriftCatalog());
		
	}
}
