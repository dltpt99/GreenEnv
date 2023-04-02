package nt.greenenv.greenenv.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nt.greenenv.greenenv.domain.member.LoginForm;
import nt.greenenv.greenenv.domain.member.Member;
import nt.greenenv.greenenv.domain.member.MemberNewForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    MemberService service;

    @Autowired
    public MemberServiceTest(MemberService service) {
        this.service = service;
    }

    @BeforeEach
    public void registerTestAccount() {
        MemberNewForm form = new MemberNewForm();
        form.setUser_id("test2");
        form.setNick("테스트2");
        form.setPw("1234");

        Member member = service.signup(form);
    }

    @Test
    void 회원가입() throws JsonProcessingException {
        MemberNewForm form = new MemberNewForm();
        form.setUser_id("test1");
        form.setNick("테스트1");
        form.setPw("1234");

        Member member = service.signup(form);

        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String result = mapper.writeValueAsString(member);
        System.out.println(result);

    }

    @Test
    void 로그인() {
        LoginForm form = new LoginForm();
        form.setUser_id("test2");
        form.setPw("1234");
    }
}