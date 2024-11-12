package com.example.job_portal.service;

import com.example.job_portal.dto.*;
import com.example.job_portal.entity.Profile;

import java.util.List;

public interface ProfileService {
    void save(Profile profile);

    List<JobApplicationDTO> getAllJobsUserAppliedOn(Long profileId);

    List<SkillDTO> getAllSkillsUnderProfile(Long profileId);

    void addSkillToUserProfile(Long profileId, Long skillId);

    void removeSkillToUserProfile(Long profileId, Long skillId);

    List<UniversityDTO> getAllUniversitiesUnderProfile(Long profileId);

    void addUniversityToUserProfile(Long profileId, Long skillId);

    void removeUniversityToUserProfile(Long profileId, Long skillId);

//    CompanyDTO addCompanyToProfileAsMyCompany(Long profileId);
}
