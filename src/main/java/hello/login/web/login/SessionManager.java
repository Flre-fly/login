package hello.login.web.login;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component//스프링빈으로 등록해서 외부에서 사용할 수 있도록
public class SessionManager {
    private static final String SESSION_NAME = "sessionId";
    //id로 저장해버리면 meember의 id인지 item의 id인지를 몰라서 안될듯..
    private static Map<String, Object> session = new ConcurrentHashMap<>();

    public void createSession(HttpServletResponse response, Member member){
        String key = UUID.randomUUID().toString();
        session.put(key, member);
        Cookie cookie = new Cookie(SESSION_NAME,key);
        response.addCookie(cookie);
    }
    public Object getSession(HttpServletRequest request){
        Cookie cookie = getCookie(request);
        if(cookie!=null) return session.get(cookie.getValue());
        return null;
    }
    public void expire(HttpServletRequest request){
        Cookie cookie = getCookie(request);
        if(cookie!=null){
            //cookie.setMaxAge(0);이걸 없애는것보다 세션저장소에있는걸없애야함
            session.remove(cookie.getValue());
        }
    }
    //클래스 내부에서만쓰는 메서드니까 private선언
    private Cookie getCookie(HttpServletRequest request){
        return Arrays
                .stream(request.getCookies())
                .filter(c -> c.getName().equals(SESSION_NAME))
                .findAny()
                .orElse(null);
    }
}
