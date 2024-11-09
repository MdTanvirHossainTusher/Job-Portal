package com.example.job_portal.service.impl;

import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.*;
import com.example.job_portal.exception.CompanyNotFoundException;
import com.example.job_portal.exception.JobAlreadyExistsException;
import com.example.job_portal.exception.JobNotFoundException;
import com.example.job_portal.repository.*;
import com.example.job_portal.repository.JobRepository;
import com.example.job_portal.service.CompanyService;
import com.example.job_portal.service.JobService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final CompanyService companyService;

    public JobServiceImpl(JobRepository jobRepository, CompanyRepository companyRepository, CompanyService companyService) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.companyService = companyService;
    }

    @Override
    @Transactional
    public JobDTO createJob(Long companyId, JobDTO jobDTO) {
        try {
            Company company = companyRepository.findCompanyById(companyId)
                    .orElseThrow(() -> new CompanyNotFoundException("Company not found with ID: " + companyId));

            Job newJob = new Job();
            newJob.setId(jobDTO.getId());
            newJob.setJobTitle(jobDTO.getJobTitle());
            newJob.setJobDescription(jobDTO.getJobDescription());
            newJob.setSalary(jobDTO.getSalary());
            newJob.setJobPosition(jobDTO.getJobPosition());
            newJob.setJobLocation(jobDTO.getJobLocation());
            newJob.setCompany(company);

            return EntityToEntityDTOConverter.convertJobToJobDTO(jobRepository.save(newJob));

        } catch (CompanyNotFoundException | JobAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error creating job: " + e.getMessage(), e);
        }
    }


    @Override
    @Transactional
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public JobDTO findJobById(Long companyId, Long jobId) {

        try {
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + companyId));

            for (Job job : company.getJobs()) {
                if (!job.isDeleted() && job.getId().equals(jobId)) {
                    return EntityToEntityDTOConverter.convertJobToJobDTO(job);
                }
            }
            throw new JobNotFoundException("Job not found with id: " + jobId + " in company: " + company.getCompanyName());

        } catch (CompanyNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Error occurred while finding job: " + ex.getMessage());
        }
    }


    @Override
    @Transactional
    public JobDTO updateJob(Long companyId, Long jobId, JobDTO jobDTO) {

        try {
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + companyId));

            for (Job existingJob : company.getJobs()) {
                if (!existingJob.isDeleted() && existingJob.getId().equals(jobId)) {

                    if(jobDTO.getJobTitle() != null) existingJob.setJobTitle(jobDTO.getJobTitle());
                    if(jobDTO.getJobDescription() != null) existingJob.setJobDescription(jobDTO.getJobDescription());
                    if(jobDTO.getSalary() != null) existingJob.setSalary(jobDTO.getSalary());
                    if(jobDTO.getJobPosition() != null) existingJob.setJobPosition(jobDTO.getJobPosition());

                    Job job = saveJob(existingJob);

                    return EntityToEntityDTOConverter.convertJobToJobDTO(job);
                }
            }
            throw new JobNotFoundException("Job not found with id: " + jobId + " in company: " + company.getCompanyName());

        } catch (CompanyNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Error occurred while finding job: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteJobById(Long companyId, Long jobId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + companyId));

        Iterator<Job> iterator = company.getJobs().iterator();
        while (iterator.hasNext()) {
            Job existingJob = iterator.next();
            if (!existingJob.isDeleted() && existingJob.getId().equals(jobId)) {
                existingJob.setDeleted(true);
                jobRepository.save(existingJob);
                return;
            }
        }

        throw new JobNotFoundException("Job not found with id: " + jobId);
    }

}
