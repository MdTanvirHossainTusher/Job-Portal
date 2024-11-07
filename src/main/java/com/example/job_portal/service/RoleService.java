package com.example.job_portal.service;


import com.example.job_portal.entity.Role;

import java.util.List;

public interface RoleService {
    void save(Role role);

    List<Role> findAllRoles();

    void createRole(String roleName);

    void updateUserRole(Long userId, String roleName);

    void deleteUserRole(Long userId, String roleName);

    void deleteById(Long id);

    List<String> getUserRoles(Long id);
}
