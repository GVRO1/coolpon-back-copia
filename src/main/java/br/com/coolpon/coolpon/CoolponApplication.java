package br.com.coolpon.coolpon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CoolponApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoolponApplication.class, args);
	}

}
