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

            if (jobRepository.existsSimilarJob(
                    jobDTO.getJobTitle(),
                    jobDTO.getJobDescription(),
                    jobDTO.getSalary(),
                    jobDTO.getJobPosition(),
                    jobDTO.getJobLocation())
            ) {
                throw new JobAlreadyExistsException("Job with title: " + jobDTO.getJobTitle() + " already exists!");
            }

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


//    @Override
//    @Transactional
//    public Job saveJob(Job job) {
//        return jobRepository.save(job);
//    }
//
//    @Override
//    public JobDTO findJobById(Long companyId, Long jobId) {
//        Optional<Job> jobOptional = jobRepository.findJobById(companyId, jobId);
//        Job job = jobOptional.orElse(null);
//        return job != null ? EntityToEntityDTOConverter.convertJobToJobDTO(job) : null;
//    }
//
//    @Override
//    @Transactional
//    public JobDTO updateJob(Long companyId, Long jobId, JobDTO jobDTO) {
//        Job existingJob = jobRepository.findJobById(companyId, jobId)
//                .orElseThrow(() -> new JobNotFoundException("Job with id: " + jobId + " is not found!"));
//
//        if(existingJob != null) {
//            if(jobDTO.getJobTitle() != null) existingJob.setJobTitle(jobDTO.getJobTitle());
//            if(jobDTO.getJobDescription() != null) existingJob.setJobDescription(jobDTO.getJobDescription());
//            if(jobDTO.getSalary() != null) existingJob.setSalary(jobDTO.getSalary());
//            if(jobDTO.getJobPosition() != null) existingJob.setJobPosition(jobDTO.getJobPosition());
//
//            Job job = jobRepository.save(existingJob);
//
//            return EntityToEntityDTOConverter.convertJobToJobDTO(job);
//
//        }
//        else {
//            throw new JobNotFoundException("Job with id: " + jobId + " is not found!");
//        }
//    }
//
////    @Override
////    public List<JobDTO> findAllJobs() {
////        return EntityToEntityDTOConverter.convertJobsToJobsDTO(jobRepository.findAllJobs());
////    }
//
//    @Override
//    @Transactional
//    public void deleteJobById(Long companyId, Long jobId) {
//        JobDTO job = findJobById(companyId, jobId);
//
//        try {
//            if(job != null) jobRepository.softDeleteJobById(companyId, jobId);
////            if(job != null) ;
//        } catch (Exception ex) {
//            throw new JobNotFoundException("Job is not found!");
//        }
//    }
//
//    @Override
//    public void applyToJobByUser(Long companyId, Long jobId, Long userId) {
//
//    }

//    @Override
//    public List<JobDTO> getAllJobsUnderOneCompany(Long companyId) {
//        CompanyDTO company = findCompanyById(companyId);
//
//        if(company != null) {
//            List<Job> jobs = jobRepository.findAllJobsUnderCompanyByCompanyId(companyId);
//            return EntityToEntityDTOConverter.convertJobsToJobsDTO(jobs);
//        }
//        else throw new CompanyNotFoundException("Company is not found!");
//    }

}
