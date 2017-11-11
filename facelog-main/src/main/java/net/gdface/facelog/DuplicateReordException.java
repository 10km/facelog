package net.gdface.facelog;

import com.facebook.swift.codec.ThriftStruct;

/**
 * 记录已存在
 * @author guyadong
 *
 */
@ThriftStruct
public final class DuplicateReordException extends Exception {
	private static final long serialVersionUID = 5368342551644905292L;

	public DuplicateReordException() {
	}

	public DuplicateReordException(String message) {
		super(message);
	}

	public DuplicateReordException(Throwable cause) {
		super(cause);
	}

	public DuplicateReordException(String message, Throwable cause) {
		super(message, cause);
	}
}
