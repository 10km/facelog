package net.gdface.facelog.client.location;

import java.io.IOException;

import gu.dtalk.engine.DefaultDevInfoProvider;
import net.gdface.utils.NetworkUtil;

public class MacProvider extends DefaultDevInfoProvider {
	
	private byte[] mac;

	public MacProvider() {
		try {
			ConnectConfigType type = ConnectConfigType.lookupRedisConnectUnchecked();			
			if(type == null || "127.0.0.1".equals(type.getHost()) || "localhost".equalsIgnoreCase(type.getHost())){
				mac = NetworkUtil.getCurrentMac("www.cnnic.net.cn", 80);
			}else{
				mac = NetworkUtil.getCurrentMac(type.getHost(), type.getPort());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] getMac() {
		return mac;
	}

}
