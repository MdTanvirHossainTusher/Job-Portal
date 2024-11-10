package com.example.job_portal.service.impl;

import com.example.job_portal.dto.JobApplicationDTO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.Job;
import com.example.job_portal.entity.Profile;
import com.example.job_portal.exception.CVNotFoundException;
import com.example.job_portal.exception.JobNotFoundException;
import com.example.job_portal.exception.UserNotFoundException;
import com.example.job_portal.repository.ProfileRepository;
import com.example.job_portal.service.ProfileService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
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
}
