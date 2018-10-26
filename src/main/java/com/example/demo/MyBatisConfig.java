package com.example.demo;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * springboot集成mybatis的基本入口 1）创建数据源(如果采用的是默认的    tomcat-jdbc数据源，则不需要)
 * 2）创建SqlSessionFactory 3）配置事务管理器，除非需要使用事务，否则不    用配置
 * @Author xuelongjiang
 */
@Configuration // 该注解类似于spring配置文件
public class MyBatisConfig {

    @Autowired
    private Environment environment;


    /**
     * 创建数据源(数据源的名称：方法名可以取为XXXDataSource(),XXX为数据    库名称,该名称也就是数据源的名称)
    */
    @Bean
    public DataSource mainDataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", environment.getProperty("main-datasource.driverClassName"));
        props.put("url", environment.getProperty("main-datasource.url"));
        props.put("username", environment.getProperty("main-datasource.username"));
        props.put("password", environment.getProperty("main-datasource.password"));
        return DruidDataSourceFactory.createDataSource(props);
}

    @Bean
    public DataSource yuntuDataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", environment.getProperty("yuntu-datasource.driverClassName"));
        props.put("url", environment.getProperty("yuntu-datasource.url"));
        props.put("username", environment.getProperty("yuntu-datasource.username"));
        props.put("password", environment.getProperty("yuntu-datasource.password"));
        return DruidDataSourceFactory.createDataSource(props);
}


    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择        哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个    注入（例如有多个DataSource类型的实例）
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("mainDataSource") DataSource mainDataSource,
                                    @Qualifier("yuntuDataSource") DataSource yuntuDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseType.main, mainDataSource);
        targetDataSources.put(DatabaseType.yuntu, yuntuDataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(mainDataSource);// 默认的datasource设置为myTestDbDataSource

        return dataSource;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
     public SqlSessionFactory sqlSessionFactory(@Qualifier("mainDataSource") DataSource mainDataSource,
                                                @Qualifier("yuntuDataSource") DataSource yuntuDataSource) throws Exception{
                 SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
                fb.setDataSource(this.dataSource(mainDataSource, yuntuDataSource));
        fb.setTypeAliasesPackage("com.example.demo.domain");//可以不设置
                 fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
                 return fb.getObject();
             }

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

}
