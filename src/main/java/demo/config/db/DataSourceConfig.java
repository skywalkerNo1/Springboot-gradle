package demo.config.db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean(value = "defaultDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(value = "dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("defaultDataSource")DataSource masterDataSource) {
        Map<Object, Object> sourceMap = new HashMap<>();
        sourceMap.put(DBTypeEnum.MASTER, masterDataSource);
        // 实例化一个动态路由数据源
        DynamicRoutingDataSource myRoutingDataSource = new DynamicRoutingDataSource();
        // 设置 master datasource 为默认源
        myRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
        // 设置 master 和 slave datasource 为目标源
        myRoutingDataSource.setTargetDataSources(sourceMap);
        return myRoutingDataSource;
    }

}
