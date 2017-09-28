package personal.utilities.logging.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Ernestas
 * @since 3/14/2017
 */
@Aspect
public class ClassLevelLoggingAspect {

    public static final Log LOGGER = LogFactory.getLog(ClassLevelLoggingAspect.class);

    @Pointcut("execution(public * *(..))")
    public void callingMethodIsPublic() {}

    @Pointcut("@within(lt.policija.atpeir.services.util.aspect.EnableMethodCallLogging)")
    public void callingClassIsMarkedWithAnnot() {}

    @Before("callingMethodIsPublic() && callingClassIsMarkedWithAnnot()")
    public void log(JoinPoint jp) throws Throwable {
        final Signature signature = jp.getSignature();
        LOGGER.trace("Tracing: " + signature.getDeclaringTypeName() + ": " + signature.getName());
    }
}