package com.example.job_portal.service.impl;

import com.example.job_portal.dto.CompanyDTO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.*;
import com.example.job_portal.exception.*;
import com.example.job_portal.repository.*;
import com.example.job_portal.repository.JobRepository;
import com.example.job_portal.service.JobService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import com.example.job_portal.utils.SortEntityDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public JobServiceImpl(JobRepository jobRepository, UserRepository userRepository, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
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
    public List<JobDTO> findAllJobs() {
        List<JobDTO> jobDTOs =  EntityToEntityDTOConverter.convertJobsToJobsDTO(jobRepository.findAllJobs());
        return SortEntityDTO.sortResponseDTO(jobDTOs, Comparator.comparing(JobDTO::getId).reversed());
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

    @Override
    public void applyToJobByUser(Long jobId, Long userId) {
        Job job = jobRepository.findJobById(jobId).orElseThrow(
                () -> new JobNotFoundException(String.format("Job with id: %d is not found", jobId)));

        User user = userRepository.findUserById(userId).orElseThrow(
                () -> new UserNotFoundException(String.format("User with id: %d is not found", userId)));

        for(Profile profile: job.getProfiles()) {
            if(profile.getUser().getId().equals(userId)) {
                throw new AlreadyAppliedException("You have already applied to this job");
            }
        }

        Profile profile = user.getProfile();

        if(!profile.isDeleted() && profile.getCv() == null) {
            throw new RuntimeException("Please upload a cv first to apply for a job.");
        }

        if(profile.getJobs() == null) {
            profile.setJobs(new ArrayList<>());
        }
        profile.getJobs().add(job);

        if(profile.getCv() != null) {
            if(job.getCvs() == null) {
                job.setCvs(new ArrayList<>());
            }
            job.getCvs().add(profile.getCv());
        }
        jobRepository.save(job);
    }

}
