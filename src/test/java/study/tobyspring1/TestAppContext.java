package study.tobyspring1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import study.tobyspring1.user.service.UserServiceTest;
import study.tobyspring1.user.service.DummyMailSender;
import study.tobyspring1.user.service.UserService;

@Configuration
@Profile("test")
public class TestAppContext {

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }

    @Bean("testUserService")
    public UserService testUserService() {
        return new UserServiceTest.TestUserService();
    }
}
