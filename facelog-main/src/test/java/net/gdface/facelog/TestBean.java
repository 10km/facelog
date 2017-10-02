package net.gdface.facelog;

import java.nio.ByteBuffer;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

@ThriftStruct
public final class TestBean {
	private byte[] blob;
	public TestBean() {
	}
	@ThriftField(name="blob", value=1)
	public ByteBuffer readBlob() {
		return ByteBuffer.wrap(blob);
	}
	@ThriftField(name="blob")
	public void writeBlob(ByteBuffer blob) {
		this.blob = blob.array();
	}
	public byte[] getBlob() {
		return blob;
	}
	public void setBlob(byte[] blob) {
		this.blob = blob;
	}
}