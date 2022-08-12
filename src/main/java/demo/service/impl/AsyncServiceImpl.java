package demo.service.impl;

import demo.service.AsyncSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author admin
 * 2022/8/12 21:18
 **/
@Service
public class AsyncServiceImpl implements AsyncSerivce {

    private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);

    // asyncServiceExecutor 是ThreadPoolExecutorConfig.java中的方法名，
    // 表明executedAsync方法进入的线程池是由 asyncServiceExecutor 方法创建的
    @Async("asyncServiceExecutor")
    @Override
    public void executeAsync()  {
        logger.info("start executeAsync");
        try {
            System.out.println("异步线程执行逻辑");
            Thread.sleep(10 * 1000);
            System.out.println("可以执行耗时较长的方法或逻辑");
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("end executedAsync");
    }

    @Async("showAsyncServiceExecutor")
    @Override
    public void executeShowAsync()  {
        logger.info("start showAsyncServiceExecutor");
        try {
            System.out.println("异步线程执行逻辑 showAsyncServiceExecutor");
            Thread.sleep(10 * 1000);
            System.out.println("可以执行耗时较长的方法或逻辑 showAsyncServiceExecutor");
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("end showAsyncServiceExecutor");
    }
}
