package com.example.job_portal;

import com.example.job_portal.entity.*;
import com.example.job_portal.repository.CompanyRepository;
import com.example.job_portal.service.CompanyService;
import com.example.job_portal.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class JobPortalApplicationTests {

//	private CompanyService companyService;
//
//	public JobPortalApplicationTests(CompanyService companyService) {
//		this.companyService = companyService;
//	}
//
//	@Test
//	void contextLoads() {
//		companyService.save(new Company("Google", "US", "Software company", "onsite",
//				new Job("traine SWE", "25000", false, Array. {"cv1", "cv2"},  ), new MyCompany()));
//	}


	@Mock
	private CompanyRepository companyRepository;

	@InjectMocks
	private CompanyServiceImpl companyService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSaveCompany() {
//
//		User user = new User(
//				"tusher", "tusher@gmail.com", "4332ed", "www.freeimage.com/tusher", "1.5",
//				Arrays.asList("ROLE_ADMIN", "ROLE_USER"),
//				Arrays.asList(new Profile())
//				);
//
//
//		List<CV> cvs = Arrays.asList(
//				new CV()
//		);
//
//		List<Job> jobs = Arrays.asList(
//				new Job("Software Engineer", "50000", false, Arrays.asList(), new Company())
////				new Job("Data Scientist", "60000", false)
//		);
//
//		List<MyCompany> myCompanies = Arrays.asList(
//				new MyCompany()
//		);
//
//		Company company = new Company("Google", "US", "Software Company", "onsite", jobs, myCompanies);
//
//
//		companyService.save(company);
//
//
//		verify(companyRepository, times(1)).save(company);



//		Profile profile = new Profile("2 years", "Java, Spring Boot, SQL");
//
//		CV cv1 = new CV("PDF", "2MB", "cv_url1");
//		CV cv2 = new CV("PDF", "3MB", "cv_url2");
//
//		Job job1 = new Job("Trainee SWE", "25000", false, Arrays.asList(cv1), profile);
//		Job job2 = new Job("Software Engineer", "50000", false, Arrays.asList(cv2), profile);
//
//		MyCompany myCompany = new MyCompany("Partner");
//
//		Company company = new Company("Google", "US", "Software Company", "Onsite",
//				Arrays.asList(job1, job2),
//				Arrays.asList(myCompany));
//
//		companyService.save(company);
	}

}
