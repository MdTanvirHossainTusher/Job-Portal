package com.example.job_portal.dao;

import com.example.job_portal.entity.Job;

import java.util.List;

public interface JobDAO {
    List<Job> filterJobByJobLocation(String jobLocation);
    List<Job> filterJobByJobPosition(String jobPosition);
}
