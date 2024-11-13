package com.example.job_portal.repository;

import com.example.job_portal.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Query("SELECT s FROM Skill s WHERE s.isDeleted = false")
    List<Skill> findAllSkill();

    @Query("SELECT s FROM Skill s WHERE s.isDeleted = false AND s.id = :id")
    Optional<Skill> findSkillById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Skill s SET s.isDeleted = true WHERE s.id = :id")
    void softDeleteSkillById(@Param("id") Long id);
}
