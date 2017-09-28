package personal.utilities.logging.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p> Logs specific exception. Can be used to log any exception
 * to specific file and additional logic.
 * <p> <i>Motivation </i>: sometimes hard to find the reason of the cause occurred in the system 
 * such as database DeadLock exception. Also there are many times when such exceptions are invisible 
 * until something serious happens in the system life-cycle. The way we can prevent such thing is
 * to handle specific exceptions (FATALS) and log into specific file so it will be noticeable and the
 * problem can be solved much earlier.
 * 
 * <p> It takes advantage to its corresponding appender (parent appender) and gives
 * a client to do some logic stuff on logger event. Such as : 
 * notify developers through mail box about some serious problems.
 * 
 *<i> Usage </i>:
 *    <li> Class should be extended by a sub (sub-class).
 *    <li> Sub-class should promise to implement all abstract methods
 *    <li> Sub-class should satisfy all rules required for abstract methods realization
 *    Example: 
 *    		DeadLockAppender;
 * 
 * @see DailyRollingFileAppender
 * @see DeadLockAppender
 * 
 * @author Ernestas
 */
public abstract class SpecificExceptionAppender extends DailyRollingFileAppender {
	
	/** 
	 * <p> Specific exception matcher.
	 *  It finds exception with qualified name and compare with the desired item;
	 *  The most of the time, the string should be a stack trace retrieved from the Exception
	 *  and the target should be supplied from the sub implementation
	 *  
	 *  @see SpecificExceptionAppender#getExceptionExp()
	 */
	private static class SpecificExceptionMatcher {
		
		/** Matches exception qualified name, such as <code>java.lang.SQLException</code> */
		private static final String REGEXP_EXCEPTION = "(([a-z]*\\.){2,}[A-Z]([\\w])+Exception)([a-z_A-Z]*)";
		private static final Pattern exceptionPattern = Pattern.compile(REGEXP_EXCEPTION);
		
		/**
		 * @param usePattern 
		 *        <p> Specify if pattern should be applied. For more dynamic cases, we can use
		 *        more case-aware rules because it matches any exception in stack trace and loops until
		 *        the line end or target is matches specific group
		 */
		public static boolean match(String stackTrace, String target, boolean usePattern) {
			if (usePattern) {
				Matcher m = exceptionPattern.matcher(stackTrace);
				while (m.find()) {
					if (target.equals(m.group())) {
						return true;
					}
				}
				return false;
			}
			return StringUtils.contains(stackTrace, target);
		}
		
		/** 
		 * Match without pattern 
		 */
		public static boolean match(String stackTrace, String target) {
			return match(stackTrace, target, false);
		}
	}
	
	/** 
	 *  Retrieve exception expression. 
	 *  It should be specified within qualified name, such as:
	 *  <code> java.lang.SQLException </code>
	 */
	protected abstract String getExceptionExp();
	
	/** 
	 * Handle logging event inside sub and get the Exception stack trace.
	 */
	protected abstract String getStackTrace(LoggingEvent event);
	
	@Override
	public synchronized void doAppend(LoggingEvent event) {
		if (doFilter(event)) {
			super.doAppend(event);
		}
	}
	
	/**
	 * Handles event and checks if it is feasible (implementation inside sub) 
	 */
	private boolean doFilter(LoggingEvent event) {
		String stackTrace = getStackTrace(event);
		if (StringUtils.isNotEmpty(stackTrace)) {
			return SpecificExceptionMatcher.match(stackTrace, getExceptionExp());
		}
		return false;
	}
}
