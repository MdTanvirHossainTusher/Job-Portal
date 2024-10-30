package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbUniversity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = DbUniversity.TABLE_NAME)
public class University extends AuditInfo {

    @Column(name = DbUniversity.UNIVERSITY_NAME)
    private String name;

    @Column(name = DbUniversity.DEGREE)
    private String degree;

    @Column(name = DbUniversity.PASSING_YEAR)
    private String passingYear;
}
