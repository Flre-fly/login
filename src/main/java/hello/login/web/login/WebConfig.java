package hello.login.web.login;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

    //@ServletComponentScan @WebFilter(filterName = "logFilter", urlPatterns = "/*") 로
    //필터 등록이 가능하지만 필터 순서 조절이 안된다. 따라서 FilterRegistrationBean 을 사용하자.
    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<Filter>();
        filterFilterRegistrationBean.setFilter(new LogFilter());//등록할 필터
        filterFilterRegistrationBean.setOrder(1);//필터체인중 제일먼저동작한다는 의미
        filterFilterRegistrationBean.addUrlPatterns("/*");//필터를 적용할 url 패턴
        return filterFilterRegistrationBean;
    }
}
