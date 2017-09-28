package personal.utilities.batching.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p> Create appropriate task manager
 * It controls creation process steps, so the client won't be confused.
 * 
 * Creates fixed and cached tasks pool.
 * 
 * @author Ernestas
 */
public class SystemTaskManagerFactory implements TypeConfigurable {
	
	private ExecutorService creatableItem;
	
	/**
	 * @see #newCachedTaskQueue()
	 * @see #newFixedTaskQueue(int)
	 */
	private SystemTaskManagerFactory(ExecutorService service) {
		this.creatableItem = service;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public <T> TaskManager<T> of(Class<T> type) {
		TaskManager<T> createdItem = new SystemTaskManager<T>(creatableItem);
		clearState();
		return createdItem;
	}

	/** creates cached tasks pool*/
	public static TypeConfigurable newCachedTaskQueue() {
		return new SystemTaskManagerFactory(Executors.newCachedThreadPool());
	}
	
	/** creates fixed size tasks pool */
	public static TypeConfigurable newFixedTaskQueue(int size) {
		return new SystemTaskManagerFactory(Executors.newFixedThreadPool(size));
	}
	
	private void clearState() {
		creatableItem = null;
	}
}
