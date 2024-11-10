package com.example.job_portal.service.impl;

import com.example.job_portal.dto.CVDTO;
import com.example.job_portal.entity.CV;
import com.example.job_portal.entity.Job;
import com.example.job_portal.entity.Profile;
import com.example.job_portal.entity.User;
import com.example.job_portal.exception.*;
import com.example.job_portal.repository.CVRepository;
import com.example.job_portal.repository.JobRepository;
import com.example.job_portal.repository.ProfileRepository;
import com.example.job_portal.service.CVService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CVServiceImpl implements CVService {

    private final CVRepository cvRepository;
    private final ProfileRepository profileRepository;
    private final JobRepository jobRepository;

    public CVServiceImpl(CVRepository cvRepository, ProfileRepository profileRepository, JobRepository jobRepository) {
        this.cvRepository = cvRepository;
        this.profileRepository = profileRepository;
        this.jobRepository = jobRepository;
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

    @Override
//    public CVDTO updateCV(Long cvId, Long profileId, CVDTO cvDTO) {
    public CVDTO updateCV(Long profileId, CVDTO cvDTO) {

        Profile profile = profileRepository.findProfileById(profileId).orElseThrow(
                () -> new UserNotFoundException(String.format("Profile with id: %d is not found", profileId)));

//        CVDTO cvdto = new CVDTO();

        if(profile.getCv() != null) {
            CV cv = profile.getCv();

//            cv.setId(cvDTO.getCvId());
            cv.setCvFormat(cvDTO.getCvFormat());
            cv.setCvSize(cvDTO.getCvSize());
            cv.setCvUrl(cvDTO.getCvUrl());

            return EntityToEntityDTOConverter.convertCVToCVDTO(cvRepository.save(cv));
        }
        else {
            throw new CVNotFoundException(String.format("CV not found for the profile id: %d", profileId));
        }
//        return cvdto;
    }

    @Override
    public List<CVDTO> getAllCVs() {
        return EntityToEntityDTOConverter.convertCVsToCVsDTO(cvRepository.findAll());
    }

    @Override
    public CVDTO getUserCV(Long profileId) {
        Profile profile = profileRepository.findProfileById(profileId).orElseThrow(
                () -> new UserNotFoundException(String.format("Profile with id: %d is not found", profileId)));

        if(profile.getCv() != null) {
            return EntityToEntityDTOConverter.convertCVToCVDTO(profile.getCv());
        }
        else {
            throw new CVNotFoundException("CV is not attached to this profile!");
        }
    }

}
