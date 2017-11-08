package net.gdface.facelog;

import com.facebook.swift.codec.ThriftStruct;

/**
 * 服务端抛出的异常
 * @author guyadong
 *
 */
@ThriftStruct
public final class ServiceRuntime extends Exception {
	private static final long serialVersionUID = -4932379960883720487L;

	public ServiceRuntime() {
	}

	public ServiceRuntime(String message) {
		super(message);
	}

	public ServiceRuntime(Throwable cause) {
		super(cause);
	}

	public ServiceRuntime(String message, Throwable cause) {
		super(message, cause);
	}
}
