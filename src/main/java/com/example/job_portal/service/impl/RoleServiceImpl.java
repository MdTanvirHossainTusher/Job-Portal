package com.example.job_portal.service.impl;

import com.example.job_portal.dto.RoleDTO;
import com.example.job_portal.entity.Role;
import com.example.job_portal.entity.User;
import com.example.job_portal.repository.RoleRepository;
import com.example.job_portal.repository.UserRepository;
import com.example.job_portal.service.RoleService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<RoleDTO> findAllRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDTO> roleDTOS = new ArrayList<>();

        for (Role role : roles) {
            roleDTOS.add(EntityToEntityDTOConverter.convertRoleToRoleDTO(role));
        }
        return roleDTOS;
    }

    @Override
    @Transactional
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role newRole = new Role();
        if(!roleRepository.existsByRole(roleDTO.getRoleName())) {
            String newRoleName = "ROLE_" + roleDTO.getRoleName().toUpperCase();
            newRole.setRole(newRoleName);
        }
        return EntityToEntityDTOConverter.convertRoleToRoleDTO(roleRepository.save(newRole));
    }

    @Override
    @Transactional
    public void deleteRoleById(Long roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    @Transactional
    public void addRoleToUser(Long userId, String role) {
        String newRoleName = "ROLE_" + role.toUpperCase();
        if(roleRepository.existsByRole(newRoleName)) {
            Optional<User> optionalUser = userRepository.findUserById(userId);
            User user = optionalUser.orElse(null);

            if(user != null) {
                Role existingRole = roleRepository.getByRole(newRoleName);

                if (!user.getRoles().contains(existingRole)) {
                    user.getRoles().add(existingRole);
                    userRepository.save(user);
                }
            }
        }
    }

    public Role findByRole(String roleName) {
        return roleRepository.getByRole(roleName);
    }
}
