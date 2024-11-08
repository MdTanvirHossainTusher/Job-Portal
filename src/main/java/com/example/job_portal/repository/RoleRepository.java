package com.example.job_portal.repository;

import com.example.job_portal.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByRole(String roleName);

    boolean existsByRole(String role);

    void deleteByRole(String roleName);
}
