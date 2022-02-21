package otaku.shelterforcowards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otaku.shelterforcowards.domain.Member;
import otaku.shelterforcowards.repository.MemberRepository;

import java.util.List;
import java.util.Optional;


public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    /**
     *회원가입
     */
    public Long join(Member member) {

        validateDuplicateMember(member); // 중복 회원 거르기
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
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
