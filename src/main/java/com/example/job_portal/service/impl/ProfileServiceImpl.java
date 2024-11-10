package com.example.job_portal.service.impl;

import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.Job;
import com.example.job_portal.entity.Profile;
import com.example.job_portal.exception.CVNotFoundException;
import com.example.job_portal.exception.UserNotFoundException;
import com.example.job_portal.repository.ProfileRepository;
import com.example.job_portal.service.ProfileService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<JobDTO> getAllJobsUserAppliedOn(Long profileId) {
        Profile profile = profileRepository.findProfileById(profileId).orElseThrow(
                () -> new UserNotFoundException(String.format("Profile with id: %d is not found", profileId)));

//        if(profile != null) {
            return EntityToEntityDTOConverter.convertJobsToJobsDTO(profile.getJobs());
//        }
//        else {
//            throw new CVNotFoundException("CV is not attached to this profile!");
//        }
    }
}
