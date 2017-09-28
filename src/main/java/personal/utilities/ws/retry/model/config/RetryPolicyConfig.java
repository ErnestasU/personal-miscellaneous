package personal.utilities.ws.retry.model.config;

import personal.utilities.ws.retry.model.constant.RetryPolicy;

import java.util.Collections;
import java.util.List;

/**
 * Handler configuration retrieved from annotation
 * @author Ernestas
 */
public class RetryPolicyConfig {
	
	private int failureThreshold;

	private long timeout;
	
	private String id;
	
	private List<RetryPolicy> policies;
	
	public RetryPolicyConfig(String id) {
		this.id = id;
	}

	public int getFailureThreshold() {
		return failureThreshold;
	}

	public RetryPolicyConfig setFailureThreshold(int failureThreshold) {
		this.failureThreshold = failureThreshold;
		return this;
	}

	public long getTimeout() {
		return timeout;
	}

	public RetryPolicyConfig setTimeout(long timeout) {
		this.timeout = timeout;
		return this;
	}

	public String getId() {
		return id;
	}
	
	public boolean hasPolicy(RetryPolicy policy) {
		if (policies == null ) {
			policies = Collections.emptyList();
			return false;
		}
		return policies.contains(policy);
	}

	public List<RetryPolicy> getPolicies() {
		return policies;
	}
	
	public boolean addPolicy(RetryPolicy policy) {
		if (policies == null) {
			policies = Collections.emptyList();
		}
		return policies.add(policy);
	}

	public RetryPolicyConfig setPolicies(List<RetryPolicy> policies) {
		this.policies = policies;
		return this;
	}
}
