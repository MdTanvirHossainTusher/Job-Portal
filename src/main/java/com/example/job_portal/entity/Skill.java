package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbSkill;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DbSkill.TABLE_NAME)
public class Skill extends AuditInfo {

    @Column(name = DbSkill.SKILL_NAME)
    private String skillName;

    @ManyToMany(mappedBy = "skills")
    private List<Profile> profiles;
}