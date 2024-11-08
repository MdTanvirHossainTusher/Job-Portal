package com.example.job_portal.service;


import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.entity.User;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    User saveUser(User user);

    UserDTO findUserById(Long id);

    UserDTO updateUser(Long id, UserDTO userDTO);

    List<UserDTO> findAll();

    void deleteUserById(Long id);

    List<String> getUserRoles(Long id);

    void deleteUserRole(Long userId, String roleName);

}
