package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbJob;
import jakarta.persistence.*;
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
@Table(name = DbJob.TABLE_NAME)
public class Job extends AuditInfo {

    @Column(name = DbJob.JOB_DESCRIPTION)
    private String jobDescription;

    @Column(name = DbJob.SALARY)
    private String salary;

    @Column(name = DbJob.IS_FRAUDULENT)
    private boolean isJobFraudulent;

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "job_cv",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "cv_id")
    )
    private List<CV> cvs;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH,
            },
            fetch = FetchType.EAGER
    )
    private Company company;

//    @ManyToMany(mappedBy = "jobs")
    @ManyToMany
    @JoinTable(
            name = "profile_job",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private List<Profile> profiles;
}
