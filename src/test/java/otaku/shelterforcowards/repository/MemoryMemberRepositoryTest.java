package otaku.shelterforcowards.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import otaku.shelterforcowards.domain.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();


    // 테스트 하나가 끝날 때마다 실행
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();

        member.setName("소이");
        repository.save(member);


        // optional로 반환되기 때문에 get 메서드로 꺼내주어야 한다.
        Member result = repository.findById(member.getId()).get();

        // null 일때 (실패 테스트)
//        Assertions.assertThat(member).isEqualTo(null);

        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("testname1");

        repository.save(member1);

        Member member2 = new Member();
        member2.setName("testname2");
        repository.save(member2);

        // Optional을 Member 타입으로 꺼내준다.
        Member result = repository.findByName("testname1").get();

        // 성공 테스트
        assertThat(result).isEqualTo(member1);

        // 실패 테스트
//        assertThat(result).isEqualTo(member2);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("소이");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("논노");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);

    }
}
