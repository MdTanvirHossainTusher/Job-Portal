package com.example.job_portal.controller;


import com.example.job_portal.dao.UserDAO;
import com.example.job_portal.dto.ProfileDTO;
import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.dto.response.UserResponse;
import com.example.job_portal.entity.api_response.ApiResponse;
import com.example.job_portal.service.RoleService;
import com.example.job_portal.service.UserService;
import com.example.job_portal.utils.SortEntityDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@Tag(name = "User", description = "User Related APIs")
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
//    public ResponseEntity<List<UserDTO>> getAllUsers(
    public ResponseEntity<UserResponse> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "desc", required = false) String sortDirection
    ) {
        UserResponse allUser = userService.findAllUser(pageNumber, pageSize, sortBy, sortDirection);
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        UserDTO userDTO = userService.findUserById(userId);
        HttpStatus status = userDTO != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(
                userDTO != null ?
                        userDTO :
                        new ApiResponse("User not found!", false), status);
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
                                                         @RequestParam(required = false) Double experienceFrom,
                                                         @RequestParam(required = false) Double experienceTo,
                                                         @RequestParam(required = false) String universityName) {
        try {
            List<UserDTO> users = userDAO.filterUsers(email, experienceFrom, experienceTo, universityName);
            List<UserDTO> sortedUserList = SortEntityDTO.sortResponseDTO(users, Comparator.comparing(UserDTO::getId).reversed());

            return new ResponseEntity<>(sortedUserList, HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{userId}/roles")
    public ResponseEntity<List<String>> getUserRolesName(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUserRoles(userId), HttpStatus.OK);
    }

    @PostMapping("/{userId}/add-role")
    public ResponseEntity<?> addNewRoleToUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = true) String roleName
    ) {
        roleService.addRoleToUser(userId, roleName);
        return new ResponseEntity<>(new ApiResponse("Role added successfully!", true), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/delete-role")
    public ResponseEntity<ApiResponse> deleteUserRole(
            @PathVariable("userId") Long userId,
            @RequestParam(required = true) String roleName
    ) {
        userService.deleteUserRole(userId, roleName);
        return new ResponseEntity<>(new ApiResponse("Role deleted successfully!", true), HttpStatus.OK);
    }

    @GetMapping("/{userId}/profiles")
    public ResponseEntity<ProfileDTO> getUserProfile(
            @PathVariable("userId") Long userId
    ) {
        ProfileDTO profileDTO = userService.getUserProfile(userId);
        return new ResponseEntity<>(profileDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUserByEmail(
            @RequestParam(value = "email", required = false) String email
    ) {
        List<UserDTO> userDTOS = userService.searchUsers(email);
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

}

