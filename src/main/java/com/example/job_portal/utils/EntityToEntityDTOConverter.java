package com.example.job_portal.utils;

import com.example.job_portal.dto.RoleDTO;
import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.entity.Role;
import com.example.job_portal.entity.User;

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
}
