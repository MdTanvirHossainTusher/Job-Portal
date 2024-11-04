package com.example.job_portal;

import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.entity.User;
import com.example.job_portal.repository.UserRepository;
import com.example.job_portal.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class JobPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(UserService userService) {
//	public CommandLineRunner commandLineRunner(UserRepository userRepository) {
		return runner -> {
			userOperations(userService);
//			userOperations(userRepository);
		};
	}

	private void userOperations(UserService userService) {

		UserDTO userDTO = new UserDTO();

		userDTO.setName("tanvir");
		userDTO.setEmail("tanvir@gmail.com");
		userDTO.setPassword("test123");
		userDTO.setProfileImageUrl("tushers.com/img/tanvir.png");
		userDTO.setTotalExperience(0.8);

		userService.createUser(userDTO);




//		List<User> allUser = userService.findAll();
//
//		for(User user: allUser) {
//			System.out.println(user.getId());
//			System.out.println(user.getName());
//			System.out.println(user.getEmail());
//			System.out.println(user.getProfile());
//			System.out.println(user.getImageUrl());
//			System.out.println(user.getTotalExperience());
//		}
//
//		System.out.println("Update user: ");
//
//		User u = new User();
//		u.setName("tanvir");
//		u.setTotalExperience(2.0);
//
//		User updateUser = userService.updateUser(2L, u);
//
//		System.out.println("After updating .... ");
//		List<User> allUser1 = userService.findAll();
//		for(User user: allUser1) {
//			System.out.println(user.getId());
//			System.out.println(user.getName());
//			System.out.println(user.getEmail());
//			System.out.println(user.getProfile());
//			System.out.println(user.getImageUrl());
//			System.out.println(user.getTotalExperience());
//		}

	}
}
