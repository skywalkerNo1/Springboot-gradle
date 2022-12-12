package demo.config.db;

/**
 * 作用：保存一个安全线程的databaseType容器
 * 建一个DatabaseType容器，并提供了向其中设置和获取DatabaseType的方法
 */
public class DatabaseContextHolder {

    // 线程存储类 指定线程内存储数据，数据存储以后，只有指定线程可以得到存储数据
    // 通过ThreadLocal将数据源设置到每个线程上下文中
    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

    public static void set(DatabaseType dbType) {
        contextHolder.set(dbType);
    }

    public static DatabaseType get() {
        return contextHolder.get();
    }
	
	public static void clearDataSource() {
		contextHolder.remove();
	}
 }
