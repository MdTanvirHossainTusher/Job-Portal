package com.example.job_portal.service.impl;

import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.Company;
import com.example.job_portal.entity.Job;
import com.example.job_portal.entity.Profile;
import com.example.job_portal.entity.User;
import com.example.job_portal.exception.ResourceAlreadyExistsException;
import com.example.job_portal.exception.ResourceNotFoundException;
import com.example.job_portal.repository.CompanyRepository;
import com.example.job_portal.repository.JobRepository;
import com.example.job_portal.repository.UserRepository;
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
            Company company = companyRepository.findByIdAndIsDeletedFalse(companyId)
                    .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + companyId));

            Job newJob = new Job();
            newJob.setId(jobDTO.getId());
            newJob.setJobTitle(jobDTO.getJobTitle());
            newJob.setJobDescription(jobDTO.getJobDescription());
            newJob.setSalary(jobDTO.getSalary());
            newJob.setJobPosition(jobDTO.getJobPosition());
            newJob.setJobLocation(jobDTO.getJobLocation());
            newJob.setCompany(company);

            return EntityToEntityDTOConverter.convertJobToJobDTO(jobRepository.save(newJob));

        } catch (ResourceNotFoundException | ResourceAlreadyExistsException e) {
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
            Company company = companyRepository.findByIdAndIsDeletedFalse(companyId)
                    .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + companyId));

            for (Job job : company.getJobs()) {
                if (!job.isDeleted() && job.getId().equals(jobId)) {
                    return EntityToEntityDTOConverter.convertJobToJobDTO(job);
                }
            }
            throw new ResourceNotFoundException("Job not found with id: " + jobId + " in company: " + company.getCompanyName());

        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Error occurred while finding job: " + ex.getMessage());
        }
    }


    @Override
    @Transactional
    public JobDTO updateJob(Long companyId, Long jobId, JobDTO jobDTO) {

        try {
            Company company = companyRepository.findByIdAndIsDeletedFalse(companyId)
                    .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + companyId));

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
            throw new ResourceNotFoundException("Job not found with id: " + jobId + " in company: " + company.getCompanyName());

        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Error occurred while finding job: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteJobById(Long companyId, Long jobId) {

        Company company = companyRepository.findByIdAndIsDeletedFalse(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + companyId));

        Iterator<Job> iterator = company.getJobs().iterator();
        while (iterator.hasNext()) {
            Job existingJob = iterator.next();
            if (!existingJob.isDeleted() && existingJob.getId().equals(jobId)) {
                existingJob.setDeleted(true);
                jobRepository.save(existingJob);
                return;
            }
        }
        throw new ResourceNotFoundException("Job not found with id: " + jobId);
    }

    @Override
    public void applyToJobByUser(Long jobId, Long userId) {
        Job job = jobRepository.findJobById(jobId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Job with id: %d is not found", jobId)));

        User user = userRepository.findUserById(userId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with id: %d is not found", userId)));

        for(Profile profile: job.getProfiles()) {
            if(profile.getUser().getId().equals(userId)) {
                throw new RuntimeException("You have already applied to this job");
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
