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
//
//            if (jobRepository.existsSimilarJob(
//                    jobDTO.getJobTitle(),
//                    jobDTO.getJobDescription(),
//                    jobDTO.getSalary(),
//                    jobDTO.getJobPosition(),
//                    jobDTO.getJobLocation())
//            ) {
//                throw new JobAlreadyExistsException("Job with title: " + jobDTO.getJobTitle() + " already exists!");
//            }

//            if(company != null) {
            Job newJob = new Job();
            newJob.setId(jobDTO.getId());
            newJob.setJobTitle(jobDTO.getJobTitle());
            newJob.setJobDescription(jobDTO.getJobDescription());
            newJob.setSalary(jobDTO.getSalary());
            newJob.setJobPosition(jobDTO.getJobPosition());
            newJob.setJobLocation(jobDTO.getJobLocation());
            newJob.setCompany(company);

//            }
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
    @Override
    public JobDTO findJobById(Long companyId, Long jobId) {

//        Optional<Job> jobOptional = jobRepository.findJobByIdAndCompanyId(companyId, jobId);
//        Job job = jobOptional.orElse(null);
//        System.out.println(job.getJobTitle() + " ---- " + job.getId());
//        return job != null ? EntityToEntityDTOConverter.convertJobToJobDTO(job) : null;


        Optional<Job> jobOptional = jobRepository.findById(jobId);
        Job job = jobOptional.orElse(null);


        if(job != null) {


            Optional<Company> companyOptional = companyRepository.findById(companyId);
            Company company = companyOptional.orElse(null);

            System.out.println(job.getCompany().getCompanyName() + " " + company.getCompanyName().trim().toLowerCase() + " nnnn");
            if(job.getCompany().getCompanyName().equals(company.getCompanyName().trim().toLowerCase())) {
                return EntityToEntityDTOConverter.convertJobToJobDTO(job);
            }
            else {
                throw new CompanyNotFoundException(String.format("Company with id: %d is not found", companyId));
            }

//            List<Job> allJobs = job.getCompany().getJobs();
//            for(Job jobs: allJobs) {
//                System.out.println(jobs + " ..... ");
//            }

//            return EntityToEntityDTOConverter.convertJobsToJobsDTO(allJobs);
        }
//        return job != null ? EntityToEntityDTOConverter.convertJobToJobDTO(job) : null;


//        Job job = jobRepository.findByIdAndCompanyIdAndIsDeletedFalse(jobId, companyId)
//                .orElseThrow(() -> new ResourceNotFoundException(
//                        String.format("Job not found with id %d for company %d", jobId, companyId)
//                ));

//        return modelMapper.map(job, JobDTO.class);
        return null;
    }

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
