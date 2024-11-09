package com.example.job_portal.utils;

import com.example.job_portal.dto.*;
import com.example.job_portal.entity.*;

import java.util.ArrayList;
import java.util.List;

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
//                cv.getProfile()
        );
    }

    public static List<CVDTO> convertCVsToCVsDTO(List<CV> cvList) {
        List<CVDTO> cvDTOs = new ArrayList<>();

        for (CV cv : cvList) {
            cvDTOs.add(EntityToEntityDTOConverter.convertCVToCVDTO(cv));
        }
        return cvDTOs;
    }
}
