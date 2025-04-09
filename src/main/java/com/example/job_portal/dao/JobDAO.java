package com.example.job_portal.dao;

import com.example.job_portal.dto.JobDTO;

import java.util.List;

public interface JobDAO {
    List<JobDTO> filterJobs(String jobPosition, String jobLocation);
}
