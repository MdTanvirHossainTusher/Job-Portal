package com.example.job_portal.service;

import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.Job;
import com.example.job_portal.entity.Job;
import com.example.job_portal.entity.Job;

import java.util.List;

public interface JobService {

    JobDTO createJob(JobDTO jobDTO);

    Job saveJob(Job job);

    JobDTO findJobById(Long id);

    JobDTO updateJob(Long id, JobDTO companyDTO);

    List<JobDTO> findAllJobs();

    void deleteJobById(Long companyId);

}
