package hello.login.web;

import hello.login.web.filter.LogFilter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();

        //필터 등록
        filterFilterRegistrationBean.setFilter(new LogFilter());

        // 필터 우선순위 설정
        filterFilterRegistrationBean.setOrder(1);

        // 필터를 적용할 요청 URL
        filterFilterRegistrationBean.addUrlPatterns("/*");

        return filterFilterRegistrationBean;
    }
}
