package com.w2m.examen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApiHeroesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiHeroesApplication.class, args);
	}
}
