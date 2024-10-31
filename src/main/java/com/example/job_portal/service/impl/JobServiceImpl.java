package com.example.job_portal.service.impl;

import com.example.job_portal.entity.Job;
import com.example.job_portal.repository.JobRepository;
import com.example.job_portal.service.JobService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    @Transactional
    public void save(Job job) {
        jobRepository.save(job);
    }
}
