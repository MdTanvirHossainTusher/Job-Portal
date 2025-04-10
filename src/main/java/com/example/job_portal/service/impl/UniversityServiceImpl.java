package com.example.job_portal.service.impl;

import com.example.job_portal.dto.request.UniversityCreateRequestDTO;
import com.example.job_portal.dto.response.UniversityCreateResponseDTO;
import com.example.job_portal.entity.Profile;
import com.example.job_portal.entity.ProfileUniversity;
import com.example.job_portal.entity.University;
import com.example.job_portal.exception.ResourceAlreadyExistsException;
import com.example.job_portal.exception.ResourceNotFoundException;
import com.example.job_portal.repository.ProfileRepository;
import com.example.job_portal.repository.UniversityRepository;
import com.example.job_portal.service.UniversityService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService {
    private final UniversityRepository universityRepository;
    private final ProfileRepository profileRepository;

    public UniversityServiceImpl(UniversityRepository universityRepository, ProfileRepository profileRepository) {
        this.universityRepository = universityRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional
    public void save(University university) {
        universityRepository.save(university);
    }

    @Override
    public List<UniversityCreateResponseDTO> getAllUniversitiesUnderApp() {
        List<UniversityCreateResponseDTO> universityDTOs =  EntityToEntityDTOConverter.
                convertUniversitiesToUniversitiesResponseDTO(universityRepository.findAllUniversity());
        return universityDTOs;
//        return SortEntityDTO.sortResponseDTO(universityDTOs, Comparator.comparing(UniversityDTO::getId).reversed());
    }

//    @Override
//    @Transactional
//    public UniversityDTO createUniversity(UniversityDTO universityDTO) {
//        Optional<University> university = universityRepository.findUniversityById(universityDTO.getId());
//        if (universityDTO.getName() == null || universityDTO.getName().trim().isEmpty()) {
//            throw new IllegalArgumentException("University name cannot be empty or whitespace-only");
//        }
//        if(university.isEmpty()) {
//            University newUniversity = new University();
//            if(universityDTO.getName() != null) newUniversity.setName(universityDTO.getName());
//            if(universityDTO.getDegree() != null) newUniversity.setDegree(universityDTO.getDegree());
//            if(universityDTO.getPassingYear() != null) newUniversity.setPassingYear(universityDTO.getPassingYear());
//            return EntityToEntityDTOConverter.convertUniversityToUniversityDTO(universityRepository.save(newUniversity));
//        }
//        else {
//            throw new ResourceAlreadyExistsException("University already exists!");
//        }
//    }

    @Override
    @Transactional
    public UniversityCreateResponseDTO createUniversity(UniversityCreateRequestDTO universityCreateDTO) {
        if (universityCreateDTO.getName() == null || universityCreateDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("University name cannot be empty or whitespace-only");
        }
        if(universityRepository.findByName(universityCreateDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("University with this name already exists!");
        }
        University newUniversity = new University();
        newUniversity.setName(universityCreateDTO.getName().toLowerCase());
        return EntityToEntityDTOConverter.convertUniversityToUniversityResponseDTO(universityRepository.save(newUniversity));
    }

//    @Override
//    @Transactional
//    public void deleteUniversity(Long universityId) {
//        University university = universityRepository.findUniversityById(universityId).orElseThrow(
//                () -> new ResourceNotFoundException(String.format("University with id: %d is not found!", universityId)));
//        for( : university.getProfileUniversities()) {
//            if(!profile.isDeleted()) {
//                Iterator<ProfileUniversity> iterator = profile.getProfileUniversities().iterator();
//                boolean modified = false;
//
//                while(iterator.hasNext()) {
//                    University userUniversity = iterator.next();
//                    if(!userUniversity.isDeleted() && userUniversity.getId().equals(universityId)) {
//                        iterator.remove();
//                        modified = true;
//                    }
//                }
//                if(modified) {
//                    profileRepository.save(profile);
//                }
//            }
//        }
//        if(!university.isDeleted()) {
//            universityRepository.softDeleteUniversityById(universityId);
//        }
//    }

    @Override
    @Transactional
    public void deleteUniversity(Long universityId) {
        University university = universityRepository.findUniversityById(universityId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("University with id: %d is not found!", universityId)));

        for(ProfileUniversity profileUniversity : university.getProfileUniversities()) {
            Profile profile = profileUniversity.getProfile();

            if(!profile.isDeleted()) {
                Iterator<ProfileUniversity> iterator = profile.getProfileUniversities().iterator();
                boolean modified = false;

                while(iterator.hasNext()) {
                    ProfileUniversity pu = iterator.next();
                    if(pu.getUniversity().getId().equals(universityId) && !pu.getUniversity().isDeleted()) {
                        iterator.remove();
                        modified = true;
                    }
                }
                if(modified) {
                    profileRepository.save(profile);
                }
            }
        }
        if(!university.isDeleted()) {
            universityRepository.softDeleteUniversityById(universityId);
        }
    }

}
