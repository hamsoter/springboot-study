package otaku.shelterforcowards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import otaku.shelterforcowards.repository.MemberRepository;
import otaku.shelterforcowards.repository.jdbcMemberRepository;
import otaku.shelterforcowards.service.MemberService;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new jdbcMemberRepository(dataSource);
    }
}
