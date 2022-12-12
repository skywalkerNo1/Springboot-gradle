package demo.config.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源，需要继承AbstractRoutingDataSource实现
 * 使用DatabaseContextHolder获取当前线程的DatabaseType， 即获取当前线程使用哪个数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 作用：保存一个安全线程的databaseType容器
     * 建一个DatabaseType容器，并提供了向其中设置和获取DatabaseType的方法
     * 线程存储类 指定线程内存储数据，数据存储以后，只有指定线程可以得到存储数据
     * 通过ThreadLocal将数据源设置到每个线程上下文中
     */
    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

    @Override
    protected Object determineCurrentLookupKey() {
        return contextHolder.get();
    }

    public static void set(DatabaseType dbType) {
        contextHolder.set(dbType);
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }
}
