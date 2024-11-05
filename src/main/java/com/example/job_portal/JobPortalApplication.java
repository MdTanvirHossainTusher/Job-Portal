package com.example.job_portal;

import com.example.job_portal.dao.CompanyDAO;
import com.example.job_portal.dao.UserDAO;
import com.example.job_portal.dao.JobDAO;
import com.example.job_portal.dto.CompanyDTO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.Company;
import com.example.job_portal.entity.Job;
import com.example.job_portal.entity.User;
import com.example.job_portal.service.CompanyService;
import com.example.job_portal.service.JobService;
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
	public CommandLineRunner commandLineRunner(
			UserService userService,
			UserDAO userDAO,
			CompanyService companyService,
			CompanyDAO companyDAO,
			JobService jobService,
			JobDAO jobDAO
			) {
		return runner -> {
//			userOperations(userService, userDAO);
//			companyOperations(companyService, companyDAO);
//			jobOperations(jobService, jobDAO);
		};
	}

	private void createJob(JobService jobService) {
		JobDTO jobDTO = new JobDTO();

		jobDTO.setJobTitle("SWE-1");
		jobDTO.setJobDescription("Job description: " +
				"1. java 2. git 3. spring");
		jobDTO.setSalary("45000");
		jobDTO.setJobLocation("BD");
		jobDTO.setJobPosition("Mid");

		jobService.createJob(jobDTO);
	}

	private void updateJob(JobService jobService) {
		Job job = new Job();

		job.setSalary("10000");

		jobService.updateJob(9L, job);
	}

	private void findAllJob(JobService jobService) {
		List<Job> jobs = jobService.findAll();

		for(Job job: jobs) {
			System.out.println(job.getJobTitle() + " " + job.getCompany().getCompanyName());
		}
	}

	private void jobOperations(JobService jobService, JobDAO jobDAO) {
//		createJob(jobService);
//		updateJob(jobService);
//
//		findAllJob(jobService); // problem
//		System.out.println(jobService.findJobById(8L).getJobLocation());
//		jobService.deleteJobById(3L);

//		List<Job> jobs = jobDAO.filterJobByJobLocation("sk");
//		List<Job> jobs = jobDAO.filterJobByJobPosition("in");
//
//		for(Job job: jobs) {
//			System.out.println(job.getJobLocation() + " " + job.getSalary()
//			+ " " + job.getJobPosition());
//		}
	}



	private void createCompany(CompanyService companyService) {
		CompanyDTO company = new CompanyDTO();
		company.setCompanyName("micro1");
		company.setCompanyType("Software and Data annotations");
		company.setWorkingMode("Remote");
		company.setCompanyLocation("South Asia");

		companyService.createCompany(company);
	}

	private void updateCompany(CompanyService companyService) {
		Company company = new Company();
//		company.setCompanyName("Google");
		company.setCompanyType("Software Company");
//		company.setWorkingMode("Onsite");
		company.setCompanyLocation("World-wide");

		companyService.updateCompany(1L, company);
	}

	private void findAllCompany(CompanyService companyService) {
		List<Company> companies = companyService.findAll();

		for(Company company: companies) {
			System.out.println(company.getCompanyName() + " " + company.getCompanyType());
		}
	}

	private void companyOperations(CompanyService companyService, CompanyDAO companyDAO) {
//		createCompany(companyService);
//		updateCompany(companyService);
//		findAllCompany(companyService);
//		System.out.println(companyService.findCompanyById(1L).getCompanyName());
//		companyService.deleteCompanyById(3L);
	}


	private void userOperations(UserService userService, UserDAO userDAO) {

//		List<User> users = userDAO.searchUserByEmailPattern("vi");
		List<User> users = userDAO.searchUserByYearOfExperience(2.0);
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
