package gu.simplemq.exceptions;

/**
 * @author guyadong
 *
 */
public class SmqTypeException extends SmqException {
	private static final long serialVersionUID = -6300483606411131090L;

	public SmqTypeException() {
	}

	public SmqTypeException(String message) {
		super(message);
	}

	public SmqTypeException(Throwable cause) {
		super(cause);
	}

	public SmqTypeException(String message, Throwable cause) {
		super(message, cause);
	}

}
