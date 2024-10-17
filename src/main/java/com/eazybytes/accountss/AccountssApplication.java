package com.eazybytes.accountss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

@ComponentScans({ @ComponentScan("com.eazybytes.accountss") })
@EnableJpaRepositories("com.eazybytes.accountss.repository")
@EntityScan("com.eazybytes.accountss.entity")
public class AccountssApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountssApplication.class, args);
	}

}
