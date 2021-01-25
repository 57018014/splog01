package com.yy.comfig;

import com.yy.filter.ServiceExceptionFilter;
import com.yy.filter.UserTokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author yaoyuan
 * @createTime 2019/12/04 8:19 PM
 * 注意 filter可能各个模块都不想同，这里只指定了最基础的检验token的filter order =1 （最高优先级）
 */
@Configuration
public class FilterConfig {

    /**
     * 用于对ServiceException的统一处理
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean serviceExceptionFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new ServiceExceptionFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }

    /**
     * 用于用户token校验
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean userTokenFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new UserTokenFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(2);
        return bean;
    }


    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }


//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig());
//        return new CorsFilter(source);
//    }
//
//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        // 1允许任何域名使用
//        corsConfiguration.addAllowedOrigin("*");
//        // 2允许任何头
//        corsConfiguration.addAllowedHeader("*");
//        // 3允许任何方法（post、get等）
//        corsConfiguration.addAllowedMethod("*");
//        return corsConfiguration;
//    }

}
