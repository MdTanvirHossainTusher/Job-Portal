package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbCompany;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

//@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DbCompany.TABLE_NAME)
public class Company extends AuditInfo {

    @Column(name = DbCompany.COMPANY_NAME, unique = true)
    private String companyName;

    @Column(name = DbCompany.COMPANY_LOCATION)
    private String companyLocation;

    @Column(name = DbCompany.COMPANY_TYPE)
    private String companyType;

    @Column(name = DbCompany.WORKING_MODE)
    private String workingMode;

    @OneToMany(
            mappedBy = "company",
            cascade = {
                    CascadeType.ALL
            },
            fetch = FetchType.LAZY
//            fetch = FetchType.EAGER
    )
    private List<Job> jobs;

    @ManyToMany(
            cascade = {
                    CascadeType.ALL
            },
            fetch = FetchType.LAZY
//            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "my_company_from_company",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "my_company_id")
    )
    private List<MyCompany> myCompanies;

}
