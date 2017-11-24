package net.gdface.facelog.client;

import static org.junit.Assert.*;

import java.net.NetworkInterface;

import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterators;

public class NetworkUtilTest {

	@Test
	public void test() {
		for(NetworkInterface nic:NetworkUtil.getNICs(NetworkUtil.Filter.PHYICAL_ONLY,NetworkUtil.Filter.UP)){
			System.out.printf("%s,MAC: %s\n",nic,NetworkUtil.getMacAddress(nic,"-"));
			System.out.printf("\t%s\n", Joiner.on(",\n\t").join(Iterators.forEnumeration(nic.getInetAddresses())));
			System.out.printf("\t%s\n", Joiner.on(",\n\t").join((nic.getInterfaceAddresses())));
		}
	}

}
