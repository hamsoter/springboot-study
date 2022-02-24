package otaku.shelterforcowards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import otaku.shelterforcowards.repository.MemberRepository;
import otaku.shelterforcowards.service.MemberService;

@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    /*
    @Bean
    public MemberRepository memberRepository() {
        // return new jdbcMemberRepository(dataSource);
        // return new jdbcTemplateMemberRepository(dataSource);
        // return new JpaMemberRepository(em);

    }*/
}
