package demo.config.ThreadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author admin
 * 2022/8/12 21:43
 * 创建一个子类， 用于显示当前线程池有多少线程在运行，多少在队列中等待执行
 * 重写了 ThreadPoolTaskExecutor方法中的execute, submit等方法，在里面调用了showThreadPoolInfo方法，
 * 这样每次有任务提交到线程池中，都会将当前线程中的基本情况打印到日志中
 **/
public class VisiableThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    private static final Logger logger = LoggerFactory.getLogger(VisiableThreadPoolTaskExecutor.class);

    private void showThreadPoolInfo(String prefix) {
        ThreadPoolExecutor executor = getThreadPoolExecutor();

        logger.info("{}, {} 线程总数taskCount: [{}], 已完成数completedTaskCount: [{}], 当前正在执行数activeCount: [{}], 剩余等待数queueSize: [{}]",
                this.getThreadNamePrefix(),
                prefix,
                executor.getTaskCount(),
                executor.getCompletedTaskCount(),
                executor.getActiveCount(),
                executor.getQueue().size());
    }

    @Override
    public void execute(Runnable task) {
        showThreadPoolInfo("1. do execute");
        super.execute(task);
    }

    @Override
    public void execute(Runnable task, long startTime) {
        showThreadPoolInfo("2. do execute");
        super.execute(task, startTime);
    }

    @Override
    public Future<?> submit(Runnable task) {
        showThreadPoolInfo("1. do submit");
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        showThreadPoolInfo("2. do submit");
        return super.submit(task);
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        showThreadPoolInfo("1. do submitListenable");
        return super.submitListenable(task);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        showThreadPoolInfo("2. do submitListenable");
        return super.submitListenable(task);
    }
}
