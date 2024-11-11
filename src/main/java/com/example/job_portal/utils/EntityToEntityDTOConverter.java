package com.example.job_portal.utils;

import com.example.job_portal.dto.*;
import com.example.job_portal.entity.*;
import com.example.job_portal.repository.ProfileRepository;

import java.util.ArrayList;
import java.util.List;

public class EntityToEntityDTOConverter {

//    private static ProfileRepository profileRepository;
//
//    public EntityToEntityDTOConverter(ProfileRepository profileRepository) {
//        this.profileRepository = profileRepository;
//    }

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
                role.getRole()
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

    public static UniversityDTO convertUniversityToUniversityDTO(University university) {
        return new UniversityDTO(
                university.getName(),
                university.getDegree(),
                university.getPassingYear()
        );
    }

    public static List<UniversityDTO> convertUniversitiesToUniversitiesDTO(List<University> universities) {
        List<UniversityDTO> universityDTOList = new ArrayList<>();

        for (University university : universities) {
            universityDTOList.add(EntityToEntityDTOConverter.convertUniversityToUniversityDTO(university));
        }
        return universityDTOList;
    }


//    convertProfileToProfileDTO

//    private CVDTO cvdto;
//    private UserDTO userDTO;
//    private List<SkillDTO> skillDTOS;
//    private List<UniversityDTO> universityDTOS;
//    private List<JobDTO> jobDTOS;
//    private List<CompanyDTO> companyDTOS;

    public static ProfileDTO convertProfileToProfileDTO(Profile profile) {

        List<Job> jobs = new ArrayList<>();
        for(Job job: profile.getJobs()) {
            if(!job.isDeleted()) {
                jobs.add(job);
            }
        }
        return new ProfileDTO(
                EntityToEntityDTOConverter.convertCVToCVDTO(profile.getCv()),
                EntityToEntityDTOConverter.convertUserToUserDTO(profile.getUser()),
                EntityToEntityDTOConverter.convertSkillsToSkillsDTO(profile.getSkills()),
                EntityToEntityDTOConverter.convertUniversitiesToUniversitiesDTO(profile.getUniversities()),
                EntityToEntityDTOConverter.convertJobsToJobsDTO(jobs)
//                EntityToEntityDTOConverter.convertMyCompaniesToMyCompaniesDTO(profile.getMyCompanies())
        );
    }



    public static List<ProfileDTO> convertProfilesToProfilesDTO(List<Profile> profiles) {
        List<ProfileDTO> profileDTOList = new ArrayList<>();

        for (Profile profile : profiles) {
            profileDTOList.add(EntityToEntityDTOConverter.convertProfileToProfileDTO(profile));
        }
        return profileDTOList;
    }
}
