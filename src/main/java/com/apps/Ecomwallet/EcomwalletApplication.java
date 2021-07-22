package com.apps.Ecomwallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EcomwalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomwalletApplication.class, args);
	}

}
