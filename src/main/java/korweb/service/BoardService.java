
package korweb.service;

import korweb.model.dto.BoardDto;
import korweb.model.dto.MemberDto;
import korweb.model.entity.BoardEntity;
import korweb.model.entity.CategoryEntity;
import korweb.model.entity.MemberEntity;
import korweb.model.repository.BoardRepository;
import korweb.model.repository.CategoryRepository;
import korweb.model.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;   // member 엔티티를 조작하는 인터페이스
    @Autowired BoardRepository boardRepository;     // board 엔티티를 조작하는 인터페이스
    @Autowired CategoryRepository categoryRepository; // category 엔티티를 조작하는 인터페이스

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
    public BoardDto boardFind(int bno) {

        // (1) 조회할 특정 게시물의 번호를 매개변수로 받는다. int bno
        // (2) 조회할 특정 게시물의 번호를 엔티티를 조회한다. .findById() 메소드는 반환타입이 Optional이다.
        // 조회된 엔티티 여부 메소드를 제공한다.
        Optional<BoardEntity> optional = boardRepository.findById(bno);
        // (3) 만약에 조회된 엔티티가 있으면 true / false
        if (optional.isPresent()) {
            // (4) 엔티티 꺼내기 .get()
            BoardEntity boardEntity = optional.get();
            // (5) 엔티티를 dto로 변환
            BoardDto boardDto = boardEntity.toDto();
            // (6) Dto 결과 반환
            return boardDto;
        }
        // 조회결과 엔티티가 없으면 null 반환
        return null;
    }

    // [4] 게시물 특정(개별) 수정
    public boolean boardUpdate(BoardDto boardDto) {
        return false;
    }

    // [5] 게시물 특정(개별) 삭제
    public boolean boardDelete(int bno) {
        return false;
    }



}
