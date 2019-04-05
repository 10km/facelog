package net.gdface.facelog.client.location;

import gu.dtalk.engine.DefaultDevInfoProvider;

public class MacProvider extends DefaultDevInfoProvider {
	private static ConnectConfigType connectType = ConnectConfigType.lookupRedisConnectUnchecked();

	public MacProvider() {
		super(hostOf(),portOf());
	}
	private static String hostOf(){
		return connectType == null ? null : connectType.getHost();
	}
	private static int portOf(){
		return connectType == null ? 0 : connectType.getPort();
	}
}
