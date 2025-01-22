package korweb.controller;

import korweb.model.dto.MemberDto;
import korweb.model.service.MemberServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired private MemberServcie memberServcie;

    // 1. 회원가입 HTTP 매핑
    @PostMapping("/member/signup.do")
    public boolean signup(@RequestBody MemberDto memberDto) {
        return memberServcie.signup(memberDto);
    }

    // 2. 로그인 HTTP 매핑
    @PostMapping("/member/login.do")
    public boolean login(@RequestBody MemberDto memberDto) {
        return memberServcie.login(memberDto);
    }

}
