package mtn.momo.contract.repayment.configs;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect
public class LoggingAspect {
    private Environment environment;

    public LoggingAspect(Environment environment) {
        this.environment = environment;
    }

    @Pointcut("within(mtn.momo.contract.repayment.controller..*)")
    public void applicationControllerPointcut() {}

//    @Pointcut("within(mtn.momo.contract.repayment.filter..*)")
//    public void applicationFilterPointcut() {}

    @Pointcut("within(mtn.momo.contract.repayment.service..*)")
    public void applicationServicePointcut() {}

    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    @Around("applicationControllerPointcut() || applicationServicePointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = logger(joinPoint);
        if (logger.isDebugEnabled()) {
            logger.debug("Entering {}.{} and arguments[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting: {} with results = {}", joinPoint.getSignature().getName(), result);
        }

        logger.info("Execution time of {} = {}ms", joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());

        return result;
    }
}
