package com.example.job_portal.service.impl;

import com.example.job_portal.dto.JobApplicationDTO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.dto.SkillDTO;
import com.example.job_portal.entity.CV;
import com.example.job_portal.entity.Job;
import com.example.job_portal.entity.Profile;
import com.example.job_portal.entity.Skill;
import com.example.job_portal.exception.*;
import com.example.job_portal.repository.ProfileRepository;
import com.example.job_portal.repository.SkillRepository;
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

    public ProfileServiceImpl(ProfileRepository profileRepository, SkillRepository skillRepository) {
        this.profileRepository = profileRepository;
        this.skillRepository = skillRepository;
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

        for(Job job: profile.getJobs()) {
            if(!job.getCompany().isDeleted() && !job.isDeleted()) {

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

        if(!profile.isDeleted()) {
            List<Skill> skills = new ArrayList<>();

            for (Skill skill: profile.getSkills()) {
                if(!skill.isDeleted()) {
                    skills.add(skill);
                }
            }
            return EntityToEntityDTOConverter.convertSkillsToSkillsDTO(skills);
        }
        else {
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

        if(!profile.isDeleted() && !skill.isDeleted()) {
            boolean skillExists = false;
            for(Skill existingSkill : profile.getSkills()) {
                if(existingSkill.getId().equals(skillId)) {
                    skillExists = true;
                    break;
                }
            }
            if(!skillExists) {
                profile.getSkills().add(skill);
                profileRepository.save(profile);
            }
            else {
                throw new SkillAlreadyExistsException("Skill already exists!");
            }
        }

    }

}
