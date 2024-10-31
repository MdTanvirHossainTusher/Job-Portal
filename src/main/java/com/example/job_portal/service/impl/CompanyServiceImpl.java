package com.example.job_portal.service.impl;

import com.example.job_portal.entity.Company;
import com.example.job_portal.repository.CompanyRepository;
import com.example.job_portal.service.CompanyService;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void save(Company company) {
        companyRepository.save(company);
    }
}
