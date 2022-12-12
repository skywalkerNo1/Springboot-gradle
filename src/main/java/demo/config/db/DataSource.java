package demo.config.db;

import java.lang.annotation.*;

/**
 * 数据源类型注解，用在实现接口的方法上面
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    DatabaseType type();
}
