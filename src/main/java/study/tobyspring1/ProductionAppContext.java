package study.tobyspring1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import study.tobyspring1.user.service.DummyMailSender;

@Configuration
@Profile("production")
public class ProductionAppContext {

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }
}
