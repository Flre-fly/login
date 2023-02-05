package hello.login.web;

import hello.login.web.login.Member;
import hello.login.web.login.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;

    @GetMapping
    //@CookieValue 를 사용하면 편리하게 쿠키를 조회할 수 있다.
    public String login(@CookieValue(name="memberId", required = false)Long memberId, Model model){
        if(memberId==null) return "/home";
        Member member = memberRepository.getById(memberId);
        model.addAttribute("member", member);
        return "/loginHome";
    }
}