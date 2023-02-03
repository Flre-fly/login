package hello.login.web.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/items";
        }
        if(loginService.login(loginForm)) return "/memberpage";
        else return "/home";
    }
}
