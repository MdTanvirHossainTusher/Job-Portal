package com.example.job_portal.service;

import com.example.job_portal.dto.JobApplicationDTO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.dto.SkillDTO;
import com.example.job_portal.entity.Profile;

import java.util.List;

public interface ProfileService {
    void save(Profile profile);

    List<JobApplicationDTO> getAllJobsUserAppliedOn(Long profileId);

    List<SkillDTO> getAllSkillsUnderProfile(Long profileId);

    void addSkillToUserProfile(Long profileId, Long skillId);

    void removeSkillToUserProfile(Long profileId, Long skillId);
}
