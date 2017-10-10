package net.gdface.facelog;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.test2.TestBean2;

@ThriftService
public interface TestService {
	@ThriftMethod
	public long testLong(String s1,Integer p2);
    @ThriftMethod
    public void fooOne(ByteBuffer input);
    @ThriftMethod
    public void setBean(TestBean1 bean);
    @ThriftMethod
    public TestBean1 getBean() throws ServiceRuntime;
    @ThriftMethod
    public TestBean2 getBean2();
    @ThriftMethod
    public DeviceBean getBean3();
}