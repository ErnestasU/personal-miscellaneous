package personal.utilities.ws.retry.model;

import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Wraps method and it's annotation.
 * @author Ernestas
 *
 * @param <T>
 */
public class MethodDescriptor<T> {
	
	private final Method target;
	private final T annotation;

	public MethodDescriptor(Method target, T annotation) {
		this.target = target;
		this.annotation = annotation;
	}
	
	/** 
	 *  Get target method class's annotation to retrieve additional meta-information
	 */
	public <A extends Annotation> A getClassAnnotationByMethod(Class<A> annotationClass) {
		Class<?> methodClass = target.getDeclaringClass();
		return AnnotationUtils.findAnnotation(methodClass, annotationClass);
	}
	
	
	public T getAnnotation() {
		return annotation;
	}
}
