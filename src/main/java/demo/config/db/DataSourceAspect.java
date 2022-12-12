package demo.config.db;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * aop前置通知
 */
@Aspect
@Component
public class DataSourceAspect implements Ordered {

    /**
     * @annotation(dataSource) 拦截前后通知，当线程执行到方法上时，先执行该方法
     *                         判断是否指定了数据源，若没有指定，则使用默认的数据源
     * @param proceedingJoinPoint
     * @param dataSource 必须和@annotation(dataSource)括号中的的命名一样
     *                   否则会出现 0 formal unbound in pointcut 错误
     * @return
     * @throws Throwable
     */
    @Around("@annotation(dataSource)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, DataSource dataSource) throws Throwable {
        try {
            DynamicDataSource.set(dataSource.type());
            return proceedingJoinPoint.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
