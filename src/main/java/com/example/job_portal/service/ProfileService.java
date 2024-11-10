package com.example.job_portal.service;

import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.Profile;

import java.util.List;

public interface ProfileService {
    void save(Profile profile);

    List<JobDTO> getAllJobsUserAppliedOn(Long profileId);
}
