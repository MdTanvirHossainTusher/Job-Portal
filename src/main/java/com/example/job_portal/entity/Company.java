package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbCompany;
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
@Table(name = DbCompany.TABLE_NAME)
public class Company extends AuditInfo {

    @Column(name = DbCompany.COMPANY_NAME)
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
    )
    private List<Job> jobs;

    @ManyToMany(
//            mappedBy = "companies",
            cascade = {
                    CascadeType.ALL
            },
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "my_company_from_company",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "my_company_id")
    )
    private List<MyCompany> myCompanies;
}
