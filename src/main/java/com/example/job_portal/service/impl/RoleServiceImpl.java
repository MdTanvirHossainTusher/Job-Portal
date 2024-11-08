package com.example.job_portal.service.impl;

import com.example.job_portal.dto.RoleDTO;
import com.example.job_portal.dto.UserDTO;
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
//        List<User> users = userRepository.findByIsDeletedFalse();
        List<RoleDTO> roleDTOS = new ArrayList<>();

        for (Role role : roles) {
            roleDTOS.add(EntityToEntityDTOConverter.convertRoleToRoleDTO(role));
        }
        return roleDTOS;
//        return roleRepository.findAll();
    }

    @Override
    @Transactional
//    public RoleDTO createRole(String roleName) {
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role newRole = new Role();
        if(!roleRepository.existsByRole(roleDTO.getRoleName())) {
//            System.out.println(roleDTO.getRoleName() + " ssssseer ");
//
            String newRoleName = "ROLE_" + roleDTO.getRoleName().toUpperCase();

//            System.out.println(newRoleName + " nnnnnnnnnnnnnnnnnnn ");

            newRole.setRole(newRoleName);
        }
        return EntityToEntityDTOConverter.convertRoleToRoleDTO(roleRepository.save(newRole));
//        return newRole;
    }

    @Override
    @Transactional
    public void updateUserRole(Long userId, String roleName) {

        Optional<User> userOptional = userRepository.findUserById(userId);
        User user = userOptional.orElse(null);

        if(user != null) {
            for(Role role: user.getRoles()) {
                if(!role.getRole().contains(roleName.toUpperCase())) {
                    String newRoleName = "ROLE_" + roleName.toUpperCase();

                    Role existingRole = roleRepository.getByRole(newRoleName);
                    List<Role> roles = user.getRoles();
                    roles.add(existingRole);
                    user.setRoles(roles);
                    userRepository.save(user);
                }
            }
        }
    }

    @Override
    public void deleteUserRole(Long userId, String roleName) {
        Optional<User> userOptional = userRepository.findUserById(userId);
        User user = userOptional.orElse(null);

        if(user != null) {
            for(Role role: user.getRoles()) {
                if(role.getRole().contains(roleName.toLowerCase())) {
                    user.getRoles().remove(role);
                }
            }
            userRepository.save(user);
        }

    }

    @Override
    public void deleteById(Long roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    public List<String> getUserRoles(Long userId) {
//        return userRepository.getRoleByUserId(userId);
//        return roleRepository.getRoleByUserId(userId);
        Optional<User> userOptional = userRepository.findUserById(userId);
        User user = userOptional.orElse(null);

        List<String> roles = new ArrayList<>();

        if(user != null) {
            for(Role role: user.getRoles()) {
//                Long roleId = role.getId();
//                roles.add()
//                if(role.getId()) {
//                    user.getRoles().remove(role);
//                }
                roles.add(role.getRole());
            }
//            userRepository.save(user);
        }
        return roles;
    }

    public Role findByRole(String roleName) {
        return roleRepository.getByRole(roleName);
    }
}
