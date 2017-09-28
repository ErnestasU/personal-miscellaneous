package personal.utilities.ws.retry.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> Registry stores all handlers.
 * Handlers can be register if and ONLY if it doesn't exists in pool!
 * 
 * @author Ernestas
 */
public class RetryPolicyHandlerRegistry implements RetryPolicyRegistry<RetryPolicyHandler> {
	
	/**
	 * @author Ernestas
	 */
	private class LazyLoader {
		
		public Map<String, RetryPolicyHandler> getHandlers() {
			if (handlers == null) {
				handlers = Collections.synchronizedMap(new HashMap<String, RetryPolicyHandler>());
			}
			return handlers;
		}
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(RetryPolicyHandlerRegistry.class);

	public LazyLoader lazyLoader = new LazyLoader();
	public Map<String, RetryPolicyHandler> handlers;
	
	public RetryPolicyHandler get(String id) {
		return lazyLoader.getHandlers().get(id);
	}

	public RetryPolicyHandler add(String id, RetryPolicyHandler handler) {
		if (lazyLoader.getHandlers().containsKey(id)) {
			LOGGER.info("Retry handlers are duplicated within handler id {}", id);
			return lazyLoader.getHandlers().get(id);
		}
		if (handler == null) {
			throw new IllegalArgumentException(String.format("The Mandatory parameter is missing within id %s!", id));
		}
		return lazyLoader.getHandlers().put(id, handler);
	}

	public RetryPolicyHandler remove(String id) {
		if (lazyLoader.getHandlers().containsKey(id)) {
			return lazyLoader.getHandlers().remove(id);
		}
		// ignore silently
		return null;
	}
}
