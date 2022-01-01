package cn.nadow.resource;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean reqResFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        AeqResFilter reqResFilter = new AeqResFilter();
        filterRegistrationBean.setFilter(reqResFilter);
     filterRegistrationBean.addUrlPatterns("/*");//配置过滤规则
 return filterRegistrationBean;

 }
}
