package net.gdface.facelog;

import java.nio.ByteBuffer;

import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;

@ThriftService
public interface TestService {
    @ThriftMethod
    public void fooOne(ByteBuffer input);
    @ThriftMethod
    public void setBean(TestBean bean);
    @ThriftMethod
    public TestBean getBean();
}