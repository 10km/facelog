package net.gdface.facelog.service;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

/**
 * @author guyadong
 *
 */
@ThriftStruct
public final class Token{
	private long t1,t2;
	public Token() {
		super();
	}
	public Token(long t1, long t2) {
		this.t1 = t1;
		this.t2 = t2;
	}
	@ThriftField(1)
	public long getT1() {
		return t1;
	}
	@ThriftField
	public void setT1(long t1) {
		this.t1 = t1;
	}
	@ThriftField(2)
	public long getT2() {
		return t2;
	}
	@ThriftField
	public void setT2(long t2) {
		this.t2 = t2;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (t1 ^ (t1 >>> 32));
		result = prime * result + (int) (t2 ^ (t2 >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Token)) {
			return false;
		}
		Token other = (Token) obj;
		if (t1 != other.t1) {
			return false;
		}
		if (t2 != other.t2) {
			return false;
		}
		return true;
	}
}