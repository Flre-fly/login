package hello.login.web.login;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("log 필터 초기화");
    }

    @Override
    //ServletRequest request 는 HTTP 요청이 아닌 경우까지 고려해서 만든 인터페이스이다. HTTP를
    //사용하면 HttpServletRequest httpRequest = (HttpServletRequest) request; 와 같이
    //다운 케스팅 하면 된다.
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String url = httpRequest.getRequestURI();
        //HTTP 요청을 구분하기 위해 요청당 임의의 uuid 를 생성해둔다.
        String uuid = UUID.randomUUID().toString();
        log.info("request {} {}", uuid, url);

        //다음 필터가 있으면 필터를 호출하고, 필터가 없으면 서블릿을 호출한다.
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        log.info("log 필터 destroy");
    }
}
