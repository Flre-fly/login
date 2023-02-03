package hello.login.web.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service//스프링빈으로 등록
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;
    public boolean login(LoginForm form){
        return loginRepository.isMember(form.getLoginId(), form.getLoginPw());

    }
}
