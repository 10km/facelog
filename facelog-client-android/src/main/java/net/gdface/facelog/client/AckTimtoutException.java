package net.gdface.facelog.client;

/**
 * 设备命令响应超时异常
 * @author guyadong
 *
 */
@SuppressWarnings("serial")
public class AckTimtoutException extends RuntimeException {

	public AckTimtoutException() {
	}

	public AckTimtoutException(String message) {
		super(message);
	}

	public AckTimtoutException(Throwable cause) {
		super(cause);
	}

	public AckTimtoutException(String message, Throwable cause) {
		super(message, cause);
	}

}
