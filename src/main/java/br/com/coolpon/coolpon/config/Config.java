package br.com.coolpon.coolpon.config;

import br.com.coolpon.coolpon.message.service.SmsService;
import com.twilio.Twilio;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public SmsService sms(){
        return new SmsService();
    }
}
