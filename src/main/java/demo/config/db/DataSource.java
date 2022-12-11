package demo.config.db;

import java.lang.annotation.*;

/**
 * 数据源类型注解，用于实现接口方法上面
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    DatabaseType type();
}
