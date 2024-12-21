package com.almousleck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LinkedinApplication {
	public static void main(String[] args) {
		SpringApplication.run(LinkedinApplication.class, args);
	}
}
