package hello.login.web.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    //로그인화면으로 들어갔을때(get요청시) 로그인화면을 보여주는 controller도 있어야함
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form){
        //login.loginForm페이지에서는 form에대한 참조를하고있음
        //그래서 일단 빈객체라도 넘겨주어야함. 그래서 사용은 안하지만 parameter로 받고있음

        log.info(form.getLoginId());//null
        log.info(form.getPassword());
        return "login/loginForm";
    }

    //prefetch로 로그아웃될수있기대문에 post요청으로 로그아웃을 진행
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        //세션삭제
        if(session!=null){
            session.invalidate();
        }

        return "redirect:/";
    }


    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member member = loginService.login(loginForm);
        if(member!=null) {
            //응답에 쿠키를 저장해준다
            HttpSession session= request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, member);
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
