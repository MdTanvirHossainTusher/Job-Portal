package com.example.job_portal.repository;

import com.example.job_portal.entity.Company;
import com.example.job_portal.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
