package otaku.shelterforcowards.service;

import org.springframework.transaction.annotation.Transactional;
import otaku.shelterforcowards.domain.Member;
import otaku.shelterforcowards.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;


@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    /**
     *회원가입
     */
    public Long join(Member member) {
            joinCheck(member);
            validateDuplicateMember(member); // 중복 회원 거르기
            memberRepository.save(member);
            return member.getId();
    }

    /**
     * 로그인
     */

    public boolean login(Member member) {
        return loginCheck(member);
    }

    // 중복회원체크
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    // 아이디 유효성 체크(영어,한글 사용 가능)
    private void joinCheck(Member member) {
        if(!(Pattern.matches("^[a-zA-Z0-9-가-힣]{3,12}$", member.getName()))) {
            throw new IllegalStateException("아이디는 특수문자를 포함하지 않은 3~12자여야 합니다.");
        }
        // 비밀번호 (숫자, 문자, 특수문자 포함 8~15자리 이내)
        if(!(Pattern.matches("^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", member.getPassword()))) {
            throw new IllegalStateException("암호는 숫자, 특수문자, 문자가 포함된 8~15자리여야 합니다.");
        }
    }

    /**
     * 세션 저장
     */


    /**
     * 전체 회원 조회
     * */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 특정 회원 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Optional<Member> findByName(String memberName) {
        return memberRepository.findByName(memberName);
    }

    private boolean loginCheck (Member loginMember) {

        try {
            Member findMember = memberRepository.findByName(loginMember.getName()).get();
            if (!(findMember.getPassword().equals(loginMember.getPassword()))) {
                throw new IllegalStateException("비밀번호를 다시 확인하세요.");
            }
            return true;
            
            // findMember를 찾지 못할 시
        } catch (NoSuchElementException e) {
            throw new IllegalStateException("일치하는 아이디가 없습니다.");
        }

    }


}
