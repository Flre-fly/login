package hello.login.session;

import hello.login.web.login.SessionManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@SpringBootTest
public class SessionManagerTest {
    @Autowired
    private SessionManager sm;
    @Test
    public void sessionTest(){
        //세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        Long id = 1l;
        sm.createSession(response,id);
        MockHttpServletRequest request = new MockHttpServletRequest();

        //쿠키설정
        request.setCookies(response.getCookies());

        String id1 = sm.getSession(request);
        Assertions.assertThat(id1).isEqualTo(id.toString());

        sm.expire(request);
        String id2 = sm.getSession(request);
        Assertions.assertThat(id2).isNull();
    }
}
