package com.example.job_portal.service;

import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.Job;

import java.util.List;

public interface JobService {

    JobDTO createJob(Long companyId, JobDTO jobDTO);

    Job saveJob(Job job);

    List<JobDTO> findAllJobs();

    JobDTO findJobById(Long companyId, Long id);

    JobDTO updateJob(Long companyId, Long id, JobDTO companyDTO);

    void deleteJobById(Long companyId, Long jobId);

    void applyToJobByUser(Long jobId, Long userId);

}
