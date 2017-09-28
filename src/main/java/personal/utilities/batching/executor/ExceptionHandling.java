package personal.utilities.batching.executor;

/**
 * 
 * Marks class exception handling aware
 * @author Ernestas
 *
 * @param <T>
 * 			Exception to handle
 */
public interface ExceptionHandling<T> {
	
	/**
	 * process raised exception
	 */
	void onError(T e);
}
