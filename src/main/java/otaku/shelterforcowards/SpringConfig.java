package otaku.shelterforcowards;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import otaku.shelterforcowards.repository.MemberRepository;
import otaku.shelterforcowards.repository.MemoryMemberRepository;
import otaku.shelterforcowards.service.MemberService;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
