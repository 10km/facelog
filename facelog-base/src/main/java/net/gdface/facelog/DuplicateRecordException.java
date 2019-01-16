package net.gdface.facelog;

import net.gdface.exception.BaseFaceException;

/**
 * 重复记录异常<br>
 * @author guyadong
 *
 */
public class DuplicateRecordException extends BaseFaceException {
	private static final long serialVersionUID = -315960224768993597L;	
	public DuplicateRecordException() {
	}
	
	public DuplicateRecordException(String s) {
		super(s);
	}
	
	public DuplicateRecordException(Throwable cause) {
		super(cause);
	}

}
