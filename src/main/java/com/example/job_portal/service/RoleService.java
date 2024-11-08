package com.example.job_portal.service;


import com.example.job_portal.dto.RoleDTO;
import com.example.job_portal.entity.Role;

import java.util.List;

public interface RoleService {
    void save(Role role);

    List<RoleDTO> findAllRoles();

    RoleDTO createRole(RoleDTO roleDTO);

    void deleteRoleById(Long roleId);

    void addRoleToUser(Long userId, String role);

}
