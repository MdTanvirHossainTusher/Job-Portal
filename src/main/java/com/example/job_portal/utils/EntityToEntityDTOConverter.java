package com.example.job_portal.utils;

import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.entity.User;

import java.util.ArrayList;
import java.util.List;

public class EntityToEntityDTOConverter {

    public static UserDTO convertUserToUserDTO(User user) {
        return new UserDTO(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getImageUrl(),
                user.getTotalExperience()
        );
    }

    public static List<UserDTO> convertUsersToUsersDTO(List<User> userList) {

        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : userList) {
            userDTOs.add(EntityToEntityDTOConverter.convertUserToUserDTO(user));
        }
        return userDTOs;
    }
}
