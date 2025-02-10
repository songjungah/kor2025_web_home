
package korweb.service;

import korweb.model.dto.BoardDto;
import korweb.model.dto.MemberDto;
import korweb.model.entity.BoardEntity;
import korweb.model.entity.CategoryEntity;
import korweb.model.entity.MemberEntity;
import korweb.model.entity.ReplyEntity;
import korweb.model.repository.BoardRepository;
import korweb.model.repository.CategoryRepository;
import korweb.model.repository.MemberRepository;
import korweb.model.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BoardService {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;   // member 엔티티를 조작하는 인터페이스
    @Autowired BoardRepository boardRepository;     // board 엔티티를 조작하는 인터페이스
    @Autowired CategoryRepository categoryRepository; // category 엔티티를 조작하는 인터페이스
    @Autowired ReplyRepository replyRepository;     // reply 엔티티를 조작하는 인터페이스
    
    // [1] 게시물 쓰기
    public boolean boardWrite(BoardDto boardDto) {

        // (1) 사용자로부터 전달받은 boardDto(btitle.bcontent.cno) 를 엔티티로 변환
        // 0. boardDto 를 entity 로 변환
        BoardEntity boardEntity = boardDto.toEntity();

        // 1. 게시물 작성자는 현재 로그인된 회원이므로 세션에서 현재 로그인된 회원번호 조회
        // - 현재 로그인된 세션 객체 조회
        MemberDto loginDto = memberService.getMyInfo();

        // - 만약에 로그인된 상태가 아니면 글쓰기 종료
        if (loginDto == null) return false;

        // - 로그인된 상태이면 회원번호 조회
        int loginMno = loginDto.getMno();

        // - 로그인된 회원 엔티티를 게시물 엔티티에 대입한다.
        MemberEntity loginEntity = memberRepository.findById(loginMno).get();
        boardEntity.setMemberEntity(loginEntity);

        // 2. 게시물 카테고리는 cno를 entity 조회해서 게시물 엔티티에 대입한다. .findById(pk번호) : 지정한 pk 번호의 엔티티 조회
        CategoryEntity categoryEntity = categoryRepository.findById(boardDto.getCno()).get();
        boardEntity.setCategoryEntity(categoryEntity);

        // (2) 엔티티 .save(저장할 엔티티)
        BoardEntity saveBoardEntity = boardRepository.save(boardEntity);

        // (3) 만약에 게시물 등록에 성공했다면, 만약에 현재 등록한 게시물이 성공이면 true 반환
        if(saveBoardEntity.getBno() > 0) {
            return true;
        } else {
            return false;   // 게시물쓰기 실패이면 false 를 반환
        }
    }

    // [2]] 게시물 전체 조회
    public List<BoardDto> boardFindAll(int cno) {

        // (1) 모든 게시물 엔티티를 조회
        List<BoardEntity> boardEntityList = boardRepository.findAll();

        // (2) 모든 게시물의 엔티티를 Dto로 변환
        // - DTO를 저장할 리스트 선언
        List<BoardDto> boardDtoList = new ArrayList<>();
        // - 반복문을 이용하여 모든 엔티티를 dto로 변환하기
        // [1] 리스트 변수명.forEach(반복변수명 -> {실행문;}) : boardEntityList.forEach(boardEntity -> {});
        // [다른 방법 1-2] for (int index = 0; index <= boardEntityList.size()-1; index++) {
        //                boardEntityList.get(index);
        //            }
        boardEntityList.forEach(entity -> {
            // [2] 엔티티 --> dto 반환
            // * 만약에 현재 조회중인 게시물의 카테고리가 선택한 카테고리와 같다면
            if (entity.getCategoryEntity().getCno() == cno) {
                BoardDto boardDto = entity.toDto();
                // [3] 변환된 dto 를 dtolist 리스트에 담는다.
                boardDtoList.add(boardDto);
            } else {

            }
        });
        // (3) 결과를 리턴한다.
        return boardDtoList;

    }

    // [3] 게시물 특정(개별) 조회
    public BoardDto boardFind( int bno ){
        // (1) 조회할 특정 게시물의 번호를 매개변수로 받는다.  int bno
        // (2) 조회할 특정 게시물의 번호의 엔티티를 조회한다. .findById() 메소드는 반환타입이 Optional 이다. 조회된 엔티티 여부 메소드 제공한다. .isPresent()
        Optional< BoardEntity > optional = boardRepository.findById( bno );
        // (3) 만약에 조회된 엔티티가 있으면 true / false
        if( optional.isPresent() ){
            // (4) optional 에서 엔티티 꺼내기. .get()
            BoardEntity boardEntity = optional.get();
            // (5) 엔티티를 dto 변환
            BoardDto boardDto = boardEntity.toDto();
            // * 현재 게시물의 댓글 리스트 조회
            // 1. 모든 게시물 댓글 조회한다.
            List<ReplyEntity> replyEntityList = replyRepository.findAll();
            // 2. 모든 댓글을 DTO/MAP 로 변환한 객체들을 저장할 리스트 선언 . --> ReplyDto 대신 MAP 컬렉션 이용한 방법
            // List 컬렉션 : [ 값, 값 , 값 ]   vs  Map 컬렉션 : { key : value , key : value , key : value }
            List<Map<String, String> > replylist = new ArrayList<>();
            // 3. 엔티티를 MAP 로 변환 하기 위한 엔티티 리스트를 반복문
            replyEntityList.forEach( (reply) ->{
                // * 만약에 현재 조회중인 게시물번호 와 댓글리스트내 반복중인 댓글의 게시물번호 와 같다면
                if( reply.getBoardEntity().getBno() == bno ){
                    // 4. map 객체 선언
                    Map<String , String > map = new HashMap<>();
                    // 5. map 객체에 하나씩 key:value (엔트리) 으로 저장한다.
                    map.put( "rno" , reply.getRno()+"" );       // 숫자타입 +"" =>문자타입 변환
                    map.put( "rcontent" , reply.getRcontent() );
                    map.put( "cdate" , reply.getCdate().toLocalDate().toString() ); // 날짜와시간 중에 날짜만 추출
                    map.put( "mid" , reply.getMemberEntity().getMid() ); // 댓글 작성자 아이디
                    map.put( "mimg" , reply.getMemberEntity().getMimg() ); // 댓글 작성자 프로필
                    // 6. map를 리스트에 담는다.
                    replylist.add( map );
                }
            });
            // 7. 반복문 종료된 후 boardDto에 댓글리스트 담기.
            boardDto.setReplylist( replylist );
            // (6) dto 결과 반환
            return boardDto;
        }
        return null; // 조회 결과 엔티티가 없으면 null 반환
    }

    // [4] 게시물 특정(개별) 수정
    public boolean boardUpdate(BoardDto boardDto) {
        return false;
    }

    // [5] 게시물 특정(개별) 삭제
    public boolean boardDelete(int bno) {
        return false;
    }

    // ========== 댓글 ========== /
    // [6] 댓글 쓰기
    public boolean replyWrite(Map<String, String> replyDto) {

        // 1. 현재 로그인된 회원번호 조회
        MemberDto memberDto = memberService.getMyInfo();
        // 2. 만약에 로그인된 정보가 없으면 함수 종료
        if (memberDto == null) {
            return false;
        }
        // [로그인 중이면]
        // 3. 회원 엔티티 조회
        MemberEntity memberEntity = memberRepository.findById(memberDto.getMno()).get();
        // 3-1. 현재 작성한 댓글이 위치한 조회중인 게시물 엔티티 조회
            // Integer.parseInt("문자열") 문자열 타입 --> 정수타입 반환 필수
        int bno = Integer.parseInt(replyDto.get("bno"));
        BoardEntity boardEntity = boardRepository.findById(bno).get();
        // 4. 입력받은 매개변수 map 을 entity 로 변환
        ReplyEntity replyEntity = new ReplyEntity();
        replyEntity.setRcontent(replyDto.get("rcontent"));  // 댓글 내용 등록
        replyEntity.setMemberEntity(memberEntity);          // 작성자 등록
        replyEntity.setBoardEntity(boardEntity);            // 댓글에 위치한 게시물 등록

        // 5. 생성한 entity 를 저장한다.
        ReplyEntity saveEntity =  replyRepository.save(replyEntity);
        if (saveEntity.getRno() > 0) {
            return true;    // 댓글 번호 생서오디었다면 등록 성공
        }
        return false;
    }

    // [7] 특정 게시물의 댓글 전체 조회
    public List<Map<String, String>> replyFindAll(int bno) {

        // 1. 모든 댓글 엔티티 조회
        List<ReplyEntity> replyEntityList = replyRepository.findAll();
        // 2. 모든 댓글 map을 저장할 list 선언
        List<Map<String, String>> replyList = new ArrayList<>();
        // 3. 모든 댓글 엔티티를 반복문으로 조회
        replyEntityList.forEach((reply)-> {
            if( reply.getBoardEntity().getBno() == bno ){
                // 4. map 객체 선언
                Map<String , String > map = new HashMap<>();
                // 5. map 객체에 하나씩 key:value (엔트리) 으로 저장한다.
                map.put( "rno" , reply.getRno()+"" );       // 숫자타입 +"" =>문자타입 변환
                map.put( "rcontent" , reply.getRcontent() );
                map.put( "cdate" , reply.getCdate().toLocalDate().toString() ); // 날짜와시간 중에 날짜만 추출
                map.put( "mid" , reply.getMemberEntity().getMid() ); // 댓글 작성자 아이디
                map.put( "mimg" , reply.getMemberEntity().getMimg() ); // 댓글 작성자 프로필
                // 6. map를 리스트에 담는다.
                replyList.add( map );
            }
        });
        // 7. 반복문 종료 후 반환한다.
        return replyList;
    };

}
