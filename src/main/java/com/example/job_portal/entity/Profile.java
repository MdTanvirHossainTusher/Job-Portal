package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant;
import com.example.job_portal.constant.db.DbConstant.DbProfile;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DbProfile.TABLE_NAME)
public class Profile extends AuditInfo {

    @Column(name = DbProfile.IS_PROFILE_DELETED)
    private boolean isDeleted = false;

//    @OneToOne(cascade = CascadeType.ALL)
    @OneToOne
    @JoinColumn(name = "cv_id")
    private CV cv;

    @OneToOne(
            mappedBy = "profile",
            cascade = CascadeType.ALL
    )
    private User user;

    @ManyToMany
    @JoinTable(
            name = "profile_skill",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;

    @ManyToMany
    @JoinTable(
            name = "profile_university",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "university_id")
    )
    private List<University> universities;

    @ManyToMany
    @JoinTable(
            name = "profile_job",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    private List<Job> jobs;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH
            },
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "profile_my_company",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "my_company_id")
    )
    private List<MyCompany> myCompanies;

}
