package com.example.job_portal.repository;

import com.example.job_portal.entity.Job;
import com.example.job_portal.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
