package com.example.job_portal.utils;

import com.example.job_portal.dto.*;
import com.example.job_portal.dto.response.UniversityCreateResponseDTO;
import com.example.job_portal.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EntityToEntityDTOConverter {

    public static UserDTO convertUserToUserDTO(User user) {
        return new UserDTO(

                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getImageUrl(),
                user.getTotalExperience(),
                user.isDeleted()
        );
    }

    public static List<UserDTO> convertUsersToUsersDTO(List<User> userList) {

        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : userList) {
            userDTOs.add(EntityToEntityDTOConverter.convertUserToUserDTO(user));
        }
        return userDTOs;
    }

    public static RoleDTO convertRoleToRoleDTO(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getRole(),
                role.isDeleted()
        );
    }

    public static List<RoleDTO> convertRolesToRoleDTO(List<Role> roleList) {
        List<RoleDTO> roleDTOs = new ArrayList<>();

        for (Role role : roleList) {
            roleDTOs.add(EntityToEntityDTOConverter.convertRoleToRoleDTO(role));
        }
        return roleDTOs;
    }

    public static CompanyDTO convertCompanyToCompanyDTO(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getCompanyName(),
                company.getCompanyLocation(),
                company.getCompanyType(),
                company.getWorkingMode(),
                company.isDeleted()
        );
    }

    public static List<CompanyDTO> convertCompaniesToCompaniesDTO(List<Company> companyList) {
        List<CompanyDTO> companyDTOS = new ArrayList<>();

        for (Company company : companyList) {
            companyDTOS.add(EntityToEntityDTOConverter.convertCompanyToCompanyDTO(company));
        }
        return companyDTOS;
    }

    public static JobDTO convertJobToJobDTO(Job job) {
        return new JobDTO(
                job.getId(),
                job.getJobTitle(),
                job.getJobDescription(),
                job.getSalary(),
                job.getJobPosition(),
                job.getJobLocation(),
                job.isDeleted()
        );
    }

    public static List<JobDTO> convertJobsToJobsDTO(List<Job> jobList) {
        List<JobDTO> jobDTOs = new ArrayList<>();

        for (Job job : jobList) {
            jobDTOs.add(EntityToEntityDTOConverter.convertJobToJobDTO(job));
        }
        return jobDTOs;
    }

    public static CVDTO convertCVToCVDTO(CV cv) {
        return new CVDTO(
                cv.getId(),
                cv.getCvFormat(),
                cv.getCvSize(),
                cv.getCvUrl()
        );
    }

    public static List<CVDTO> convertCVsToCVsDTO(List<CV> cvList) {
        List<CVDTO> cvDTOs = new ArrayList<>();

        for (CV cv : cvList) {
            cvDTOs.add(EntityToEntityDTOConverter.convertCVToCVDTO(cv));
        }
        return cvDTOs;
    }

    public static SkillDTO convertSkillToSkillDTO(Skill skill) {
        return new SkillDTO(
                skill.getId(),
                skill.getSkillName()
        );
    }

    public static List<SkillDTO> convertSkillsToSkillsDTO(List<Skill> skills) {
        List<SkillDTO> skillDTOS = new ArrayList<>();

        for (Skill skill : skills) {
            skillDTOS.add(EntityToEntityDTOConverter.convertSkillToSkillDTO(skill));
        }
        return skillDTOS;
    }

//    public static UniversityDTO convertUniversityToUniversityDTO(University university) {
//        return new UniversityDTO(
//                university.getId(),
//                university.getName(),
//                university.getDegree(),
//                university.getPassingYear()
//        );
//    }

    public static UniversityCreateResponseDTO convertUniversityToUniversityResponseDTO(University university) {
        return new UniversityCreateResponseDTO(
                university.getId(),
                university.getName()
        );
    }

    public static List<UniversityDTO> convertUniversitiesToUniversitiesDTO(List<University> universities) {
        List<UniversityDTO> universityDTOList = new ArrayList<>();
        for (University university : universities) {
            universityDTOList.add(EntityToEntityDTOConverter.convertUniversityToUniversityDTO(university));
        }
        return universityDTOList;
    }

    public static List<UniversityCreateResponseDTO> convertUniversitiesToUniversitiesResponseDTO(List<University> universities) {
        List<UniversityCreateResponseDTO> universityDTOList = new ArrayList<>();
        for (University university : universities) {
            universityDTOList.add(EntityToEntityDTOConverter.convertUniversityToUniversityResponseDTO(university));
        }
        return universityDTOList;
    }

    public static ProfileDTO convertProfileToProfileDTO(Profile profile) {
        CVDTO cvDTO = profile.getCv() != null ?
                EntityToEntityDTOConverter.convertCVToCVDTO(profile.getCv()) : null;
        return new ProfileDTO(
                cvDTO,
                EntityToEntityDTOConverter.convertUserToUserDTO(profile.getUser()),
                EntityToEntityDTOConverter.convertSkillsToSkillsDTO(profile.getSkills()),
//                EntityToEntityDTOConverter.convertUniversitiesToUniversitiesDTO(profile.getUniversities()),
//                EntityToEntityDTOConverter.convertUniversitiesToUniversitiesDTO(profile.getProfileUniversities()),
                EntityToEntityDTOConverter.convertCompaniesToCompaniesDTO(profile.getCompanies()),
                EntityToEntityDTOConverter.convertProfileUniversityListToDTO(profile.getProfileUniversities())
        );
    }

    public static List<ProfileDTO> convertProfilesToProfilesDTO(List<Profile> profiles) {
        List<ProfileDTO> profileDTOList = new ArrayList<>();

        for (Profile profile : profiles) {
            profileDTOList.add(EntityToEntityDTOConverter.convertProfileToProfileDTO(profile));
        }
        return profileDTOList;
    }

    public static UniversityDTO convertUniversityToUniversityDTO(University university) {
        return new UniversityDTO(
                university.getId(),
                university.getName()
        );
    }

    public static ProfileUniversityDTO convertProfileUniversityToDTO(ProfileUniversity profileUniversity) {
        return new ProfileUniversityDTO(
                profileUniversity.getUniversity().getId(),
                profileUniversity.getUniversity().getName(),
                profileUniversity.getDegree(),
                profileUniversity.getPassingYear()
        );
    }

    public static List<ProfileUniversityDTO> convertProfileUniversityListToDTO(List<ProfileUniversity> profileUniversities) {
        return profileUniversities.stream()
                .map(EntityToEntityDTOConverter::convertProfileUniversityToDTO)
                .collect(Collectors.toList());
    }
}
