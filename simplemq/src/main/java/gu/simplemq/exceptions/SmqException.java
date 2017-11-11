package gu.simplemq.exceptions;

/**
 * @author guyadong
 *
 */
public class SmqException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SmqException() {
	}

	public SmqException(String message) {
		super(message);
	}

	public SmqException(Throwable cause) {
		super(cause);
	}

	public SmqException(String message, Throwable cause) {
		super(message, cause);
	}
}
