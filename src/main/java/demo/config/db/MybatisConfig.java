package demo.config.db;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan("demo.dao")
public class MybatisConfig {

    @Resource(name = "dynamicDataSource")
    private DataSource dynamicDataSource;

    @Bean
    public SqlSessionFactory sessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        //指定model层的位置
        bean.setTypeAliasesPackage("demo.model");
        //指定Mapper层位置
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*Mapper.xml"));
        //开启mybatis字段命名驼峰转换
        if (bean.getObject() != null) {
            bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        }
        return bean.getObject();
    }

    /**
     * 配置事务管理器
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

}
