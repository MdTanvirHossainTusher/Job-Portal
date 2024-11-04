package com.example.job_portal.service.impl;

import com.example.job_portal.entity.User;
import com.example.job_portal.exception.UserNotFoundException;
import com.example.job_portal.repository.UserRepository;
import com.example.job_portal.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
//    public User save(User user) {
    public void save(User user) {
//        return userRepository.save(user);
        userRepository.save(user);
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
    public User updateUser(User user) {
        User existingUser = findUserById(user.getId());

        if(existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setImageUrl(user.getImageUrl());
            existingUser.setPassword(user.getPassword());
            existingUser.setTotalExperience(user.getTotalExperience());
        }
        return existingUser;
    }
}
