package com.example.job_portal;

import com.example.job_portal.entity.Company;
import com.example.job_portal.entity.Job;
import com.example.job_portal.entity.MyCompany;
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

//		List<Job> jobs = Arrays.asList(
//				new Job("Software Engineer", "50000", false, ),
//				new Job("Data Scientist", "60000", false)
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
	}

}
