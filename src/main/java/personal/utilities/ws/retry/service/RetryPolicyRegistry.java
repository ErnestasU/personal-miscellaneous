package personal.utilities.ws.retry.service;

/**
 * Registry API
 * @author Ernestas
 * @param <T>
 */
public interface RetryPolicyRegistry<T> {
	
	T get(String id);

	T add(String id, T t);
	
	T remove(String id);
}
