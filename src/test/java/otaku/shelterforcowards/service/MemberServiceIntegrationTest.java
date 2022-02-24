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
        member.setPassword("rldialwl1!");

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
        assertThat(e.getMessage()).isEqualTo("아이디는 특수문자를 포함하지 않은 3~12자여야 합니다.");
    }

    @Test
    void 중복회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("소이이");
        member1.setPassword("sianansi1!");
        Member member2 = new Member();
        member2.setName("소이이");
        member2.setPassword("sianansi1!");

        // when
        memberService.join(member1);

        // 람다식의 로직을 실행할 시 첫 번째 인자의 예외가 터지면 테스트 성공
        final IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

    @Test
    void 로그인_비밀번호실패() {

        // given
        Member member1 = new Member();
        member1.setName("소이이");
        member1.setPassword("sianansi1!");
        Member member2 = new Member();
        member2.setName("소이이");
        member2.setPassword("xmfflszz1!");

        memberService.join(member1);
        // when
        // 람다식의 로직을 실행할 시 첫 번째 인자의 예외가 터지면 테스트 성공
        final IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.login(member2));

        // then
        assertThat(e.getMessage()).isEqualTo("비밀번호를 다시 확인하세요.");

    }


    @Test
    void 로그인_아이디실패() {

        // given
        Member member1 = new Member();
        member1.setName("소이이");
        member1.setPassword("sianansi1!");
        Member member2 = new Member();
        member2.setName("소이냐");
        member2.setPassword("xmfflszz1!");

        memberService.join(member1);
        // when

        // when
        // 람다식의 로직을 실행할 시 첫 번째 인자의 예외가 터지면 테스트 성공
        final IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.login(member2));

        // then
        assertThat(e.getMessage()).isEqualTo("일치하는 아이디가 없습니다.");
    }


    @Test
    void 로그인_성공() {

        // given
        Member member1 = new Member();
        member1.setName("소이이");
        member1.setPassword("sianansi1!");
        Member member2 = new Member();
        member2.setName("소이이");
        member2.setPassword("sianansi1!");

        memberService.join(member1);
        memberService.login(member2);
    }
}