package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbUniversity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DbUniversity.TABLE_NAME)
public class University extends AuditInfo {

    @Column(name = DbUniversity.UNIVERSITY_NAME)
    private String name;

    @Column(name = DbUniversity.DEGREE)
    private String degree;

    @Column(name = DbUniversity.PASSING_YEAR)
    private String passingYear;

    @ManyToMany(mappedBy = "universities")
    private List<Profile> profiles;
}
