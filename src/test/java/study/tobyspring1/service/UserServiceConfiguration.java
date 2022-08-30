package study.tobyspring1.service;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;

public class UserServiceConfiguration {

    @Bean
    public MailSender mailSender() {
        DummyMailSender mailSender = new DummyMailSender();
        return mailSender;
    }
}
