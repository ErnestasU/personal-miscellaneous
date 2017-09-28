package personal.utilities.ws.retry.model.exception;

/**
 * @author Ernestas
 */
public class RetryServiceStateException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private String externalSystem;

	public RetryServiceStateException() {
		super();
	}
	
	public RetryServiceStateException(String message, String externalSystem) {
		super(message);
		this.externalSystem = externalSystem;
	}
	
	public RetryServiceStateException(String message, String externalSystem, Throwable cause) {
		super(message, cause);
		this.externalSystem = externalSystem;
	}

	public RetryServiceStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RetryServiceStateException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetryServiceStateException(String message) {
		super(message);
	}

	public RetryServiceStateException(Throwable cause) {
		super(cause);
	}

	public String getExternalSystem() {
		return externalSystem;
	}

}
