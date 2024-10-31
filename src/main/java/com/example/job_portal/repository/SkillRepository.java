package com.example.job_portal.repository;

import com.example.job_portal.entity.Profile;
import com.example.job_portal.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
}
