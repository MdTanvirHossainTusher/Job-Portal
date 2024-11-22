package com.example.job_portal.service.impl;

import com.example.job_portal.dto.ProfileDTO;
import com.example.job_portal.dto.UniversityDTO;
import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.dto.response.UserResponse;
import com.example.job_portal.entity.*;
import com.example.job_portal.exception.UserAlreadyExistsException;
import com.example.job_portal.exception.UserNotFoundException;
import com.example.job_portal.repository.ProfileRepository;
import com.example.job_portal.repository.RoleRepository;
import com.example.job_portal.repository.UserRepository;
import com.example.job_portal.service.UserService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import com.example.job_portal.utils.SortEntityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ProfileRepository profileRepository;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository, ProfileRepository profileRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("User has already exists!");
        }
        User newUser = new User();
        newUser.setId(userDTO.getId());
        newUser.setName(userDTO.getName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setImageUrl(userDTO.getProfileImageUrl());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setTotalExperience(userDTO.getTotalExperience());

        Role role = roleRepository.getByRole("ROLE_USER");

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        newUser.setRoles(roles);
        newUser.setProfile(new Profile());

        return EntityToEntityDTOConverter.convertUserToUserDTO(userRepository.save(newUser));
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDTO findUserById(Long id) {
        Optional<User> userOptional = userRepository.findUserById(id);
        User user = userOptional.orElse(null);
        return user != null ? EntityToEntityDTOConverter.convertUserToUserDTO(user) : null;
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {

        User existingUser = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id: %d is not found!", id)));

        if(userDTO.getName() != null) {
            existingUser.setName(userDTO.getName());
        }
        if(userDTO.getProfileImageUrl() != null) existingUser.setImageUrl(userDTO.getProfileImageUrl());
        if(userDTO.getTotalExperience() != null) existingUser.setTotalExperience(userDTO.getTotalExperience());

        User savedUser = userRepository.save(existingUser);
        return EntityToEntityDTOConverter.convertUserToUserDTO(savedUser);
    }


    @Override
//    public List<UserDTO> findAllUser(int pageNumber, int pageSize) {
    public UserResponse findAllUser(int pageNumber, int pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());

        Page<User> userPage = userRepository.findByIsDeletedFalse(pageable);

        List<User> users = userPage.getContent();

//        List<User> users = userRepository.findAllUser();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            if(!user.isDeleted()) {
                userDTOs.add(EntityToEntityDTOConverter.convertUserToUserDTO(user));
            }
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(userDTOs);
        userResponse.setPageNumber(userPage.getNumber());
        userResponse.setPageSize(userPage.getSize());
        userResponse.setTotalElements(userPage.getTotalElements());
        userResponse.setTotalPages(userPage.getTotalPages());
        userResponse.setLastPage(userPage.isLast());

//        return SortEntityDTO.sortResponseDTO(userDTOs, Comparator.comparing(UserDTO::getId).reversed());
        return userResponse;
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {

        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with id: %d is not found!", id)
                ));

        try {
            if (user.getProfile() != null && !user.getProfile().isDeleted()) {
                user.getProfile().setDeleted(true);
            }

            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                user.getRoles().clear();
            }
            userRepository.softDeleteUserById(id);
            userRepository.save(user);

        } catch (Exception e) {
            throw new RuntimeException("Error occurred while deleting user: " + e.getMessage());
        }
    }


    @Override
    public List<String> getUserRoles(Long userId) {
        Optional<User> userOptional = userRepository.findUserById(userId);
        User user = userOptional.orElse(null);

        List<String> roles = new ArrayList<>();

        if(user != null) {
            for(Role role: user.getRoles()) {
                if(!role.isDeleted()) roles.add(role.getRole());
                else throw new RuntimeException("Role doesn't exists!");
            }
        }
        else {
            throw new UserNotFoundException(String.format("User with id: %d is not found!", userId));
        }

        Collections.sort(roles);
        return roles;
    }


    @Override
    public void deleteUserRole(Long userId, String roleName) {
        Optional<User> userOptional = userRepository.findUserById(userId);
        User user = userOptional.orElse(null);
        boolean roleExists = false;

        if(user != null) {
            for(Role role: user.getRoles()) {
                String newRoleName = "ROLE_" + roleName.toUpperCase();

                if(role.getRole().equals(newRoleName)) {
                    if(!roleName.contains("user")) {
                        roleExists = true;
                        user.getRoles().remove(role);
                        userRepository.save(user);
                    }
                    else {
                        throw new RuntimeException(String.format("Role: '%s' can't be deleted!", roleName));
                    }
                }
            }
            if(!roleExists) {
                throw new RuntimeException(String.format("User with id: %d doesn't have role: %s", userId, roleName));
            }
        }
        else {
            throw new UserNotFoundException(String.format("User with id: %d is not found!", userId));
        }
    }

    @Override
    public ProfileDTO getUserProfile(Long userId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with id: %d is not found!", userId)));

        return EntityToEntityDTOConverter.convertProfileToProfileDTO(user.getProfile());
    }

}
