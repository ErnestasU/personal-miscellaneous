package personal.utilities.ws.retry.model.exception;

/**
 * @author Ernestas
 */
public class RetryOpenException extends RetryServiceStateException {

	private static final long serialVersionUID = 1L;
	
	public RetryOpenException() {
		super();
	}
	
	public RetryOpenException(String message, String externalSystem) {
		super(message, externalSystem);
	}

	public RetryOpenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RetryOpenException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetryOpenException(String message) {
		super(message);
	}

	public RetryOpenException(Throwable cause) {
		super(cause);
	}
}
