package korweb.model.service;

import jakarta.transaction.Transactional;
import korweb.model.dto.MemberDto;
import korweb.model.entity.MemberEntity;
import korweb.model.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServcie {

    @Autowired private MemberRepository memberRepository;

    // 1. 회원가입 서비스
    @Transactional  // 트랜잭션
    public boolean signup(MemberDto memberDto) {
        // 1. 저장할 dto를 entity로 변환한다.
        MemberEntity memberEntity = memberDto.toEntity();
        // 2. 변환된 entity 를 save 한다.
        // 3. save(영속성/연결된)한 엔티티를 반환받는다.
        MemberEntity saveEntity = memberRepository.save(memberEntity);
        // 4. 만약에 영속된 엔티티의 회원번호가 0보다 크면
        if (saveEntity.getMno() > 0) {
            return true;}
        else { return  false;}

    }

}
