package net.gdface.facelog;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

import com.google.common.base.Preconditions;

/**
 * 服务调用产生的运行时异常<br>
 * 调用service端方法时产生的所有{@link RuntimeException}在抛出到客户端时被封装在{@link ServiceRuntime}中<br>
 * @author guyadong
 *
 */
@ThriftStruct
public final class ServiceRuntime extends Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * 服务器端错误堆栈信息
	 */
	private String serverStackTraceMessage = null;

	public ServiceRuntime() {
	}
	/**
	 * @param cause
	 */
	public ServiceRuntime(Throwable cause) {
		super(stripRuntimeShell(Preconditions.checkNotNull(cause)));
		fillStackTraceMessage(getCause());
	}
	/**
	 * 以递归方式返回被{@link RuntimeException}多层封装的异常<br>
	 * @param e
	 * @return
	 */
	private static final Throwable stripRuntimeShell(Throwable e){
		if(null != e && null !=e.getCause() && e.getClass() == RuntimeException.class){
			return stripRuntimeShell(e.getCause());
		}
		return e;
	}
	/**
	 * 调用{@link #printStackTrace(PrintWriter)}将错误堆栈信息存入 {@link #serverStackTraceMessage}
	 * 
	 * @param cause
	 * @see #printStackTrace(PrintWriter)
	 */
	private void fillStackTraceMessage(Throwable cause) {
		if (null != cause) {
			StringWriter write = new StringWriter(256);
			PrintWriter pw = new PrintWriter(write);
			cause.printStackTrace(pw);
			serverStackTraceMessage = write.toString();
		}
	}

	@Override
	public String getMessage() {
		return getServerStackTraceMessage();
	}
	/**
	 * 返回服务器端异常的堆栈信息
	 * @return serverStackTraceMessage
	 */
	@ThriftField(1)
	public String getServerStackTraceMessage() {
		return serverStackTraceMessage;
	}
	/**
	 * @param serverStackTraceMessage
	 *            要设置的 serverStackTraceMessage
	 */
	@ThriftField
	public void setServerStackTraceMessage(String serverStackTraceMessage) {
		this.serverStackTraceMessage = serverStackTraceMessage;
	}
}
