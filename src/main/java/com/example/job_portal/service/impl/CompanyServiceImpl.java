package com.example.job_portal.service.impl;

import com.example.job_portal.dto.CompanyDTO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.*;
import com.example.job_portal.exception.CompanyAlreadyExistsException;
import com.example.job_portal.exception.CompanyNotFoundException;
import com.example.job_portal.repository.CompanyRepository;
import com.example.job_portal.repository.JobRepository;
import com.example.job_portal.service.CompanyService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, JobRepository jobRepository) {
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    @Transactional
    public CompanyDTO createCompany(CompanyDTO companyDTO) {

        if(companyRepository.existsByName(companyDTO.getCompanyName())) {
            throw new CompanyAlreadyExistsException("Company is already exists!");
        }

        Company newCompany = new Company();
        newCompany.setId(companyDTO.getId());
        newCompany.setCompanyName(companyDTO.getCompanyName());
        newCompany.setCompanyLocation(companyDTO.getCompanyLocation());
        newCompany.setCompanyType(companyDTO.getCompanyType());
        newCompany.setWorkingMode(companyDTO.getWorkingMode());

//        Job job = new Job();
//        List<Job> jobs = new ArrayList<>();
//        jobs.add(job);
//
//        List<MyCompany> myCompanies = new ArrayList<>();
//        myCompanies.add(new MyCompany());
//
//        newCompany.setJobs(jobs);
//        newCompany.setMyCompanies(myCompanies);

        return EntityToEntityDTOConverter.convertCompanyToCompanyDTO(companyRepository.save(newCompany));
    }

    @Override
    @Transactional
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public CompanyDTO findCompanyById(Long id) {
        Optional<Company> companyOptional = companyRepository.findCompanyById(id);
        Company company = companyOptional.orElse(null);
        return company != null ? EntityToEntityDTOConverter.convertCompanyToCompanyDTO(company) : null;
    }

    @Override
    @Transactional
    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {
        Company existingCompany = companyRepository.findCompanyById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company with id: " + id + " is not found!"));

        if(existingCompany != null) {
            if(companyDTO.getCompanyType() != null) existingCompany.setCompanyType(companyDTO.getCompanyType());
            if(companyDTO.getCompanyLocation() != null) existingCompany.setCompanyLocation(companyDTO.getCompanyLocation());
            if(companyDTO.getWorkingMode() != null) existingCompany.setWorkingMode(companyDTO.getWorkingMode());

            Company company = companyRepository.save(existingCompany);

            return EntityToEntityDTOConverter.convertCompanyToCompanyDTO(company);

        }
        else {
            throw new CompanyNotFoundException("Company : " + companyDTO.getCompanyName() + " is not found!");
        }
    }

    @Override
    public List<CompanyDTO> findAllCompany() {
        return EntityToEntityDTOConverter.convertCompaniesToCompaniesDTO(companyRepository.findAllCompany());
    }

    @Override
    @Transactional
    public void deleteCompanyById(Long id) {
        CompanyDTO company = findCompanyById(id);
        try {
            if(company != null) companyRepository.softDeleteCompanyById(id);
        } catch (Exception ex) {
            throw new CompanyNotFoundException("Company : " + company.getCompanyName() + " not found!");
        }
    }

    @Override
    public List<JobDTO> getAllJobsUnderOneCompany(Long companyId) {
//        CompanyDTO company = findCompanyById(companyId);

        Company company = companyRepository.findCompanyById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company with id: " + companyId + " is not found!"));


        if(company != null) {
//            List<Job> jobs = jobRepository.findAllJobsUnderCompanyByCompanyId(companyId);
            List<Job> jobs = company.getJobs();
            return EntityToEntityDTOConverter.convertJobsToJobsDTO(jobs);
        }
        else throw new CompanyNotFoundException("Company is not found!");
    }


}
