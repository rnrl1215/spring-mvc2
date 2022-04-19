package hello.login.web;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
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


    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LoginCheckFilter());
        filterFilterRegistrationBean.setOrder(2);

        // 이거에 대한 성능 저하는 없다
        // 바다의 모래알 정도이다.
        filterFilterRegistrationBean.addUrlPatterns("/*");
        return filterFilterRegistrationBean;
    }
}

