package com.jg.project.core.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @ClassName DataSourceConfiguration
 * @Author huangGuoGang@qq.com
 * @Date 2019-06-27 9:15
 * @Version 1.0
 **/
@SpringBootConfiguration
@Component
public class DataSourceConfiguration {

    @Value("${jg.jdbc.name}")
    private String userName;
    @Value("${jg.jdbc.password}")
    private String password;
    @Value("${jg.jdbc.url}")
    private String url;
    @Value("${jg.jdbc.driverClassName}")
    private String driverClassName;

    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        dataSource.setInitialSize(1);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(20);
        dataSource.setMaxWait(60000);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setValidationQuery("SELECT　'x'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        return dataSource;
    }
}
