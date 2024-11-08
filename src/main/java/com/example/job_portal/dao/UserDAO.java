package com.example.job_portal.dao;

import com.example.job_portal.dto.UserDTO;

import java.util.List;

public interface UserDAO {

    List<UserDTO> filterUsers(String email, Double experience, String universityName);

}
