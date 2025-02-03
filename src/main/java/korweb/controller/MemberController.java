package korweb.controller;

import korweb.model.dto.MemberDto;
import korweb.model.service.MemberServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    @Autowired private MemberServcie memberServcie;

    // 1. 회원가입 HTTP 매핑
//    @PostMapping("/member/signup.do")
//    public boolean signup(@RequestBody MemberDto memberDto) {
//        return memberServcie.signup(memberDto);
//    }

    // 1. 첨부파일이 포함된 회원가입 HTTP 매핑, 첨부파일은 JSON 타입이 아닌 multipart/form-data 타입으로 @RequestBody 사용하지 않는다.
    @PostMapping("/member/signup.do")
    public boolean signup(MemberDto memberDto) {    // @RequestBody 를 사용하지 않는다. Application/json 아니라서
        return memberServcie.signup(memberDto);
    }

    // 2. 로그인 HTTP 매핑
    @PostMapping("/member/login.do")
    public boolean login(@RequestBody MemberDto memberDto) {
        return memberServcie.login(memberDto);
    }

    // 3. 현재 로그인된 회원 아이디 http 매핑
    @GetMapping("/member/login/id.do")
    public String loginId() {
        return memberServcie.getSession();
    }

    // 4. 현재 로그인된 회원 로그아웃
    @GetMapping("/member/logout.do")
    public boolean logout() {
        return memberServcie.deleteSession();
    }
    
    // 5. 내 정보 조회
    @GetMapping("/member/myinfo.do")
    public MemberDto myInfo() {
        return memberServcie.getMyInfo();
    }
    
    // 6. 회원탈퇴
    @DeleteMapping("/member/delete.do")
    public boolean myDelete() {
        return memberServcie.myDelete();
    }
    
    // 7. 회원정보 수정
    @PutMapping("/member/update.do")
    public boolean myUpdate(@RequestBody MemberDto memberDto) {
        return memberServcie.myUpdate(memberDto);
    }

}
