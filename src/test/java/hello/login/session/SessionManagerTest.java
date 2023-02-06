package hello.login.session;

import hello.login.web.login.Member;
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
        Member member = new Member();
        sm.createSession(response,member);
        MockHttpServletRequest request = new MockHttpServletRequest();

        //쿠키설정
        request.setCookies(response.getCookies());

        Member member1 = (Member)sm.getSession(request);
        Assertions.assertThat(member1).isEqualTo(member);

        sm.expire(request);
        Member member2 = (Member)sm.getSession(request);
        Assertions.assertThat(member2).isNull();
    }
}
