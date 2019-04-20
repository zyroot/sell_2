package com.eim.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.aspectj.weaver.IntMap;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zy on 2018/12/7.
 */
@Configuration
public class DruidConfig {

    /**
     * 注入druid
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {

        //自己创建冲突2.0

        //自己创建冲突4.0



        //自己制造冲突3.0
        return new DruidDataSource();
    }

    //配置servlet
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,String> initMap = new HashMap<>();
        initMap.put("loginUsername","admin");
        initMap.put("loginPassword","123456");
        servletRegistrationBean.setInitParameters(initMap);
        return servletRegistrationBean;
    }

    //配置filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        Map<String,String> initMap = new HashMap<>();
        initMap.put("exclusions","*.js,*.css,/druid/*");
        filterRegistrationBean.setInitParameters(initMap);
        return filterRegistrationBean;
    }


}
