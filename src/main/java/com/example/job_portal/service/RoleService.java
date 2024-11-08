package com.example.job_portal.service;


import com.example.job_portal.dto.RoleDTO;
import com.example.job_portal.entity.Role;

import java.util.List;

public interface RoleService {
    void save(Role role);

//    List<Role> findAllRoles();
    List<RoleDTO> findAllRoles();

//    RoleDTO createRole(String roleName);
    RoleDTO createRole(RoleDTO roleDTO);

    void updateUserRole(Long userId, String roleName);

    void deleteUserRole(Long userId, String roleName);

    void deleteRoleById(Long roleId);

    void addRoleToUser(Long userId, String role);

//    List<String> getUserRoles(Long id);
}
