package demo.service;

/**
 * @author admin
 * 2022/8/12 21:16
 **/
public interface AsyncSerivce {

    void getReadTest();

    void getWriteTest();

    /**
     * 执行异步任务
     * 可以根据需求， 自己加参数
     */
    void executeAsync();

    /**
     * 执行异步任务
     * 可以根据需求， 自己加参数
     */
    void executeShowAsync();
}
