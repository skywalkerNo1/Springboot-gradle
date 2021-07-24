package demo.config.db;

public class DBContextHolder {

    // 线程存储类 指定线程内存储数据，数据存储以后，只有指定线程可以得到存储数据
    // 通过ThreadLocal将数据源设置到每个线程上下文中
    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    public static void set(DBTypeEnum dbType) {
        System.out.println("enum -------"+dbType);
        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        return contextHolder.get();
    }

    static void master() {
        set(DBTypeEnum.MASTER);
        System.out.println("切换到master");
    }

//    static void slave() {
//        set(DBTypeEnum.SLAVE);
//        System.out.println("切换到slave");
//    }
}
