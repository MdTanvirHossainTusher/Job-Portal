package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbSkill;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = DbSkill.TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_skill",
                        columnNames = {DbSkill.SKILL_NAME}
                )
        })
public class Skill extends AuditInfo {

    @NotEmpty
    @NotNull @NotBlank
    @Column(name = DbSkill.SKILL_NAME)
    private String skillName;

    @Column(name = DbSkill.IS_Skill_DELETED)
    private boolean isDeleted = false;

    @ManyToMany(mappedBy = "skills")
    private List<Profile> profiles;
}