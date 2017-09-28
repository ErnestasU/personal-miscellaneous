package personal.utilities.batching.executor;

/**
 * <p>The task manager API to work within tasks.
 * 
 * 
 * @see SystemTaskManager
 * @author Ernestas
 *
 * @param <T>
 * 			Task result
 */
public interface TaskManager<T> {
	
	/**
	 * Adding specific task to the pool
	 * @pre task should be SystemTask<T> (invariant)
	 * 
	 * @see SystemTask<T>
	 */
	void addTask(SystemTask<T> task);
	
	/**
	 * executes all tasks
     *
	 * @post shutdown tasks pool executor
     * @param timeout - time to wait for task result in <code>MILLISECONDS</code>
	 */
	void executeAll(long timeout);
}
