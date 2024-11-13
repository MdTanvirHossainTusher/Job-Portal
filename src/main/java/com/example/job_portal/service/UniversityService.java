package com.example.job_portal.service;


import com.example.job_portal.dto.UniversityDTO;
import com.example.job_portal.entity.University;

import java.util.List;

public interface UniversityService {

    void save(University university);

    List<UniversityDTO> getAllUniversitiesUnderApp();

    UniversityDTO createUniversity(UniversityDTO universityDTO);

    void deleteUniversity(Long universityId);

}
