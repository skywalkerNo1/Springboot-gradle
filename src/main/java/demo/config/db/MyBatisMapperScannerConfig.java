package demo.config.db;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("demo.dao");
        Properties props = new Properties();
        props.setProperty("mappers", "demo.config.db.BaseMapper");
        props.setProperty("notEmpty", "false");
        props.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(props);
        return mapperScannerConfigurer;
    }
}
