package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbMyCompany;
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
@Table(name = DbMyCompany.TABLE_NAME)
public class MyCompany extends AuditInfo {

//    @ManyToMany(mappedBy = "myCompanies")
    @ManyToMany
    @JoinTable(
            name = "my_company_from_company",
            joinColumns = @JoinColumn(name = "my_company_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<Company> companies;

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
            joinColumns = @JoinColumn(name = "my_company_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private List<Profile> profiles;
}
