package com.example.job_portal.service.impl;

import com.example.job_portal.dto.*;
import com.example.job_portal.entity.*;
import com.example.job_portal.exception.*;
import com.example.job_portal.repository.ProfileRepository;
import com.example.job_portal.repository.SkillRepository;
import com.example.job_portal.repository.UniversityRepository;
import com.example.job_portal.service.ProfileService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final SkillRepository skillRepository;
    private final UniversityRepository universityRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository, SkillRepository skillRepository, UniversityRepository universityRepository) {
        this.profileRepository = profileRepository;
        this.skillRepository = skillRepository;
        this.universityRepository = universityRepository;
    }

    @Override
    @Transactional
    public void save(Profile profile) {
        profileRepository.save(profile);
    }

    @Override
    public List<JobApplicationDTO> getAllJobsUserAppliedOn(Long profileId) {
        Profile profile = profileRepository.findProfileById(profileId).orElseThrow(
                () -> new UserNotFoundException(String.format("Profile with id: %d is not found", profileId)));

        Long activeJobsCount = profile.getJobs().stream()
                .filter(job -> !job.isDeleted() && !job.getCompany().isDeleted())
                .count();

        List<JobApplicationDTO> jobApplicationDTOList = new ArrayList<>();

        for (Job job : profile.getJobs()) {
            if (!job.getCompany().isDeleted() && !job.isDeleted()) {

                JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();

                jobApplicationDTO.setJobId(job.getId());
                jobApplicationDTO.setJobTitle(job.getJobTitle());
                jobApplicationDTO.setJobDescription(job.getJobDescription());
                jobApplicationDTO.setSalary(job.getSalary());
                jobApplicationDTO.setJobPosition(job.getJobPosition());
                jobApplicationDTO.setJobLocation(job.getJobLocation());
                jobApplicationDTO.setCompany(EntityToEntityDTOConverter.convertCompanyToCompanyDTO(job.getCompany()));
                jobApplicationDTO.setTotalApplications(activeJobsCount);

                jobApplicationDTOList.add(jobApplicationDTO);
            }
        }
        return jobApplicationDTOList;
    }

    @Override
    public List<SkillDTO> getAllSkillsUnderProfile(Long profileId) {
        Profile profile = profileRepository.findProfileById(profileId).orElseThrow(
                () -> new UserNotFoundException(String.format("Profile with id: %d is not found", profileId)));

        if (!profile.isDeleted()) {
            List<Skill> skills = new ArrayList<>();

            for (Skill skill : profile.getSkills()) {
                if (!skill.isDeleted()) {
                    skills.add(skill);
                }
            }
            return EntityToEntityDTOConverter.convertSkillsToSkillsDTO(skills);
        } else {
            throw new RuntimeException("User didn't add any skill yet!");
        }
    }

    @Override
    @Transactional
    public void addSkillToUserProfile(Long profileId, Long skillId) {
        Profile profile = profileRepository.findProfileById(profileId).orElseThrow(
                () -> new UserNotFoundException(String.format("Profile with id: %d is not found", profileId)));

        Skill skill = skillRepository.findSkillById(skillId).orElseThrow(
                () -> new UserNotFoundException(String.format("Skill with id: %d is not found", skillId)));

        if (!profile.isDeleted() && !skill.isDeleted()) {
            boolean skillExists = false;
            for (Skill existingSkill : profile.getSkills()) {
                if (existingSkill.getId().equals(skillId)) {
                    skillExists = true;
                    break;
                }
            }
            if (!skillExists) {
                profile.getSkills().add(skill);
                profileRepository.save(profile);
            } else {
                throw new SkillAlreadyExistsException("Skill already exists!");
            }
        }
    }

    @Override
    @Transactional
    public void removeSkillToUserProfile(Long profileId, Long skillId) {
        Profile profile = profileRepository.findProfileById(profileId).orElseThrow(
                () -> new UserNotFoundException(String.format("Profile with id: %d is not found", profileId)));

        if (!profile.isDeleted()) {
            boolean skillExists = false;
            for (Skill existingSkill : profile.getSkills()) {
                if (existingSkill.getId().equals(skillId)) {
                    skillExists = true;
                    profile.getSkills().remove(existingSkill);
                    profileRepository.save(profile);
                    break;
                }
            }
            if (!skillExists) {
                throw new SkillAlreadyExistsException("Skill not found!");
            }
        }
    }




    @Override
    public List<UniversityDTO> getAllUniversitiesUnderProfile(Long profileId) {
        Profile profile = profileRepository.findProfileById(profileId).orElseThrow(
                () -> new UserNotFoundException(String.format("Profile with id: %d is not found", profileId)));

        if (!profile.isDeleted()) {
            List<University> universityList = new ArrayList<>();

            for (University university : profile.getUniversities()) {
                if (!university.isDeleted()) {
                    universityList.add(university);
                }
            }
            return EntityToEntityDTOConverter.convertUniversitiesToUniversitiesDTO(universityList);
        } else {
            throw new RuntimeException("User didn't add any university yet!");
        }
    }

    @Override
    @Transactional
    public void addUniversityToUserProfile(Long profileId, Long universityId) {
        Profile profile = profileRepository.findProfileById(profileId).orElseThrow(
                () -> new UserNotFoundException(String.format("Profile with id: %d is not found", profileId)));

        University university = universityRepository.findUniversityById(universityId).orElseThrow(
                () -> new UserNotFoundException(String.format("University with id: %d is not found", universityId)));

        if (!profile.isDeleted() && !university.isDeleted()) {
            boolean universityExists = false;

            for (University existingUniversity : profile.getUniversities()) {
                if (existingUniversity.getId().equals(universityId)) {
                    universityExists = true;
                    break;
                }
            }
            if (!universityExists) {
                profile.getUniversities().add(university);
                profileRepository.save(profile);
            } else {
                throw new UniversityAlreadyExistsException("University already exists!");
            }
        }
    }

    @Override
    @Transactional
    public void removeUniversityToUserProfile(Long profileId, Long universityId) {
        Profile profile = profileRepository.findProfileById(profileId).orElseThrow(
                () -> new UserNotFoundException(String.format("Profile with id: %d is not found", profileId)));

        if (!profile.isDeleted()) {
            boolean universityExists = false;
            for (University existingUniversity : profile.getUniversities()) {
                if (existingUniversity.getId().equals(universityId)) {
                    universityExists = true;
                    profile.getUniversities().remove(existingUniversity);
                    profileRepository.save(profile);
                    break;
                }
            }
            if (!universityExists) {
                throw new UniversityAlreadyExistsException("University not found!");
            }
        }
    }

    @Override
    public CompanyDTO addCompanyToProfileAsMyCompany(Long profileId) {
        return null;
    }


}
