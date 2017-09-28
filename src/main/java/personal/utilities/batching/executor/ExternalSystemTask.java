package personal.utilities.batching.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p> Task executes calls to remote services
 * Handles base remote services exception <a>
 * 
 * @see Exception
 * @author Ernestas
 */
public abstract class ExternalSystemTask implements SystemTask<Boolean>, ExceptionHandling<Exception> {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExternalSystemTask.class);

	@Override
	public Boolean call() throws Exception {
		try {
			process();
		} catch(Exception e) {
			onError(e);
		}
		return Boolean.TRUE;
	}
	
	/** default handling*/ 
	@Override
	public void onError(Exception e) {
		LOG.warn("External system task has failed!", e);
	}
}
