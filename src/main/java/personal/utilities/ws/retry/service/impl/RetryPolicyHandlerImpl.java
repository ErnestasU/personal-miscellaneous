package personal.utilities.ws.retry.service.impl;

import personal.utilities.ws.retry.model.annotation.RetryOperation;
import personal.utilities.ws.retry.model.config.RetryPolicyConfig;
import personal.utilities.ws.retry.model.constant.RetryPolicy;
import personal.utilities.ws.retry.model.constant.ServiceStatus;
import personal.utilities.ws.retry.service.RetryPolicyConfigurer;
import personal.utilities.ws.retry.service.RetryPolicyControl;
import personal.utilities.ws.retry.service.RetryPolicyHandler;

import java.time.LocalDateTime;

/**
 * <p> Represents and holds a strategy within specific context.
 * 
 * <p> <b>Motivation </b> :
 * external resources usually cannot be reached for a certain time, the reason can vary (<code>HTTP 404</code>,<code> HTTP 500</code>,<code> HTTP 203</code> and so on)
 * <br/> So invoking such external resources will throw approriate exceptions and depending from the reason it can wait some time until it gets such an error.
 * To reduce such overheat and exceptions, handler strategy will reduce such calls into much cheaper flow.
 * 
 * <p> Default flow for calls controlling looks like:
 * <ol>
 * <li> If service throws exception within concrete method call, then state handler becomes <code>TRANSITIVE</code>
 * <li> If service throws exception again within concrete method call, state handler becomes <code>OPEN</code>
 * <li> While state handler is <code>OPEN</code>, invoker won't be call a real method instead of it, state handler will throw an exception for unavailability
 * <li> After fixed threshold, state handler becomes <code>TRANSITIVE</code> and gives an opportunity to call a real method 
 * <li> If it success, state handler becomes <code>CLOSED</code> (starts from 1 step again), else - <code>OPEN</code> (repeat 3 step)
 * 
 * @see RetryPolicyHandler
 * @see RetryPolicyControl
 * @see RetryPolicyConfigurer
 * @See RetryPolicyConfig
 * @author Ernestas
 */
public class RetryPolicyHandlerImpl implements RetryPolicyHandler, RetryPolicyControl, RetryPolicyConfigurer<RetryPolicyConfig> {
	
	/**
	 * @author Ernestas
	 */
	public static class RetryPolicyHandlerFactory {
		
		public synchronized static RetryPolicyHandler createHandler(String id, RetryOperation metaData) {
			RetryPolicyConfig config = new RetryPolicyConfig(id);
			config.setFailureThreshold(metaData.failureThreshold()).
			                           setTimeout(metaData.timeout());
			RetryPolicyConfigurer<RetryPolicyConfig> configurer = RetryPolicyHandlerImpl.createHandler(id);
			configurer.addConfig(config);
			return (RetryPolicyHandler)configurer;
		}
	}
	
	/**
	 * @author Ernestas
	 */
	public class ReleaseWorker extends Thread {
		
		private Object lock = new Object();
		private boolean startedOnce = false;
		
		public ReleaseWorker() {
			super();
			setDaemon(true);
		}

		@Override
		public void run() {
			startedOnce = true;
			while(true) {
				try {
					if (canReleaseState()) {
						onRelease();
					}
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// break and ignore silently
					break;
				}
			}
		}
		
		public boolean canRun() {
			return !startedOnce;
		}
		
		public void wakeUp() {
			synchronized(lock) {
				lock.notify();
			}
		}
		
		public void relaxAndWait() {
			try {
				synchronized(lock) {
					lock.wait();
				}
			} catch (InterruptedException e) {
				// ignore silently
			}
		}
		
	}

	private final String id;
	
	private volatile ServiceStatus currentStatus = ServiceStatus.CLOSED;
	
	private RetryPolicyConfig config;
	
	private volatile LocalDateTime suspendTime;
	
	private ReleaseWorker releaseWorker;
	
	private RetryPolicyHandlerImpl(String id) {
		this.id = id;
		releaseWorker = new ReleaseWorker();
	}
	
	private static RetryPolicyConfigurer<RetryPolicyConfig> createHandler(String id) {
		return new RetryPolicyHandlerImpl(id);
	}
	
	public void onRelease() {
		update(ServiceStatus.TRANSITIVE);
		releaseWorker.relaxAndWait();
	}
	
	private boolean canReleaseState() {
		return LocalDateTime.now().isAfter(suspendTime.plusMinutes(config.getFailureThreshold()));
	}
	
	public synchronized void onFailure() {
		if (isClosed()) {
			update(ServiceStatus.TRANSITIVE);
		} else if (isTransitive()) {
			update(ServiceStatus.OPEN);
			suspendTime = LocalDateTime.now();
			if (releaseWorker.canRun()) {
				releaseWorker.start();
			} else {
				releaseWorker.wakeUp();
			}
		} 
	}
	
	public synchronized void onSuccess() {
		if (isTransitive()) {
			update(ServiceStatus.CLOSED);
		}
	}
	
	public void update(ServiceStatus status) {
		this.currentStatus = status;
	}

	public boolean hasRetryPolicy(RetryPolicy policy) {
		if (getConfig() == null) {
			throw new IllegalArgumentException(String.format(
					"Retry policy config cannot be founded! Handler id {'%s'}!", this.id));
		}
		return getConfig().hasPolicy(policy);
	}

	public RetryPolicyConfig addConfig(RetryPolicyConfig config) {
		if (getConfig() != null) {
			throw new IllegalArgumentException(String.format(
					"Retry policy config cannot be overrided! Founded at handler within id %s!", this.id));
		}
		this.config = config;
		return config;
	}
	
	public RetryPolicyConfig getConfig() {
		return this.config;
	}

	public String getId() {
		return this.id;
	}

	public ServiceStatus getServiceStatus() {
		return currentStatus;
	}

	public boolean isClosed() {
		return currentStatus == ServiceStatus.CLOSED;
	}

	public boolean isOpen() {
		return currentStatus == ServiceStatus.OPEN;
	}

	public boolean isTransitive() {
		return currentStatus == ServiceStatus.TRANSITIVE;
	}
}
