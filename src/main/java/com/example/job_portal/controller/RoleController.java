package com.example.job_portal.controller;

import com.example.job_portal.dto.RoleDTO;
import com.example.job_portal.entity.api_response.ApiResponse;
import com.example.job_portal.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("${api.prefix}/roles")
@Tag(name = "Role", description = "Role Related APIs")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return new ResponseEntity<>(roleService.findAllRoles(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoleDTO> createUserRole(@Valid @RequestBody RoleDTO roleDTO) {
        RoleDTO createdRole = roleService.createRole(roleDTO);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRoleById(@PathVariable Long roleId) {
        roleService.deleteRoleById(roleId);
        return new ResponseEntity<>(new ApiResponse("Role deleted successfully!", true),
                HttpStatus.OK);
    }

}
