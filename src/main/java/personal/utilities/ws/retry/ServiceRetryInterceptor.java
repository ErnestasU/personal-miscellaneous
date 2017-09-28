package personal.utilities.ws.retry;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import personal.utilities.ws.retry.model.MethodDescriptor;
import personal.utilities.ws.retry.model.annotation.RetryOperation;
import personal.utilities.ws.retry.model.annotation.RetryTargetClass;
import personal.utilities.ws.retry.model.exception.RetryOpenException;
import personal.utilities.ws.retry.model.exception.RetryServiceStateException;
import personal.utilities.ws.retry.service.RetryPolicyControl;
import personal.utilities.ws.retry.service.RetryPolicyHandler;
import personal.utilities.ws.retry.service.RetryPolicyHandlerRegistry;
import personal.utilities.ws.retry.service.impl.RetryPolicyHandlerImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Ernestas
 */
@Aspect
public class ServiceRetryInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRetryInterceptor.class);

    private RetryPolicyHandlerRegistry handlerRegistry;

    public static ServiceRetryInterceptor createInstance(RetryPolicyHandlerRegistry handlerRegistry) {
        return new ServiceRetryInterceptor(handlerRegistry);
    }

    private ServiceRetryInterceptor(RetryPolicyHandlerRegistry handlerRegistry) {
        this.handlerRegistry = handlerRegistry;
    }

    @Pointcut(value = "@annotation(personal.utilities.ws.retry.model.annotation.RetryOperation)")
    public void executionPoint() {
    }

    @Around("executionPoint()")
    public Object intercept(ProceedingJoinPoint jp) {

        MethodDescriptor<RetryOperation> methodDescriptor = getRetryOperationMetaData(jp, RetryOperation.class);
        RetryOperation retry = methodDescriptor.getAnnotation();
        RetryPolicyHandler handler = handlerRegistry.get(retry.id());
        if (handler == null) {
            handler = RetryPolicyHandlerImpl.RetryPolicyHandlerFactory.createHandler(retry.id(), retry);
            handlerRegistry.add(retry.id(), handler);
        }

        RetryPolicyControl control = (RetryPolicyControl) handler;

        RetryTargetClass targetClass = methodDescriptor.getClassAnnotationByMethod(RetryTargetClass.class);
        if (control.isOpen()) {
            String faultMessage = String.format("SERVICE (%s) method is not available for awhile!", targetClass.externalSystem());
            LOGGER.error(faultMessage);
            // EXCEPTION SHOULD BE IGNORED BY LOG4J FILE APPENDERS
            throw new RetryOpenException(faultMessage, targetClass.externalSystem());
        }
        try {
            Object result = jp.proceed();
            control.onSuccess();
            return result;
        } catch (Throwable e) {
            control.onFailure();
            throw new RetryServiceStateException(String.format("SERVICE (%s) method failure occurred within retry handler %s", targetClass.externalSystem(), handler.getId()),
                    targetClass.externalSystem(),
                    e);
        }
    }

    private <A extends Annotation> MethodDescriptor<A> getRetryOperationMetaData(ProceedingJoinPoint jp, Class<A> annotation) {
        if (MethodSignature.class.isAssignableFrom(jp.getSignature().getClass())) {
            MethodSignature signature = (MethodSignature) jp.getSignature();
            Method method = signature.getMethod();
            return new MethodDescriptor<A>(method, AnnotationUtils.findAnnotation(method, annotation));
        }
        throw new IllegalArgumentException("JointPoint signature cannot be resolved!");
    }
}
