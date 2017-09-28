package personal.utilities.batching.executor;

import java.util.concurrent.Callable;

/**
 * 
 * <p> Generic system task
 *
 * @param <T>
 *           returned task value
 * @author Ernestas
 */
public interface SystemTask<T> extends Callable<T> {
	
	/**
	 * <p>Task performing operation
	 *    
	 * @pre not lengthy operation
	 */
	void process();
}
