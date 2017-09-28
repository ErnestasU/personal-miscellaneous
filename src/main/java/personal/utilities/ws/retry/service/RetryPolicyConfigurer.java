package personal.utilities.ws.retry.service;

import personal.utilities.ws.retry.model.config.RetryPolicyConfig;

/**
 * @author Ernestas
 *
 * @param <T> - config parameter
 * @see RetryPolicyConfig
 */
public interface RetryPolicyConfigurer<T> {

	/** 
	 * @pre cannot be null
	 * @post return argument
	 */
	T addConfig(T config);
	
	T getConfig();
}
