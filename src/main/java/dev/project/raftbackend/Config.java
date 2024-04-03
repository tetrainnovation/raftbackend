package dev.project.raftbackend;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {


        @Bean
        public JavaMailSender javaMailSender() {
            return new JavaMailSender();
        }

}
