package com.jwt.secjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.jwt.secjwt.repo.UserRepo;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses=UserRepo.class)
public class SecJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecJwtApplication.class, args);
	}

}
