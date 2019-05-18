package net.gdface.facelog;

import com.google.common.base.Function;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 访问令牌
 * @author guyadong
 *
 */
@ApiModel(description="访问令牌")
public final class Token{
	static final int FAKE_ROOT_ID = -1;
	/** 令牌类型 */
	@ApiModel(description="令牌类型")
	public static enum TokenType{
		/** 未初始化 */UNINITIALIZED,
		/** 设备令牌 */DEVICE,
		/** 人员令牌 */PERSON,
		/** root令牌 */ROOT
	}
	/** 持有令牌的设备/人员ID */
	@ApiModelProperty("持有令牌的设备/人员ID")
	private int id;
	/** 令牌类型 */
	@ApiModelProperty("令牌类型")
	private TokenType type = TokenType.UNINITIALIZED;
	/** 令牌数据 */
	@ApiModelProperty("令牌数据")
	private int t1,t2,t3,t4;
	public Token() {
	}
	public Token(int t1, int t2, int t3, int t4) {
		this.t1 = t1;
		this.t2 = t2;
		this.t3 = t3;
		this.t4 = t4;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public TokenType getType() {
		return type;
	}
	public void setType(TokenType type) {
		this.type = type;
	}
	public int getT1() {
		return t1;
	}
	public void setT1(int t1) {
		this.t1 = t1;
	}
	public int getT2() {
		return t2;
	}
	public void setT2(int t2) {
		this.t2 = t2;
	}

	public int getT3() {
		return t3;
	}

	public void setT3(int t3) {
		this.t3 = t3;
	}

	public int getT4() {
		return t4;
	}

	public void setT4(int t4) {
		this.t4 = t4;
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
		this.setId(FAKE_ROOT_ID);
		this.setType(TokenType.ROOT);
		return this;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + t1;
		result = prime * result + t2;
		result = prime * result + t3;
		result = prime * result + t4;
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
		if (t3 != other.t3) {
			return false;
		}
		if (t4 != other.t4) {
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
		builder.append(", t3=");
		builder.append(t3);
		builder.append(", t4=");
		builder.append(t4);
		builder.append("]");
		return builder.toString();
	}
	/**
	 * 返回令牌所有者信息<br>
	 * 只返回{@link #type}和{@link #id}字段
	 * @return
	 */
	public String owner(){
		StringBuffer buffer = new StringBuffer();
		buffer.append(type)
			.append(".id=").append(id);
		return buffer.toString();
	}
	/**
	 * 将当前实例设置为当前上下文的令牌
	 * @return
	 */
	Token asContextToken(){
		CurrentTokenContextOp.getInstance().currentToken(this);
		return this;
	}
	/**
	 * 从复制src的所有字段到当前对象<br>
	 * src为{@code null}则跳过
	 * @param src
	 * @return
	 */
	public Token assignFrom(Token src){
		if(src != null){
			this.id=src.id;
			this.type=src.type;
			this.t1 = src.t1;
			this.t2 = src.t2;
			this.t3 = src.t3;
			this.t4 = src.t4;
		}
		return this;
	}
	static Function<Token,String> KEY_HELPER = new Function<Token,String>(){
		@Override
		public String apply(Token input) {
			return null == input ? null : Integer.toString(input.getId());
		}};	
}