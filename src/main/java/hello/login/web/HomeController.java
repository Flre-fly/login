package hello.login.web;

import hello.login.web.login.Member;
import hello.login.web.login.MemberRepository;
import hello.login.web.login.SessionConst;
import hello.login.web.login.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping
    //@CookieValue 를 사용하면 편리하게 쿠키를 조회할 수 있다.
    public String login(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "home";
        }
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("member", member);
        return "loginHome";
    }
}