package net.gdface.facelog.service;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;
import com.google.common.base.Function;

/**
 * 访问令牌
 * @author guyadong
 *
 */
@ThriftStruct
public final class Token{
	/** 令牌类型 */
	@ThriftStruct
	public static enum TokenType{
		/** 未初始化 */UNINITIALIZED,
		/** 设备令牌 */DEVICE,
		/** 人员令牌 */PERSON,
		/** root令牌 */ROOT
	}
	/** 持有令牌的设备/人员ID */
	private int id;
	private TokenType type = TokenType.UNINITIALIZED;
	private long t1,t2;
	public Token() {
	}
	public Token(long t1, long t2) {
		this.t1 = t1;
		this.t2 = t2;
	}
	@ThriftField(1)
	public int getId() {
		return id;
	}
	@ThriftField
	public void setId(int id) {
		this.id = id;
	}
	@ThriftField(2)
	public TokenType getType() {
		return type;
	}
	@ThriftField
	public void setType(TokenType type) {
		this.type = type;
	}
	@ThriftField(3)
	public long getT1() {
		return t1;
	}
	@ThriftField
	public void setT1(long t1) {
		this.t1 = t1;
	}
	@ThriftField(4)
	public long getT2() {
		return t2;
	}
	@ThriftField
	public void setT2(long t2) {
		this.t2 = t2;
	}
	Token asDeviceToken(int deviceId){
		this.setId(deviceId);
		this.setType(TokenType.DEVICE);
		return this;
	}
	Token asPersonToken(int personId){
		this.setId(personId);
		this.setType(TokenType.PERSON);
		return this;
	}
	Token asRootToken(){
		this.setId(-1);
		this.setType(TokenType.ROOT);
		return this;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + (int) (t1 ^ (t1 >>> 32));
		result = prime * result + (int) (t2 ^ (t2 >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (id != other.id) {
			return false;
		}
		if (t1 != other.t1) {
			return false;
		}
		if (t2 != other.t2) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Token [id=");
		builder.append(id);
		builder.append(", type=");
		builder.append(type);
		builder.append(", t1=");
		builder.append(t1);
		builder.append(", t2=");
		builder.append(t2);
		builder.append("]");
		return builder.toString();
	}
	static Function<Token,String> KEY_HELPER = new Function<Token,String>(){
		@Override
		public String apply(Token input) {
			return null == input ? null : Integer.toString(input.getId());
		}};
}