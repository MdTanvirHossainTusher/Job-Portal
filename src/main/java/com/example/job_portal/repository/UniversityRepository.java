package com.example.job_portal.repository;

import com.example.job_portal.entity.University;
import com.example.job_portal.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    @Query("SELECT u FROM University u WHERE u.isDeleted = false")
    List<University> findAllUniversity();

    @Query("SELECT u FROM University u WHERE u.isDeleted = false AND u.id = :id")
    Optional<University> findUniversityById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE University u SET u.isDeleted = true WHERE u.id = :id")
    void softDeleteUniversityById(@Param("id") Long id);
}
