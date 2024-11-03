package com.example.job_portal.service;


import com.example.job_portal.entity.User;

public interface UserService {
    User save(User user);

    User findUserById(Long id);
}
