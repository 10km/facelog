package gu.simplemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author guyadong
 *
 */
public interface Constant {
	public static final Logger logger = LoggerFactory.getLogger(Constant.class);

	/** milliseconds	 */
	public static final int DEFAULT_CONSUMER_CHECK_INTERVAL = 2000;

}
