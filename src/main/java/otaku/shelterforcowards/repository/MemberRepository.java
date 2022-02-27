package otaku.shelterforcowards.repository;

import otaku.shelterforcowards.domain.Member;

import java.util.List;
import java.util.Optional;

// 회원 관련 저장소에서 필요한 기능들을 정의
public interface MemberRepository {
    // 1. 저장
    Member save(Member member);

    // 2. 조회
   Optional<Member> findById(Long id);
   Optional<Member> findByName(String name);
   List<Member> findAll();

   // 3. 삭제

    void delete(Member member);

}
