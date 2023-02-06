package hello.login.session;

import hello.login.web.login.SessionManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;

@SpringBootTest
public class SessionManagerTest {
    @Autowired
    private SessionManager sm;
    @Test
    public void sessionTest(){
        //세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        sm.createSession(response,1l);
    }
}
