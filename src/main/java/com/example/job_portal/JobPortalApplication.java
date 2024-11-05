package com.example.job_portal;

import com.example.job_portal.dao.UserDAO;
import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.entity.User;
import com.example.job_portal.repository.UserRepository;
import com.example.job_portal.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class JobPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserService userService, UserDAO userDAO) {
//	public CommandLineRunner commandLineRunner(UserRepository userRepository) {
		return runner -> {
			userOperations(userService, userDAO);
//			userOperations(userRepository);
		};
	}

	private void userOperations(UserService userService, UserDAO userDAO) {

		List<User> users = userDAO.searchUserByEmailPattern("sh");
//		List<User> users = userDAO.searchUserByYearOfExperience(0.2);
//
//
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//
//
//		Date userCreationDate = null;
//		try {
//			userCreationDate = dateFormat.parse("4-11-2024");
//		} catch (ParseException e) {
//			throw new RuntimeException(e);
//		}
//		List<User> users = userDAO.searchUserByUserCreationDate(userCreationDate);

		for(User user: users) {
			System.out.println(user.getEmail() + " " + user.getName() + " " + user.getTotalExperience());
		}


//		System.out.println(users.get(0).getEmail());


//		UserDTO userDTO = new UserDTO();
//
//		userDTO.setName("tusher");
//		userDTO.setEmail("tusher@gmail.com");
//		userDTO.setPassword("test123");
//		userDTO.setProfileImageUrl("tushers.com/img/tusher.jpg");
//		userDTO.setTotalExperience(0.8);
//

//		userDTO.setName("sohan");
//		userDTO.setEmail("sohan@gmail.com");
//		userDTO.setPassword("test123");
//		userDTO.setProfileImageUrl("sohan.com/img/sohan.jpg");
//		userDTO.setTotalExperience(0.2);
//
//		userService.createUser(userDTO);

//		userService.deleteUserById(1L);




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
