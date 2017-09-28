package personal.utilities.batching.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p> Simple system task.
 * Usually it should perform internal process and avoid remote service calls.
 * 
 * @author Ernestas
 */
public abstract class SimpleSystemTask implements SystemTask<Boolean>, ExceptionHandling<Exception> {
	
	private static final Logger LOG = LoggerFactory.getLogger(SimpleSystemTask.class);
	
	@Override
	public Boolean call() throws Exception {
		try {
			process();
		} catch(Exception e) {
			onError(e);
		}
		return Boolean.TRUE;
	}
	
	@Override
	public void onError(Exception e) {
		LOG.warn("Simple task failed!", e);
	}

}
