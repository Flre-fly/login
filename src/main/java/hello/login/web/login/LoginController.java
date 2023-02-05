package hello.login.web.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    
    //로그인화면으로 들어갔을때(get요청시) 로그인화면을 보여주는 controller도 있어야함
    @GetMapping
    public String login(@ModelAttribute("loginForm") LoginForm form){
        //login.loginForm페이지에서는 form에대한 참조를하고있음
        //그래서 일단 빈객체라도 넘겨주어야함. 그래서 사용은 안하지만 parameter로 받고있음

        log.info(form.getLoginId());//null
        log.info(form.getPassword());
        return "login/loginForm";
    }

    @PostMapping
    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member member = loginService.login(loginForm);
        if(member!=null) {
            //로그인이 성공했을 경우 쿠키를 담아 보낸다
            Cookie idCookie = new Cookie("memberId", String.valueOf(member.getId()));
            response.addCookie(idCookie);
            return "redirect:/";
        }
        //만약 로그인에 실패했을경우엔 bindingResult에 error넣어줘야함
        else{
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
            //bindingResult.reject: 글로벌오류
        }
    }
}
