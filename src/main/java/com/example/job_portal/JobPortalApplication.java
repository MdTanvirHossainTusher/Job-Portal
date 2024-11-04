package com.example.job_portal;

import com.example.job_portal.entity.User;
import com.example.job_portal.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class JobPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(UserService userService) {
		return runner -> {
			userOperations(userService);
		};
	}

	private void userOperations(UserService userService) {
		List<User> allUser = userService.findAll();
		System.out.println("All users: ");
		for(User user: allUser) {
			System.out.println(user.getName());
			System.out.println(user.getEmail());
			System.out.println(user.getProfile());
		}
	}
}
