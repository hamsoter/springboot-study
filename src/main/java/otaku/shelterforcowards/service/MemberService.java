package otaku.shelterforcowards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otaku.shelterforcowards.domain.Member;
import otaku.shelterforcowards.repository.MemberRepository;

import java.util.List;
import java.util.Optional;
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
            throw new IllegalStateException("아이디에 특수문자를 넣을 수 없습니다.");
        }
        // 비밀번호 (숫자, 문자, 특수문자 포함 8~15자리 이내)
        if(!(Pattern.matches("^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", member.getPassword()))) {
            throw new IllegalStateException("암호는 숫자, 특수문자, 문자가 포함된 8~15자리여야 합니다.");
        }
    }


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
}
