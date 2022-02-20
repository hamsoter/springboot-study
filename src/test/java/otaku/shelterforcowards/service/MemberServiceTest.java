package otaku.shelterforcowards.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otaku.shelterforcowards.domain.Member;
import otaku.shelterforcowards.repository.MemoryMemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


class MemberServiceTest {

    // given: 어떤 상황이 주어졌을 때

    // when: 이것을 실행했을때

    // then: 이렇게 나와야 한다.

    MemberService memberService;
    MemoryMemberRepository repository;


    @BeforeEach
    public void beforeEach() {
        repository = new MemoryMemberRepository();
        memberService = new MemberService(repository);
    }


    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("소이");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
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
/*

        try {
            memberService.join(member2);
            fail();

        // 예외가 터진 경우
        }catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        */

    }

    @Test
    void 전체회원조회() {
    }

    @Test
    void 특정회원조회() {
    }
}