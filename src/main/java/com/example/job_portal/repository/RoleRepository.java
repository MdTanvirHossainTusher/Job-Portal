package com.example.job_portal.repository;

import com.example.job_portal.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.isDeleted = false AND r.role = :roleName")
    Role getByRole(String roleName);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Role r WHERE r.isDeleted = false AND r.role = :roleName")
    boolean existsByRole(String roleName);

//    void deleteByRole(String roleName);

    @Query("SELECT r FROM Role r WHERE r.isDeleted = false AND r.id = :roleId")
    Optional<Role> findRoleById(@Param("roleId") Long roleId);
}
