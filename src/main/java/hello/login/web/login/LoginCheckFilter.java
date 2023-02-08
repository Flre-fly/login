package hello.login.web.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginCheckFilter implements Filter {
    private final LoginService loginService;
    //항상 접근을 허용하는 url
    static final String []whiteList = {"/", "/members/add", "/login", "/logout","/css/*"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("로그인 체크 필터 초기화 ");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String url = httpRequest.getRequestURI();
        if(MustLogin(url)){
            LoginForm form = (LoginForm)httpRequest.getAttribute("loginForm");
            Member member = loginService.login(form);
            if(member!=null) {
                HttpSession session= httpRequest.getSession();
                session.setAttribute(SessionConst.LOGIN_MEMBER, member);
                chain.doFilter(request, response);
            }
            else{
                return;
            }
        }
        //안적어도되지않나?
        return;
    }
    public boolean MustLogin(String url){
        return !PatternMatchUtils.simpleMatch(whiteList, url);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        log.info("로그인 체크 필터 destroy");
    }
}
