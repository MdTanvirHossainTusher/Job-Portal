package com.example.job_portal.service.impl;

import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.entity.User;
import com.example.job_portal.exception.UserAlreadyExistsException;
import com.example.job_portal.exception.UserNotFoundException;
import com.example.job_portal.repository.UserRepository;
import com.example.job_portal.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDTO userDTO) {

//        userRepository.existsByEmailIgnoreCase(userDTO.getEmail());
        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("User has already exists!");
        }
        User newUser = new User();
        newUser.setName(userDTO.getName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setTotalExperience(userDTO.getTotalExperience());

//        newUser.setCreateBy();

        userRepository.save(newUser);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
//    public void saveUser(User user) {
//        user.setName();
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
//        userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
//        try {
//            return userRepository.findById(id).orElseThrow(() ->
//                        new UserNotFoundException("User with id: " + id + " is not found!")
//                );
//        } catch (UserNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    @Transactional
    public User updateUser(Long id, User user) {
        User existingUser = findUserById(id);

//        System.out.println(user.getId());
//        System.out.println(user.getName());
//        System.out.println(user.getEmail());
//        System.out.println(user.getTotalExperience());
//        System.out.println(user.getImageUrl());
////        System.out.println(user.getId());
//        System.out.println(existingUser.getId() + " id......");

        if(existingUser != null) {
            if(user.getName() != null) existingUser.setName(user.getName());
            if(user.getName() != null) existingUser.setImageUrl(user.getImageUrl());
            if(user.getName() != null) existingUser.setPassword(user.getPassword());
            if(user.getName() != null) existingUser.setTotalExperience(user.getTotalExperience());

            userRepository.save(existingUser);
        }
        else {
            throw new UserNotFoundException("User with id: " + id + " not found!");
        }
        return existingUser;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        User user = findUserById(id);
        try {
            if(user != null) userRepository.deleteById(id);
        } catch (Exception ex) {
            try {
                throw new UserNotFoundException("User with the id: " + id + " is not found!");
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
