package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant;
import com.example.job_portal.constant.db.DbConstant.DbSkill;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DbSkill.TABLE_NAME)
public class Skill extends AuditInfo {

    @Column(name = DbSkill.SKILL_NAME, unique = true)
    private String skillName;

    @Column(name = DbSkill.IS_Skill_DELETED)
    private boolean isDeleted = false;

    @ManyToMany(mappedBy = "skills")
    private List<Profile> profiles;
}