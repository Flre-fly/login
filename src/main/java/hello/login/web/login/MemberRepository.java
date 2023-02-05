package hello.login.web.login;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemberRepository {
    private Map<Long, Member> store = new HashMap<>();
    Long id = 0l;
    public Member save(Member member){
        member.setId(id);
        store.put(id++, member);
        return member;
    }
    public Member getById(Long id){
        return store.get(id);
    }
    public Member isMember(String loginId, String loginPw){
        for (Member member:store.values()) {
            if(member.getLoginId().equals(loginId) && member.getLoginPw().equals(loginPw)) return member;
        }
        return null;
    }
}
