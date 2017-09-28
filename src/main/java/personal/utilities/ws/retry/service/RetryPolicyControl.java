package personal.utilities.ws.retry.service;


import personal.utilities.ws.retry.model.constant.RetryPolicy;

/**
 * @author Ernestas
 */
public interface RetryPolicyControl {
	
	void onFailure();

	void onSuccess();
	
	boolean isClosed();

	boolean isOpen();

	boolean isTransitive();

	boolean hasRetryPolicy(RetryPolicy policy);

}
