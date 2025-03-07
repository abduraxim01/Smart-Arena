package com.practise.Smart_Arena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SmartArenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartArenaApplication.class, args);
	}

}
