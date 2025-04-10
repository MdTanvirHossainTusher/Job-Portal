package com.example.job_portal.service;


import com.example.job_portal.dto.request.UniversityCreateRequestDTO;
import com.example.job_portal.dto.response.UniversityCreateResponseDTO;
import com.example.job_portal.entity.University;

import java.util.List;

public interface UniversityService {
    void save(University university);
    List<UniversityCreateResponseDTO> getAllUniversitiesUnderApp();
//    UniversityDTO createUniversity(UniversityDTO universityDTO);
    UniversityCreateResponseDTO createUniversity(UniversityCreateRequestDTO universityCreateDTO);
    void deleteUniversity(Long universityId);
}
