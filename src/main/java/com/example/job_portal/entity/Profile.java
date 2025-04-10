package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbProfile;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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

//    @ManyToMany
//    @JoinTable(
//            name = "profile_university",
//            joinColumns = @JoinColumn(name = "profile_id"),
//            inverseJoinColumns = @JoinColumn(name = "university_id")
//    )
//    private List<University> universities;

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
            name = "profile_company",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<Company> companies;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileUniversity> profileUniversities = new ArrayList<>();

//    public void addUniversity(University university, String degree, String passingYear) {
    public void addUniversity(University university) {
        ProfileUniversity profileUniversity = new ProfileUniversity();
        profileUniversity.setId(new ProfileUniversityId(this.getId(), university.getId()));
        profileUniversity.setProfile(this);
        profileUniversity.setUniversity(university);
//        profileUniversity.setDegree(degree);
//        profileUniversity.setPassingYear(passingYear);

        profileUniversities.add(profileUniversity);
    }

    public void updateUniversityDetails(Long universityId, String degree, String passingYear) {
        for (ProfileUniversity pu : profileUniversities) {
            if (pu.getId().getUniversityId().equals(universityId)) {
                if (degree != null) pu.setDegree(degree);
                if (passingYear != null) pu.setPassingYear(passingYear);
                break;
            }
        }
    }

}
