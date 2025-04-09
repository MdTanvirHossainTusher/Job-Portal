package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbUniversity;
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
        name = DbUniversity.TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_university_name",
                        columnNames = {DbUniversity.UNIVERSITY_NAME}
                )
        })
public class University extends AuditInfo {

    @NotEmpty
    @NotNull @NotBlank
    @Column(name = DbUniversity.UNIVERSITY_NAME)
    private String name;

    @Column(name = DbUniversity.DEGREE)
    private String degree;

    @Column(name = DbUniversity.PASSING_YEAR)
    private String passingYear;

    @Column(name = DbUniversity.IS_UNIVERSITY_DELETED)
    private boolean isDeleted = false;

    @ManyToMany(mappedBy = "universities")
    private List<Profile> profiles;
}
