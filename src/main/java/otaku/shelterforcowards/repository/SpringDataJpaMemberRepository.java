package otaku.shelterforcowards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import otaku.shelterforcowards.domain.Member;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    @Override
    Optional<Member> findByName(String name);

    @Override
    void delete(Member member);

}
