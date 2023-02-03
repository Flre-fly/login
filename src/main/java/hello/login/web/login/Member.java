package hello.login.web.login;

import lombok.Data;

@Data
public class Member {
    private Long id;
    private String loginId;
    private String loginPw;

}
