package demo.config.ThreadPool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * 配置全局线程池
 * @author admin
 * 2021/7/1521:06
 **/
@EnableAsync
@Configuration
public class ThreadPoolExecutorConfig {

//    @Bean("threadPool")
//    public ExecutorService threadPool() {
//        //配置核心线程数
//        int corePoolSize = 5;
//        //配置最大线程数
//        int maxPoolSize = 5;
//        //空闲线程最大等待时长
//        int keepAliveTime = 0;
//        ThreadFactory threadFactory = new CustomizableThreadFactory("threadPool-%d");
//        return new ThreadPoolExecutor(corePoolSize, maxPoolSize,
//                keepAliveTime, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<>(), threadFactory, new ThreadPoolExecutor.AbortPolicy());
//    }

    // 普通线程池
    @Bean(name = "asyncServiceExecutor")
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 配置核心线程数
        executor.setCorePoolSize(10);
        // 配置最大线程数
        executor.setMaxPoolSize(20);
        // 配置队列大小
        executor.setQueueCapacity(50);
        // 配置线程池中的线程名称前缀
        executor.setThreadNamePrefix("async-service-");
        //rejection-policy: 当pool已经达到max size的时候， 如何处理新任务
        // CALLER_RUNS： 不在新线程中执行任务，而是由调用这所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    // 打印 当前线程池中的任务总数，已完成数，活跃线程数
    @Bean("showAsyncServiceExecutor")
    public Executor showAsyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        // 配置核心线程数
        executor.setCorePoolSize(10);
        // 配置最大线程数
        executor.setMaxPoolSize(20);
        // 配置队列大小
        executor.setQueueCapacity(50);
        // 配置线程池中的线程名称前缀
        executor.setThreadNamePrefix("async-service-");
        //rejection-policy: 当pool已经达到max size的时候， 如何处理新任务
        // CALLER_RUNS： 不在新线程中执行任务，而是由调用这所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
