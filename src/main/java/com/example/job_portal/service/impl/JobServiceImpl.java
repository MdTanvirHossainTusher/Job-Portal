package com.example.job_portal.service.impl;

import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.*;
import com.example.job_portal.exception.CompanyNotFoundException;
import com.example.job_portal.exception.JobAlreadyExistsException;
import com.example.job_portal.exception.JobNotFoundException;
import com.example.job_portal.repository.CVRepository;
import com.example.job_portal.repository.CompanyRepository;
import com.example.job_portal.repository.JobRepository;
import com.example.job_portal.repository.ProfileRepository;
import com.example.job_portal.service.JobService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final CVRepository cvRepository;
    private final ProfileRepository profileRepository;

    public JobServiceImpl(JobRepository jobRepository, CompanyRepository companyRepository, CVRepository cvRepository, ProfileRepository profileRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.cvRepository = cvRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional
    public void createJob(JobDTO jobDTO) {
        if(jobRepository.existsSimilarJob(
                jobDTO.getJobTitle(),
                jobDTO.getJobDescription(),
                jobDTO.getSalary(),
                jobDTO.getJobPosition(),
                jobDTO.getJobLocation())
        ) {
            throw new JobAlreadyExistsException("Job is already exists!");
        }

        Job job = new Job();
        job.setJobTitle(jobDTO.getJobTitle());
        job.setJobDescription(jobDTO.getJobDescription());
        job.setJobLocation(jobDTO.getJobLocation());
        job.setJobPosition(jobDTO.getJobPosition());
        job.setSalary(jobDTO.getSalary());

        CV cv = new CV();
        cv = cvRepository.save(cv);
        List<CV> cvs = new ArrayList<>();
        cvs.add(cv);
        job.setCvs(cvs);

        Company company = companyRepository.findCompanyByName("google");

        if(company != null) {
            job.setCompany(company);

            Profile profile = new Profile();
            profile = profileRepository.save(profile);
            List<Profile> profiles = new ArrayList<>();
            profiles.add(profile);

            job.setProfiles(profiles);

            jobRepository.save(job);
        }
        else {
            throw new CompanyNotFoundException("This job is not associated with any registered company!!");
        }

    }

    @Override
    @Transactional
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public Job findJobById(Long id) {
        Optional<Job> job = jobRepository.findById(id);
        return job.orElse(null);
    }

    @Override
    @Transactional
    public Job updateJob(Long id, Job job) {
        Job existingJob = findJobById(id);

        if(existingJob != null) {
            if(job.getJobTitle() != null) existingJob.setJobTitle(job.getJobTitle());
            if(job.getJobDescription() != null) existingJob.setJobDescription(job.getJobDescription());
            if(job.getJobPosition() != null) existingJob.setJobPosition(job.getJobPosition());
            if(job.getSalary() != null) existingJob.setSalary(job.getSalary());

            jobRepository.save(existingJob);
        }
        else {
            throw new JobNotFoundException("Job with id: "+ id + " is not found!");
        }
        return existingJob;
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteJobById(Long id) {
        Job job = findJobById(id);
        try {
            if(job != null) companyRepository.deleteById(id);
        } catch (Exception ex) {
            throw new JobNotFoundException("Job with id: "+ id + " is not found!");
        }
    }
}
