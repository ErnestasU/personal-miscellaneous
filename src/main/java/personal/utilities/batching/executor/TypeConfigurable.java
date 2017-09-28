package personal.utilities.batching.executor;

/**
 * <p> Configures the result of the task
 * 
 * @see SystemTaskManagerFactory
 * @author Ernestas
 */
public interface TypeConfigurable {
	 
	/**
	 * <T> parameterized is the type of the result
	 * @return instance of the TaskManager
	 * 
	 * @see TaskManager
	 * @see SystemTaskManager
	 */
	<T> TaskManager<T> of(Class<T> type);
}
