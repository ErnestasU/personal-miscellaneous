package personal.utilities.logging.filter;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.hibernate.exception.LockAcquisitionException;
import personal.utilities.format.TextUtils;

/**
 * Specific exception appender.
 * Handles org.hibernate.exception.LockAcquisitionException (includes DEADLOCK either)
 * 
 * @see LockAcquisitionException
 * @author Ernestas
 */
public class DeadLockAppender extends SpecificExceptionAppender {
	
	/**
	 * <p>Null-safe.
	 * <p> if Exception exists, parses stacktrace and returns as the result
	 * 
	 * @see ExceptionUtils#getStackTrace(Throwable);
	 */
	@Override
	protected String getStackTrace(LoggingEvent event) {
		ThrowableInformation wrapper = event.getThrowableInformation();
		if (wrapper == null || wrapper.getThrowable() == null) {
			return TextUtils.BLANK;
		}
		return ExceptionUtils.getStackTrace(wrapper.getThrowable());
	}

	@Override
	protected String getExceptionExp() {
		return LockAcquisitionException.class.getName();
	}
}
