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

//    List<UniversityDTO> getAllUniversitiesUnderProfile(Long universityId);
    List<ProfileUniversityDTO> getAllUniversitiesUnderProfile(Long universityId);

//    void addUniversityToUserProfile(Long profileId, Long universityId);
//    ProfileUniversityDTO  addUniversityToUserProfile(Long profileId, Long universityId, String degree, String passingYear);
    ProfileUniversityDTO  addUniversityToUserProfile(Long profileId, Long universityId);

    void removeUniversityToUserProfile(Long profileId, Long universityId);

    List<CompanyDTO> getAllCompaniesUnderProfile(Long profileId);

    void addCompanyToUserProfile(Long profileId, Long companyId);

    void removeCompanyToUserProfile(Long profileId, Long companyId);

//    UniversityDTO updateUniversityToUserProfile(Long profileId, Long universityId, UniversityDTO universityDTO);
    ProfileUniversityDTO updateUniversityToUserProfile(Long profileId, Long universityId, String degree, String passingYear);

}
