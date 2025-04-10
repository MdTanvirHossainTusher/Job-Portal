package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbUniversity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile_university")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUniversity extends JoinEntityAuditInfo {

    @EmbeddedId
    private ProfileUniversityId id;

    @ManyToOne
    @MapsId("profileId")
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne
    @MapsId("universityId")
    @JoinColumn(name = "university_id")
    private University university;

    @Column(name = DbUniversity.DEGREE)
    private String degree;

    @Column(name = DbUniversity.PASSING_YEAR)
    private String passingYear;
}
