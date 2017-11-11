package gu.simplemq.exceptions;

/**
 * @author guyadong
 *
 */
public class SmqTableException extends SmqException{
	private static final long serialVersionUID = 1L;

	public SmqTableException(String message, Throwable cause) {
		super(message, cause);
	}

	public SmqTableException(String message) {
		super(message);
	}

	public SmqTableException(Throwable cause) {
		super(cause);
	}
}