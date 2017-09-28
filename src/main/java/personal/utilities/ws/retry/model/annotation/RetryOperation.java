package personal.utilities.ws.retry.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * @author Ernestas
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryOperation {
	
	int failureThreshold() default 3;

	long timeout() default 180000;
	
	String id();
	
	String[] policies() default "void";
}
