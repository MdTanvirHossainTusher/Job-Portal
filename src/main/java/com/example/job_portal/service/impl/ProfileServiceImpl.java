package com.example.job_portal.service.impl;

import com.example.job_portal.entity.Profile;
import com.example.job_portal.repository.ProfileRepository;
import com.example.job_portal.service.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
