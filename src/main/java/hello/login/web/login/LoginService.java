package hello.login.web.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service//스프링빈으로 등록
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    public boolean login(LoginForm form){
        return memberRepository.isMember(form.getLoginId(), form.getPassword());

    }
}
