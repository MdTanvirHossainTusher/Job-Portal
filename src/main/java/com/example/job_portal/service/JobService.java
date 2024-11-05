package com.example.job_portal.service;

import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.Job;
import com.example.job_portal.entity.Job;

import java.util.List;

public interface JobService {

    void createJob(JobDTO companyDTO);

    Job saveJob(Job company);

    Job findJobById(Long id);

    Job updateJob(Long id, Job company);

    List<Job> findAll();

    void deleteJobById(Long id);

}
