package net.gdface.service.facelog;

import com.facebook.swift.service.ThriftXHRDecoder;
import com.facebook.swift.service.XHRHomepageProvider;
import com.google.common.io.ByteStreams;

public class FacelogXHRHomepageProvider implements XHRHomepageProvider {

	@Override
	public byte[] homepage() throws Exception {
		return ByteStreams.toByteArray(ThriftXHRDecoder.class.getResourceAsStream("/facelog_homepage.html"));
	}

}
