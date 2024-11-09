package com.example.job_portal.service.impl;

import com.example.job_portal.dto.CVDTO;
import com.example.job_portal.entity.CV;
import com.example.job_portal.entity.Profile;
import com.example.job_portal.exception.ProfileNotFoundException;
import com.example.job_portal.repository.CVRepository;
import com.example.job_portal.repository.ProfileRepository;
import com.example.job_portal.service.CVService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class CVServiceImpl implements CVService {

    private final CVRepository cvRepository;
    private final ProfileRepository profileRepository;

    public CVServiceImpl(CVRepository cvRepository, ProfileRepository profileRepository) {
        this.cvRepository = cvRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional
    public void save(CV cv) {
        cvRepository.save(cv);
    }

    @Override
    public CVDTO createCV(Long profileId, CVDTO cvDTO) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        String.format("Profile with id: %d not found", profileId)));

        CV newCV = new CV();
        newCV.setCvFormat(cvDTO.getCvFormat());
        newCV.setCvSize(cvDTO.getCvSize());
        newCV.setCvUrl(cvDTO.getCvUrl());
        newCV.setJobs(new ArrayList<>());
        newCV.setProfile(profile);

        CV savedCV = cvRepository.save(newCV);

        profile.setCv(savedCV);
        profileRepository.save(profile);

        return EntityToEntityDTOConverter.convertCVToCVDTO(newCV);

    }
}
