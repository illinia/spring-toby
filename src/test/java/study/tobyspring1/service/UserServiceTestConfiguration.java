package study.tobyspring1.service;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;

public class UserServiceTestConfiguration {

    @Bean
    public MailSender mailSender() {
        DummyMailSender mailSender = new DummyMailSender();
        return mailSender;
    }

    @Bean("testUserService")
    public UserService testUserService() {
        return new UserServiceTest.TestUserService();
    }
}
