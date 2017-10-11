package net.gdface.facelog;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;

import net.gdface.facelog.db.DeviceBean;

@ThriftService
public interface TestService {
	@ThriftMethod
	public long testLong(String s1,Integer p2);
    @ThriftMethod
    public void fooOne(ByteBuffer input);
    @ThriftMethod
    public void setBean(TestBean bean);
    @ThriftMethod
    public TestBean getBean() throws ServiceRuntime;
    @ThriftMethod
    public DeviceBean getBean3();
}