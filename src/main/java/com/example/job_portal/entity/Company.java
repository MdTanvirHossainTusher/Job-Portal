package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbCompany;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = DbCompany.TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_company_name",
                        columnNames = {DbCompany.COMPANY_NAME}
                )
        })
public class Company extends AuditInfo {

    @Column(name = DbCompany.COMPANY_NAME, unique = true)
    @NotBlank @NotNull @NotEmpty
    private String companyName;

    @Column(name = DbCompany.COMPANY_LOCATION)
    private String companyLocation;

    @Column(name = DbCompany.COMPANY_TYPE)
    private String companyType;

    @Column(name = DbCompany.WORKING_MODE)
    private String workingMode;

    @Column(name = DbCompany.IS_COMPANY_DELETED)
    private boolean isDeleted = false;

    @OneToMany(
            mappedBy = "company",
            cascade = {
                    CascadeType.ALL
            },
            fetch = FetchType.LAZY
    )
    private List<Job> jobs;

    @ManyToMany(
            cascade = {
                    CascadeType.ALL
            },
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "profile_company",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private List<Profile> profiles;

}
