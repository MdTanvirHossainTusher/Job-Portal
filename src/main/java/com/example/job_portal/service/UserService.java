package com.example.job_portal.service;


import com.example.job_portal.dto.ProfileDTO;
import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.dto.response.UserResponse;
import com.example.job_portal.entity.User;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    User saveUser(User user);

    UserDTO findUserById(Long id);

    UserDTO updateUser(Long id, UserDTO userDTO);

//    List<UserDTO> findAllUser();
//    List<UserDTO> findAllUser(int pageNumber, int pageSize);
    UserResponse findAllUser(int pageNumber, int pageSize, String sortBy);

    void deleteUserById(Long id);

    List<String> getUserRoles(Long id);

    void deleteUserRole(Long userId, String roleName);

    ProfileDTO getUserProfile(Long userId);

}
