package net.gdface.facelog.message.exceptions;

public class SmqExcepiton extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SmqExcepiton() {
	}

	public SmqExcepiton(String message) {
		super(message);
	}

	public SmqExcepiton(Throwable cause) {
		super(cause);
	}

	public SmqExcepiton(String message, Throwable cause) {
		super(message, cause);
	}
}
