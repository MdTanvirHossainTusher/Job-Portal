package com.example.job_portal.service;


import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.entity.User;

import java.util.List;

public interface UserService {
    void createUser(UserDTO userDTO);

    User saveUser(User user);
//    void saveUser(User user);

    User findUserById(Long id);

//    User updateUser(User user);
    User updateUser(Long id, User user);

    List<User> findAll();

    void deleteUserById(Long id);

//    User SearchUserByEmail(String email);
}
