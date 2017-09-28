package personal.utilities.logging.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;

/**
 * <p>
 *     Probes each <i>Data access</i> method invocation and measures it's transaction "weight" e.g. <br/>
 *     if query is not compliant with metrics (execution time), it is recorded to specific log file for
 *     further analysis.
 * </p>
 * @see #queryExecutionTimeThreshold
 * @author Ernestas Uscila
 */
@Aspect
public class HibernateStatisticsLogger implements InitializingBean {

    private static final Log LOGGER = LogFactory.getLog(HibernateStatisticsLogger.class);

    @Autowired
    private EntityManagerFactory emFactory;

    /** query execution threshold in millis */
    private int queryExecutionTimeThreshold = 500;

    @Around("within(lt.policija.atpeir.dao..*)")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        SessionFactory sessionFactory = (SessionFactory)emFactory;
        Statistics statistics = sessionFactory.getStatistics();
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        LOGGER.info("Method '" + pjp.getSignature()+"' execution elapsed time (in millis) :" + (end - start));
        StringBuilder sb = new StringBuilder();
        long slowestQueryExecTime = statistics.getQueryExecutionMaxTime();
        if (slowestQueryExecTime > queryExecutionTimeThreshold) {
            sb.append("\n");
            sb.append("============================================================================");
            sb.append("\n");
            sb.append("WARNING! The following method generates queries that exceeding execution time's threshold!");
            sb.append("\n");
            sb.append("Threshold value (in millis): ").append(queryExecutionTimeThreshold);
            sb.append("\n");
            sb.append("Slowest query time (in millis): ").append(statistics.getQueryExecutionMaxTime());
            sb.append("\n");
            sb.append("Slowest query string: ").append(statistics.getQueryExecutionMaxTimeQueryString());
            sb.append("\n");
            sb.append("Query destination: ").append(pjp.getSignature());
            sb.append("\n =========================================================================");
            LOGGER.info(sb.toString());
        }
        statistics.clear();
        return result;
    }

    public void afterPropertiesSet() throws Exception {
        LOGGER.warn("[HIBERNATE-STATISTICS] Logger is initialized! All relevant data can be found at logs/uery-statistics.log file!");
    }
}