package otaku.shelterforcowards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import otaku.shelterforcowards.repository.JpaMemberRepository;
import otaku.shelterforcowards.repository.MemberRepository;
import otaku.shelterforcowards.service.MemberService;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {

    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        // return new jdbcMemberRepository(dataSource);
        // return new jdbcTemplateMemberRepository(dataSource);

        return new JpaMemberRepository(em);
    }
}
