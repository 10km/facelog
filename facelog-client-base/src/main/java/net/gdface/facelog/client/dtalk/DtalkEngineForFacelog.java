package net.gdface.facelog.client.dtalk;

import gu.dtalk.engine.ItemEngine;
import gu.dtalk.engine.SampleConnector;
import gu.dtalk.redis.RedisConfigType;
import gu.simplemq.Channel;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisSubscriber;
import net.gdface.utils.NetworkUtil;

import static gu.dtalk.CommonUtils.*;
import static gu.dtalk.engine.SampleConnector.*;

import gu.dtalk.MenuItem;

public class DtalkEngineForFacelog {
	private final SampleConnector connAdapter;
	private final RedisSubscriber subscriber;
	private final byte[] devMac;
	public DtalkEngineForFacelog(RedisConfigType configType, MenuItem root) {
		JedisPoolLazy pool = JedisPoolLazy.getInstance(configType.readRedisParam(),false);
		subscriber = RedisFactory.getSubscriber(pool);
		connAdapter = new SampleConnector(pool)
				.setItemAdapter(new ItemEngine(pool).setRoot(root));
		devMac = DEVINFO_PROVIDER.getMac();
	}
	/**
	 * 启动连接
	 * @return 
	 */
	public DtalkEngineForFacelog start(){
		System.out.printf("DEVICE MAC address(设备地址): %s\n",NetworkUtil.formatMac(devMac, ":"));
		String connchname = getConnChannel(devMac);
		Channel<String> connch = new Channel<>(connchname, String.class)
				.setAdapter(connAdapter);
		subscriber.register(connch);
		System.out.printf("Connect channel registered(连接频道注册) : %s \n",connchname);
		return this;
	}

}
