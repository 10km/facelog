package net.gdface.facelog;

import com.facebook.swift.codec.ThriftStruct;

/**
 * 记录已存在
 * @author guyadong
 *
 */
@ThriftStruct
public class DuplicateReord extends ServiceRuntime {
	private static final long serialVersionUID = 5368342551644905292L;

	public DuplicateReord() {
	}

	public DuplicateReord(String message) {
		super(message);
	}

	public DuplicateReord(Throwable cause) {
		super(cause);
	}

	public DuplicateReord(String message, Throwable cause) {
		super(message, cause);
	}
}
