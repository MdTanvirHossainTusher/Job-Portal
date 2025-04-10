package com.example.job_portal.service.impl;

import com.example.job_portal.dto.*;
import com.example.job_portal.entity.*;
import com.example.job_portal.exception.*;
import com.example.job_portal.repository.CompanyRepository;
import com.example.job_portal.repository.ProfileRepository;
import com.example.job_portal.repository.SkillRepository;
import com.example.job_portal.repository.UniversityRepository;
import com.example.job_portal.service.ProfileService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import com.example.job_portal.utils.SortEntityDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final SkillRepository skillRepository;
    private final UniversityRepository universityRepository;
    private final CompanyRepository companyRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository,
                              SkillRepository skillRepository,
                              UniversityRepository universityRepository,
                              CompanyRepository companyRepository) {
        this.profileRepository = profileRepository;
        this.skillRepository = skillRepository;
        this.universityRepository = universityRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    @Transactional
    public void save(Profile profile) {
        profileRepository.save(profile);
    }

    @Override
    public List<JobApplicationDTO> getAllJobsUserAppliedOn(Long profileId) {
        Profile profile = profileRepository.findByIdAndIsDeletedFalse(profileId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Profile with id: %d is not found", profileId)));

        Long activeJobsCount = profile.getJobs().stream()
                .filter(job -> !job.isDeleted() && !job.getCompany().isDeleted())
                .count();

        List<JobApplicationDTO> jobApplicationDTOList = new ArrayList<>();

        for (Job job : profile.getJobs()) {
            if (!job.getCompany().isDeleted() && !job.isDeleted()) {

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

    @Override
    public List<SkillDTO> getAllSkillsUnderProfile(Long profileId) {
        Profile profile = profileRepository.findByIdAndIsDeletedFalse(profileId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Profile with id: %d is not found", profileId)));

        if (!profile.isDeleted()) {
            List<Skill> skills = new ArrayList<>();

            for (Skill skill : profile.getSkills()) {
                if (!skill.isDeleted()) {
                    skills.add(skill);
                }
            }
            List<SkillDTO> skillDTOs =  EntityToEntityDTOConverter.convertSkillsToSkillsDTO(skills);
            return SortEntityDTO.sortResponseDTO(skillDTOs, Comparator.comparing(SkillDTO::getId).reversed());

        } else {
            throw new RuntimeException("User didn't add any skill yet!");
        }
    }

    @Override
    @Transactional
    public void addSkillToUserProfile(Long profileId, Long skillId) {
        Profile profile = profileRepository.findByIdAndIsDeletedFalse(profileId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Profile with id: %d is not found", profileId)));

        Skill skill = skillRepository.findSkillById(skillId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Skill with id: %d is not found", skillId)));

        if (!profile.isDeleted() && !skill.isDeleted()) {
            boolean skillExists = false;
            for (Skill existingSkill : profile.getSkills()) {
                if (existingSkill.getId().equals(skillId)) {
                    skillExists = true;
                    break;
                }
            }
            if (!skillExists) {
                profile.getSkills().add(skill);
                profileRepository.save(profile);
            } else {
                throw new ResourceAlreadyExistsException("Skill already exists!");
            }
        }
    }

    @Override
    @Transactional
    public void removeSkillToUserProfile(Long profileId, Long skillId) {
        Profile profile = profileRepository.findByIdAndIsDeletedFalse(profileId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Profile with id: %d is not found", profileId)));

        if (!profile.isDeleted()) {
            boolean skillExists = false;
            for (Skill existingSkill : profile.getSkills()) {
                if (existingSkill.getId().equals(skillId)) {
                    skillExists = true;
                    profile.getSkills().remove(existingSkill);
                    profileRepository.save(profile);
                    break;
                }
            }
            if (!skillExists) {
                throw new ResourceNotFoundException("Skill not found!");
            }
        }
    }

//    @Override
//    public List<UniversityDTO> getAllUniversitiesUnderProfile(Long profileId) {
//        Profile profile = profileRepository.findByIdAndIsDeletedFalse(profileId).orElseThrow(
//                () -> new ResourceNotFoundException(String.format("Profile with id: %d is not found", profileId)));
//
//        if (!profile.isDeleted()) {
//            List<University> universityList = new ArrayList<>();
//
//            for (University university : profile.getUniversities()) {
//                if (!university.isDeleted()) {
//                    universityList.add(university);
//                }
//            }
//            List<UniversityDTO> universityDTOs = EntityToEntityDTOConverter.convertUniversitiesToUniversitiesDTO(universityList);
//            return SortEntityDTO.sortResponseDTO(universityDTOs, Comparator.comparing(UniversityDTO::getId).reversed());
//
//        } else {
//            throw new RuntimeException("User didn't add any university yet!");
//        }
//    }

    @Override
    public List<ProfileUniversityDTO> getAllUniversitiesUnderProfile(Long profileId) {
        Profile profile = profileRepository.findByIdAndIsDeletedFalse(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id: " + profileId));
        return EntityToEntityDTOConverter.convertProfileUniversityListToDTO(profile.getProfileUniversities());
    }

//    @Override
//    @Transactional
//    public void addUniversityToUserProfile(Long profileId, Long universityId) {
//        Profile profile = profileRepository.findByIdAndIsDeletedFalse(profileId).orElseThrow(
//                () -> new ResourceNotFoundException(String.format("Profile with id: %d is not found", profileId)));
//
//        University university = universityRepository.findUniversityById(universityId).orElseThrow(
//                () -> new ResourceNotFoundException(String.format("University with id: %d is not found", universityId)));
//
//        if (!profile.isDeleted() && !university.isDeleted()) {
//            boolean universityExists = false;
//
//            for (University profileUniversity : profile.getUniversities()) {
//                if (profileUniversity.getId().equals(universityId)) {
//                    universityExists = true;
//                    break;
//                }
//            }
//            if (!universityExists) {
//                profile.getUniversities().add(university);
//                profileRepository.save(profile);
//            } else {
//                throw new ResourceAlreadyExistsException("University already exists!");
//            }
//        }
//    }

//    @Override
//    @Transactional
//    public UniversityDTO updateUniversityToUserProfile(Long profileId, Long universityId, UniversityDTO universityDTO) {
//        Profile profile = profileRepository.findByIdAndIsDeletedFalse(profileId).orElseThrow(
//                () -> new ResourceNotFoundException(String.format("Profile with id: %d is not found", profileId)));
//        University university = universityRepository.findUniversityById(universityId).orElseThrow(
//                () -> new ResourceNotFoundException(String.format("University with id: %d is not found", universityId)));
//
//        if(universityDTO.getPassingYear() != null) university.setPassingYear(universityDTO.getPassingYear());
//        if(universityDTO.getDegree() != null) university.setDegree(universityDTO.getDegree());
//        if (!profile.getUniversities().contains(university)) {
//            profile.getUniversities().add(university);
//            profileRepository.save(profile);
//        }
//        return EntityToEntityDTOConverter.convertUniversityToUniversityDTO(universityRepository.save(university));
//    }
//
//    @Override
//    @Transactional
//    public void removeUniversityToUserProfile(Long profileId, Long universityId) {
//        Profile profile = profileRepository.findByIdAndIsDeletedFalse(profileId).orElseThrow(
//                () -> new ResourceNotFoundException(String.format("Profile with id: %d is not found", profileId)));
//
//        if (!profile.isDeleted()) {
//            boolean universityExists = false;
//            for (University profileUniversity : profile.getUniversities()) {
//                if (profileUniversity.getId().equals(universityId)) {
//                    universityExists = true;
//                    profile.getUniversities().remove(profileUniversity);
//                    profileRepository.save(profile);
//                    break;
//                }
//            }
//            if (!universityExists) {
//                throw new ResourceNotFoundException("University not found!");
//            }
//        }
//    }

    @Override
    @Transactional
//    public ProfileUniversityDTO addUniversityToUserProfile(Long profileId, Long universityId, String degree, String passingYear) {
    public ProfileUniversityDTO addUniversityToUserProfile(Long profileId, Long universityId) {
        Profile profile = profileRepository.findByIdAndIsDeletedFalse(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id: " + profileId));
        University university = universityRepository.findUniversityById(universityId)
                .orElseThrow(() -> new ResourceNotFoundException("University not found with id: " + universityId));

        boolean universityExists = profile.getProfileUniversities().stream()
                .anyMatch(pu -> pu.getUniversity().getId().equals(universityId));

        if (universityExists) {
            throw new ResourceAlreadyExistsException("This university is already added to your profile");
        }
//        profile.addUniversity(university, degree, passingYear);
        profile.addUniversity(university);
        Profile savedProfile = profileRepository.save(profile);

        ProfileUniversity addedUniversity = savedProfile.getProfileUniversities().stream()
                .filter(pu -> pu.getUniversity().getId().equals(universityId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error adding university to profile"));

        return EntityToEntityDTOConverter.convertProfileUniversityToDTO(addedUniversity);
    }

    @Override
    @Transactional
    public ProfileUniversityDTO updateUniversityToUserProfile(Long profileId, Long universityId, String degree,
                                                              String passingYear) {
        Profile profile = profileRepository.findByIdAndIsDeletedFalse(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id: " + profileId));

        ProfileUniversity profileUniversity = profile.getProfileUniversities().stream()
                .filter(pu -> pu.getUniversity().getId().equals(universityId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "University with id: " + universityId + " not found in profile with id: " + profileId));

        if (degree != null) profileUniversity.setDegree(degree);
        if (passingYear != null) profileUniversity.setPassingYear(passingYear);

        profileRepository.save(profile);
        return EntityToEntityDTOConverter.convertProfileUniversityToDTO(profileUniversity);
    }

    @Override
    @Transactional
    public void removeUniversityToUserProfile(Long profileId, Long universityId) {
        Profile profile = profileRepository.findByIdAndIsDeletedFalse(profileId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Profile with id: %d is not found", profileId)));

        if (!profile.isDeleted()) {
            boolean universityExists = false;
            for (ProfileUniversity pu : profile.getProfileUniversities()) {
                if (pu.getUniversity().getId().equals(universityId) && !pu.getUniversity().isDeleted()) {
                    universityExists = true;
                    profile.getProfileUniversities().remove(pu);
                    profileRepository.save(profile);
                    break;
                }
            }
            if (!universityExists) {
                throw new ResourceNotFoundException("University not found!");
            }
        }
    }

    @Override
    public List<CompanyDTO> getAllCompaniesUnderProfile(Long profileId) {
        Profile profile = profileRepository.findByIdAndIsDeletedFalse(profileId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Profile with id: %d is not found", profileId)));

        if (!profile.isDeleted()) {
            List<Company> companyList = new ArrayList<>();

            for (Company company : profile.getCompanies()) {
                if (!company.isDeleted()) {
                    companyList.add(company);
                }
            }
            List<CompanyDTO> companyDTOs = EntityToEntityDTOConverter.convertCompaniesToCompaniesDTO(companyList);
            return SortEntityDTO.sortResponseDTO(companyDTOs, Comparator.comparing(CompanyDTO::getId).reversed());
        } else {
            throw new RuntimeException("User didn't add any company yet!");
        }
    }

    @Override
    public void addCompanyToUserProfile(Long profileId, Long companyId) {
        Profile profile = profileRepository.findByIdAndIsDeletedFalse(profileId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Profile with id: %d is not found", profileId)));

        Company company = companyRepository.findByIdAndIsDeletedFalse(companyId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Company with id: %d is not found", companyId)));

        if (!profile.isDeleted() && !company.isDeleted()) {
            boolean companyExists = false;

            for (Company existingCompany : profile.getCompanies()) {
                if (existingCompany.getId().equals(companyId)) {
                    companyExists = true;
                    break;
                }
            }
            if (!companyExists) {
                profile.getCompanies().add(company);
                profileRepository.save(profile);
            } else {
                throw new ResourceAlreadyExistsException("Company already exists!");
            }
        }
    }

    @Override
    public void removeCompanyToUserProfile(Long profileId, Long companyId) {
        Profile profile = profileRepository.findByIdAndIsDeletedFalse(profileId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Profile with id: %d is not found", profileId)));

        if (!profile.isDeleted()) {
            boolean companyExists = false;
            for (Company existingCompany : profile.getCompanies()) {
                if (existingCompany.getId().equals(companyId)) {
                    companyExists = true;
                    profile.getCompanies().remove(existingCompany);
                    profileRepository.save(profile);
                    break;
                }
            }
            if (!companyExists) {
                throw new ResourceNotFoundException("Company not found!");
            }
        }
    }
}
