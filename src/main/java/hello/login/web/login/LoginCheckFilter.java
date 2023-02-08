package hello.login.web.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginCheckFilter implements Filter {
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
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String url = httpRequest.getRequestURI();
        try{
            if(MustLogin(url)){//로그인 해야하는url의 경우
                HttpSession session= httpRequest.getSession(false);//새로생성x, 요청으로 들어온 세션값 확인
                if(session==null && session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
                    //미인증사용자는 리다이렉트시키기
                    //자기가 보려고 한 화면을 보게 하려고 아래와 같은 코드를 작성했음
                    httpResponse.sendRedirect("/login?redirectURL=" + url);
                }
                else chain.doFilter(request, response);


            }
        }catch (Exception e) {
            throw e; //예외 로깅 가능 하지만, 톰캣까지 예외를 보내주어야 함
        } finally {
            log.info("인증 체크 필터 종료 {}", url);
        }

    }
    public boolean MustLogin(String url){
        //whiteList에 url이 해당되는지 여부를 return해준다
        return !PatternMatchUtils.simpleMatch(whiteList, url);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        log.info("로그인 체크 필터 destroy");
    }
}
