package net.gdface.facelog;

import java.io.Serializable;
import java.nio.ByteBuffer;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

@ThriftStruct
public final class TestBean implements Cloneable,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1258100305388020798L;
	private byte[] blob;
	private Integer id;
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

	/**
	 * @return id
	 */
	@ThriftField(2)
	public Integer getId() {
		return id;
	}
	/**
	 * @param id 要设置的 id
	 */
	@ThriftField()
	public void setId(Integer id) {
		this.id = id;
	}
	public void setId(int id) {
		this.id = id;
	}
}