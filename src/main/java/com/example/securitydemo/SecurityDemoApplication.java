package com.example.securitydemo;

import com.example.securitydemo.POJO.User;
import com.example.securitydemo.Repository.UserRepo;
import com.example.securitydemo.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityDemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner users(UserRepo userRepo) {
		return (args) -> {

			userRepo.save(new User("Marcus", "123456"));

			/*
			UserService userService = new UserService(userRepo);
			userService.createUser("Marcus", "123456");
			 */
		};
	}
}
