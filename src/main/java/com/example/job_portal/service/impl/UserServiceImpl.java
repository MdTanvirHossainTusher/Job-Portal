package com.example.job_portal.service.impl;

import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.entity.Profile;
import com.example.job_portal.entity.Role;
import com.example.job_portal.entity.User;
import com.example.job_portal.exception.UserAlreadyExistsException;
import com.example.job_portal.exception.UserNotFoundException;
import com.example.job_portal.repository.RoleRepository;
import com.example.job_portal.repository.UserRepository;
import com.example.job_portal.service.UserService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
//    private final UserDTO userDTO;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository
//                           UserDTO userDTO
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
//        this.userDTO = userDTO;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        if(userRepository.existsByEmail(userDTO.getEmail())) {
//        if(userRepository.existsByEmailAndIsDeletedFalse(userDTO.getEmail())) {
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
//        Optional<User> userOptional = userRepository.findByIdAndIsDeletedFalse(id);
        User user = userOptional.orElse(null);
//        User user = userRepository.findById(id);
        return user != null ? EntityToEntityDTOConverter.convertUserToUserDTO(user) : null;
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {

        User existingUser = userRepository.findUserById(id)
//        User existingUser = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found!"));

        if(userDTO.getName() != null) {
            existingUser.setName(userDTO.getName());
        }
        if(userDTO.getProfileImageUrl() != null) existingUser.setImageUrl(userDTO.getProfileImageUrl());
//        if(userDTO.getPassword() != null) {
//            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        }

        if(userDTO.getTotalExperience() != null) existingUser.setTotalExperience(userDTO.getTotalExperience());
//        if(userDTO.getEmail() != null) existingUser.setEmail(userDTO.getEmail());
//        if(!userDTO.isDeleted()) existingUser.setDeleted(true);

        User savedUser = userRepository.save(existingUser);
        return EntityToEntityDTOConverter.convertUserToUserDTO(savedUser);
    }


    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAllUser();
//        List<User> users = userRepository.findByIsDeletedFalse();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            if(!user.isDeleted()) {
                userDTOs.add(EntityToEntityDTOConverter.convertUserToUserDTO(user));
            }
        }
        return userDTOs;
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        Optional<User> userOptional = userRepository.findUserById(id);
//        Optional<User> userOptional = userRepository.findByIdAndIsDeletedFalse(id);
        User user = userOptional.orElse(null);

        if (user != null) {
            userRepository.softDeleteUserById(id);
//            userRepository.deleteByIdAndIsDeletedFalse(id);
        } else {
            throw new UserNotFoundException("User with the id: " + id + " is not found!");
        }
    }


//    @Override
    public List<String> getUserRoles(Long userId) {
//        return userRepository.getRoleByUserId(userId);
//        return roleRepository.getRoleByUserId(userId);
        Optional<User> userOptional = userRepository.findUserById(userId);
        User user = userOptional.orElse(null);

        List<String> roles = new ArrayList<>();
        roles.clear();

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



//    @Override
//    @Transactional
//    public void deleteUserById(Long id) {
////        UserDTO userDTO = findUserById(id);
//
////        Optional<User> user = userRepository.findById(id);
//
//        Optional<User> userOptional = userRepository.findUserById(id);
//        User user = userOptional.orElse(null);
//
////        User user = new User();
//        try {
//            System.out.println(" --");
//
//            if(user != null) {
////                userRepository.deleteById(id);
//                user.setDeleted(true);
////                saveUser(user);
////                updateUser(user.getId(), user);
//
//                System.out.println(" -vvvv");
////
//            }
////            userRepository.save(user);
//        } catch (Exception ex) {
//            throw new UserNotFoundException("User with the id: " + id + " is not found!");
//        }
//    }
//
}
