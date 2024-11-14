package com.example.job_portal.repository;

import com.example.job_portal.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
//
//    @Query("SELECT p FROM Profile p WHERE p.id = :profileId AND p.isDeleted = false")
//    Optional<Profile> findProfileById(@Param("profileId") Long profileId);

    Optional<Profile> findByIdAndIsDeletedFalse(@Param("profileId") Long profileId);

}
