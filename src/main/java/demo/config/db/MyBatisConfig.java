package demo.config.db;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer {

    @Autowired
    private Environment env;
    @Autowired
    DataSource dynamicDataSource;

    /**
     * 根据数据源创建 SqlSessionFactory工厂
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(DynamicDataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        // 指定数据源
        bean.setDataSource(dynamicDataSource);
        bean.setTypeAliasesPackage("demo.model");
        // 添加插件
//        bean.setPlugins(new Interceptor[]{});
        // 添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入时，优先选择哪一个，而不是让@Autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入 （例如有多个dataSource类型的实例）
     */
    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource(@Qualifier("writeDBDataSource") @Lazy DataSource writeDataSource,
                                               @Qualifier("readDBDataSource") @Lazy DataSource readDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseType.writeDB, writeDataSource);
        targetDataSources.put(DatabaseType.readDB, readDataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        // 该方法是AbstractRoutingDataSource的方法
        dataSource.setTargetDataSources(targetDataSources);
        // 设计默认的dataSource，一般将写库设置为默认数据库
        dataSource.setDefaultTargetDataSource(writeDataSource);
        return dataSource;
    }

    /**
     * SqlSessionTemplate是线程安全的
     * SqlSessionTemplate 时Mybatis-Spring的核心， 是Mybatis接入Spring而提供的bean
     * 用于负责管理Mybatis的SqlSession
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory factory) {
        return new SqlSessionTemplate(factory);
    }

    /**
     * 配置事务管理
     * @return
     */
    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

    /**
     * 写库
     */
    @Bean
    public DataSource writeDBDataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("dataSource.write.driverClassName"));
        props.put("url", env.getProperty("dataSource.write.url"));
        props.put("username", env.getProperty("dataSource.write.username"));
        props.put("password", env.getProperty("dataSource.write.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    /**
     * 读库
     */
    @Bean
    public DataSource readDBDataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("dataSource.read.driverClassName"));
        props.put("url", env.getProperty("dataSource.read.url"));
        props.put("username", env.getProperty("dataSource.read.username"));
        props.put("password", env.getProperty("dataSource.read.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

}
