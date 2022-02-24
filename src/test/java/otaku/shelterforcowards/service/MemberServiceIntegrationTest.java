package otaku.shelterforcowards.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import otaku.shelterforcowards.domain.Member;
import otaku.shelterforcowards.repository.MemberRepository;
import otaku.shelterforcowards.repository.MemoryMemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
// Transaction으로 테스트 후 데이터 자동롤백
@Transactional
class MemberServiceIntegrationTest {

    // 편의를 위해 Autowired 주입 사용
    @Autowired MemberService memberService;


    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("기야미");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 회원가입실패() {
        // given
        Member member = new Member();
        member.setName("기야!");

        // when
        final IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member));

        // then
        assertThat(e.getMessage()).isEqualTo("아이디에 특수문자를 넣을 수 없습니다.");
    }

    @Test
    void 중복회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("소이");
        Member member2 = new Member();
        member2.setName("소이");

        // when
        memberService.join(member1);

        // 람다식의 로직을 실행할 시 첫 번째 인자의 예외가 터지면 테스트 성공
        final IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

    @Test
    void 전체회원조회() {
    }

    @Test
    void 특정회원조회() {
    }
}