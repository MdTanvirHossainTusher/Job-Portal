package com.example.job_portal.service.impl;

import com.example.job_portal.entity.University;
import com.example.job_portal.repository.UniversityRepository;
import com.example.job_portal.service.UniversityService;
import org.springframework.stereotype.Service;

@Service
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;

    public UniversityServiceImpl(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Override
    public void save(University university) {
        universityRepository.save(university);
    }
}
