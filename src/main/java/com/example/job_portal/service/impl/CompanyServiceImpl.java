package com.example.job_portal.service.impl;

import com.example.job_portal.dto.CompanyDTO;
import com.example.job_portal.entity.*;
import com.example.job_portal.exception.CompanyAlreadyExistsException;
import com.example.job_portal.exception.CompanyNotFoundException;
import com.example.job_portal.repository.CompanyRepository;
import com.example.job_portal.service.CompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    @Transactional
    public void createCompany(CompanyDTO companyDTO) {

        if(companyRepository.existsByName(companyDTO.getCompanyName())) {
            throw new CompanyAlreadyExistsException("Company is already exists!");
        }

        Company newCompany = new Company();
        newCompany.setCompanyName(companyDTO.getCompanyName());
        newCompany.setCompanyLocation(companyDTO.getCompanyLocation());
        newCompany.setCompanyType(companyDTO.getCompanyType());
        newCompany.setWorkingMode(companyDTO.getWorkingMode());

        Job job = new Job();
        List<Job> jobs = new ArrayList<>();
        jobs.add(job);

        List<MyCompany> myCompanies = new ArrayList<>();
        myCompanies.add(new MyCompany());

        newCompany.setJobs(jobs);
        newCompany.setMyCompanies(myCompanies);

        companyRepository.save(newCompany);
    }

    @Override
    @Transactional
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company findCompanyById(Long id) {
        Optional<Company> company = companyRepository.findById(id);
        return company.orElse(null);
    }

    @Override
    public Company updateCompany(Long id, Company company) {
        Company existingCompany = findCompanyById(id);

        if(existingCompany != null) {
            if(company.getCompanyType() != null) existingCompany.setCompanyType(company.getCompanyType());
            if(company.getCompanyLocation() != null) existingCompany.setCompanyLocation(company.getCompanyLocation());
            if(company.getWorkingMode() != null) existingCompany.setWorkingMode(company.getWorkingMode());

            companyRepository.save(existingCompany);
        }
        else {
            throw new CompanyNotFoundException("Company : " + company.getCompanyName() + " not found!");
        }
        return existingCompany;
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public void deleteCompanyById(Long id) {
        Company company = findCompanyById(id);
        try {
            if(company != null) companyRepository.deleteById(id);
        } catch (Exception ex) {
            throw new CompanyNotFoundException("Company : " + company.getCompanyName() + " not found!");
        }
    }


}
