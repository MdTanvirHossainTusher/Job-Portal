package com.example.job_portal.service;

import com.example.job_portal.dto.CompanyDTO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.Company;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CompanyService {

    CompanyDTO createCompany(CompanyDTO companyDTO);

    Company saveCompany(Company company);

    CompanyDTO findCompanyById(Long id);

    CompanyDTO updateCompany(Long id, CompanyDTO companyDTO);

    List<CompanyDTO> findAllCompany();

    void deleteCompanyById(Long companyId);

    List<JobDTO> getAllJobsUnderOneCompany(Long companyId);

}
