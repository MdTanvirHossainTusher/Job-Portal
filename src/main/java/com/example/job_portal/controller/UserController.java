package com.example.job_portal.controller;

import com.example.job_portal.dao.UserDAO;
import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.entity.api_response.ApiResponse;
import com.example.job_portal.service.RoleService;
import com.example.job_portal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserDAO userDAO;

    public UserController(UserService userService, RoleService roleService, UserDAO userDAO) {
        this.userService = userService;
        this.roleService = roleService;
        this.userDAO = userDAO;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        UserDTO userDTO = userService.findUserById(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long userId) {
        UserDTO updatedUser = userService.updateUser(userId, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(
                new ApiResponse("User deleted successfully", true),
                HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<UserDTO>> filterFromUsers(@RequestParam(required = false) String email,
                                                       @RequestParam(required = false) Double experience,
                                                       @RequestParam(required = false) String universityName) {
        try {
            List<UserDTO> users = userDAO.filterUsers(email, experience, universityName);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{userId}/roles")
    public ResponseEntity<List<String>> getUserRolesName(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUserRoles(userId), HttpStatus.OK);
    }

    @PostMapping("/{userId}/add-role")
    public ResponseEntity<Void> addNewRoleToUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = true) String roleName
    ) {
        try {
            roleService.addRoleToUser(userId, roleName);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{userId}/delete-role")
    public ResponseEntity<Void> deleteUserRole(
            @PathVariable("userId") Long userId,
            @RequestParam(required = true) String roleName
    ) {
        try {
            userService.deleteUserRole(userId, roleName);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
