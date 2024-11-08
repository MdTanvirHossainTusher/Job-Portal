package com.example.job_portal.service.impl;

import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.*;
import com.example.job_portal.exception.JobAlreadyExistsException;
import com.example.job_portal.exception.JobNotFoundException;
import com.example.job_portal.exception.JobAlreadyExistsException;
import com.example.job_portal.exception.JobNotFoundException;
import com.example.job_portal.repository.CVRepository;
import com.example.job_portal.repository.JobRepository;
import com.example.job_portal.repository.JobRepository;
import com.example.job_portal.repository.ProfileRepository;
import com.example.job_portal.service.JobService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    @Transactional
    public JobDTO createJob(JobDTO jobDTO) {

        Job existingJob = jobRepository.findJobById(jobDTO.getId())
                .orElseThrow(() -> new JobNotFoundException("Job with id: " + jobDTO.getId() + " is not found!"));

        Job newJob = new Job();
        newJob.setId(jobDTO.getId());
        newJob.setJobTitle(jobDTO.getJobTitle());
        newJob.setJobDescription(jobDTO.getJobDescription());
        newJob.setSalary(jobDTO.getSalary());
        newJob.setJobPosition(jobDTO.getJobPosition());
        newJob.setJobLocation(jobDTO.getJobLocation());

        return EntityToEntityDTOConverter.convertJobToJobDTO(jobRepository.save(newJob));
    }

    @Override
    @Transactional
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public JobDTO findJobById(Long id) {
        Optional<Job> jobOptional = jobRepository.findJobById(id);
        Job job = jobOptional.orElse(null);
        return job != null ? EntityToEntityDTOConverter.convertJobToJobDTO(job) : null;
    }

    @Override
    @Transactional
    public JobDTO updateJob(Long id, JobDTO jobDTO) {
        Job existingJob = jobRepository.findJobById(id)
                .orElseThrow(() -> new JobNotFoundException("Job with id: " + id + " is not found!"));

        if(existingJob != null) {
            if(jobDTO.getJobTitle() != null) existingJob.setJobTitle(jobDTO.getJobTitle());
            if(jobDTO.getJobDescription() != null) existingJob.setJobDescription(jobDTO.getJobDescription());
            if(jobDTO.getSalary() != null) existingJob.setSalary(jobDTO.getSalary());
            if(jobDTO.getJobPosition() != null) existingJob.setJobPosition(jobDTO.getJobPosition());

            Job job = jobRepository.save(existingJob);

            return EntityToEntityDTOConverter.convertJobToJobDTO(job);

        }
        else {
            throw new JobNotFoundException("Job with id: " + id + " is not found!");
        }
    }

    @Override
    public List<JobDTO> findAllJobs() {
        return EntityToEntityDTOConverter.convertJobsToJobsDTO(jobRepository.findAllJobs());
    }

    @Override
    @Transactional
    public void deleteJobById(Long id) {
        JobDTO job = findJobById(id);
        try {
            if(job != null) jobRepository.softDeleteJobById(id);
        } catch (Exception ex) {
            throw new JobNotFoundException("Job is not found!");
        }
    }

}
