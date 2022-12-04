package com.sohwakmo.cucumbermarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CucumbermarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(CucumbermarketApplication.class, args);
	}

}
