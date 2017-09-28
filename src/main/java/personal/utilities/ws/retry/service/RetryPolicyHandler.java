package personal.utilities.ws.retry.service;

import personal.utilities.ws.retry.model.constant.ServiceStatus;

/**
 * Provides basic information about policy handler
 * @author Ernestas
 */
public interface RetryPolicyHandler {

	/**
	 * handler id specific by annotation
	 */
	String getId();

	/**
	 * get current service status
	 */
	ServiceStatus getServiceStatus();

}
