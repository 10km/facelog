package net.gdface.facelog.client.location;

/**
 * facelog连接异常
 * @author guyadong
 *
 */
public class FaceLogConnectException extends Exception {

	private static final long serialVersionUID = 1L;

	public FaceLogConnectException() {
	}

	public FaceLogConnectException(String message) {
		super(message);
	}

	public FaceLogConnectException(Throwable cause) {
		super(cause);
	}

	public FaceLogConnectException(String message, Throwable cause) {
		super(message, cause);
	}

}
