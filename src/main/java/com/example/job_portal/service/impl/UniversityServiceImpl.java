package com.example.job_portal.service.impl;

import com.example.job_portal.dto.CompanyDTO;
import com.example.job_portal.dto.UniversityDTO;
import com.example.job_portal.entity.Profile;
import com.example.job_portal.entity.University;
import com.example.job_portal.exception.UniversityAlreadyExistsException;
import com.example.job_portal.exception.UniversityNotFoundException;
import com.example.job_portal.repository.ProfileRepository;
import com.example.job_portal.repository.UniversityRepository;
import com.example.job_portal.service.UniversityService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import com.example.job_portal.utils.SortEntityDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
    public List<UniversityDTO> getAllUniversitiesUnderApp() {

        List<UniversityDTO> universityDTOs =  EntityToEntityDTOConverter.convertUniversitiesToUniversitiesDTO(universityRepository.findAllUniversity());
        return SortEntityDTO.sortResponseDTO(universityDTOs, Comparator.comparing(UniversityDTO::getId).reversed());

    }

    @Override
    @Transactional
    public UniversityDTO createUniversity(UniversityDTO universityDTO) {

        Optional<University> university = universityRepository.findUniversityById(universityDTO.getId());

        if (universityDTO.getName() == null || universityDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("University name cannot be empty or whitespace-only");
        }

        if(university.isEmpty()) {
            University newUniversity = new University();

            if(universityDTO.getName() != null) newUniversity.setName(universityDTO.getName());
            if(universityDTO.getDegree() != null) newUniversity.setDegree(universityDTO.getDegree());
            if(universityDTO.getPassingYear() != null) newUniversity.setPassingYear(universityDTO.getPassingYear());

            return EntityToEntityDTOConverter.convertUniversityToUniversityDTO(universityRepository.save(newUniversity));
        }
        else {
            throw new UniversityAlreadyExistsException("University already exists!");
        }
    }

    @Override
    @Transactional
    public void deleteUniversity(Long universityId) {
        University university = universityRepository.findUniversityById(universityId).orElseThrow(
                () -> new UniversityNotFoundException(String.format("University with id: %d is not found!", universityId)));

        for(Profile profile: university.getProfiles()) {

            if(!profile.isDeleted()) {
                Iterator<University> iterator = profile.getUniversities().iterator();
                boolean modified = false;

                while(iterator.hasNext()) {
                    University userUniversity = iterator.next();
                    if(!userUniversity.isDeleted() && userUniversity.getId().equals(universityId)) {
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
