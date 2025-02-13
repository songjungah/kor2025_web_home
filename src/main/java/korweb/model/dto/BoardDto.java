package korweb.model.dto;

import korweb.model.entity.BoardEntity;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    // 코드 작성하기 전
    private int bno;            // 번호
    private String btitle;      // 제목
    private String bcontent;    // 내용
    private int bview;          // 작성자
    private int mno;            // 작성자의 회원번호
    private int cno;            // 카테고리 번호
    private String cdate;       // 작성일

    // 화면에는 작성자의 회원번호가 아닌 아이디를 출력해야하므로
    private String mid;         // 작성자의 회원아이디
    private String cname;       // 카테고리명

    // * 댓글 리스트
    private List<Map<String, String>> replylist;


    // + Dto --> entity 번환 메소드
    // dto를 entity 객체로 변환해서 데이터베이스에 저장해야 하므로 변환이 필요하다
    public BoardEntity toEntity() {
        return BoardEntity.builder()
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .bview(this.bview)
                .build();
    }


}