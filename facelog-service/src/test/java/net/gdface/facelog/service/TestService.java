package net.gdface.facelog.service;

import java.nio.ByteBuffer;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftField.Requiredness;
import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.service.ServiceRuntimeException;

/**
 * @author guyadong
 *
 */
@ThriftService
public interface TestService {
	@ThriftMethod
	public long testLong(String s1,@ThriftField(requiredness=Requiredness.OPTIONAL)Integer p2);
    @ThriftMethod
    public void fooOne(ByteBuffer input);
    @ThriftMethod
    public void setBean(TestBean bean);
    @ThriftMethod
    public TestBean getBean() throws ServiceRuntimeException;
    @ThriftMethod
    public DeviceBean getBean3();
    @ThriftMethod
    public Short getChar(short c);
}