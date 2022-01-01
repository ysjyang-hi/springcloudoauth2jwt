package cn.nadow.oauthserver.config;



import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Druid数据源配置
 */
@Configuration
public class DataSourceConfig {
    private static String dbUrl;

    private static String username;

    private static String password;

    private static String driverClassName;

    private static int initialSize;

    private static int minIdle;

    private static int maxActive;

    private static int maxWait;
    //数据源
    @Bean
    @Primary
    public BasicDataSource dataSource(){
        org.apache.commons.dbcp2.BasicDataSource dataSource=new org.apache.commons.dbcp2.BasicDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(dbUrl);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);

        dataSource.setDriverClassName(driverClassName);
         return dataSource;

    }
    @Value("${spring.datasource.url}")
    public void setDbUrl(String dbUrl) {
        DataSourceConfig.dbUrl = dbUrl;
    }

    @Value("${spring.datasource.username}")
    public void setUsername(String username) {
        DataSourceConfig.username = username;
    }

    @Value("${spring.datasource.password}")
    public void setPassword(String password) {
        DataSourceConfig.password = password;
    }

    @Value("${spring.datasource.driver-class-name}")
    public void setDriverClassName(String driverClassName) {
        DataSourceConfig.driverClassName = driverClassName;
    }

    @Value(value = "${spring.datasource.druid.initialSize}")
    public void setInitialSize(int initialSize) {
        DataSourceConfig.initialSize = initialSize;
    }

    @Value(value = "${spring.datasource.druid.minIdle}")
    public void setMinIdle(int minIdle) {
        DataSourceConfig.minIdle = minIdle;
    }

    @Value(value = "${spring.datasource.druid.maxActive}")
    public void setMaxActive(int maxActive) {
        DataSourceConfig.maxActive = maxActive;
    }

    @Value(value = "${spring.datasource.druid.maxWait}")
    public void setMaxWait(int maxWait) {
        DataSourceConfig.maxWait = maxWait;
    }

}