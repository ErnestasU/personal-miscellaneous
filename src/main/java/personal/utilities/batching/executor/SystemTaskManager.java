package personal.utilities.batching.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *  <p>
 *      Leverages concurrent tasks management
 *  </p>
 *
 *  @param <T> 
 *  		  Task results parameter
 * @author Ernestas
 */
public final class SystemTaskManager<T> implements TaskManager<T> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemTaskManager.class);
	
	private ExecutorService executorService;
	private List<Callable<T>> tasks;
	
	public SystemTaskManager(ExecutorService executorService) {
		this.executorService = executorService;
	}
	
	public SystemTaskManager() {}

	@Override
	public void addTask(SystemTask<T> task) {
		if (tasks == null) {
			tasks = new ArrayList<>();
		} else if (tasks.contains(task)) {
			LOGGER.error("Tasks list already contains target task!");
			return;
		}
		tasks.add(task);
	}

	@Override
	public void executeAll(long timeout) {
		try {
			for (Future<T> task : executorService.invokeAll(tasks)) {
				try {
					task.get(timeout, TimeUnit.MILLISECONDS);
				} catch (ExecutionException e) {
					LOGGER.error("Error occured while executing task!", e);
				} catch (TimeoutException e) {
					if (!task.isCancelled()) {
						task.cancel(true);
					}
					LOGGER.error("Task got timeout!", e);
				}
			}
		} catch(InterruptedException e) {
			LOGGER.error("Thread was interrupted! Task result was lost!", e);
		} finally {
			if (!(executorService.isShutdown())) {
				executorService.shutdownNow();
			}
		}

	}
}
