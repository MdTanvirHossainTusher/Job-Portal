package com.example.job_portal.service;

import com.example.job_portal.dto.CompanyDTO;
import com.example.job_portal.entity.Company;
import com.example.job_portal.entity.Company;

import java.util.List;

public interface CompanyService {

    void createCompany(CompanyDTO companyDTO);

    Company saveCompany(Company company);

    Company findCompanyById(Long id);

    //    Company updateCompany(Company user);
    Company updateCompany(Long id, Company company);

    List<Company> findAll();

    void deleteCompanyById(Long id);

}
