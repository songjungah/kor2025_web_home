package korweb.model.dto;

import korweb.model.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private int mno;        // 회원번호
    private String mid;     // 회원아이디
    private String mpwd;    // 회원비밀번호
    private String mname;   // 회원닉네임
    private String memail;  // 회원이메일

    // dto --> entity 변환필수
    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .mno(this.mno)
                .mid(this.mid)
                .mpwd(this.mpwd)
                .mname(this.mname)
                .memail(this.memail)
                .build();
    }
    
}
