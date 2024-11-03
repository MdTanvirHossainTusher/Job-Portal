package com.example.job_portal;

import com.example.job_portal.entity.*;
import com.example.job_portal.repository.UserRepository;
import com.example.job_portal.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void userSave() {
		Profile profile = new Profile();
		Role role = new Role();
		role.setRole("ROLE_USER");

		List<Role> roles = new ArrayList<>();
		roles.add(role);

		User user = new User(
				"tusher",
				"tusher@gmail.com",
				"4332ed",
				"www.freeimage.com/tusher",
				1.5,
				roles,
				profile
		);

		when(userRepository.save(any(User.class))).thenReturn(user);

		User savedUser = userService.save(user);

		assertNotNull(savedUser);
		assertEquals("name not matched", "tusher", savedUser.getName());
		assertEquals("email not matched","tusher@gmail.com", savedUser.getEmail());

		verify(userRepository).save(any(User.class));
	}
}