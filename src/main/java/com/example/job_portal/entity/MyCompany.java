package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant;
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
@Table(name = DbConstant.DbCV.TABLE_NAME)
public class MyCompany extends AuditInfo{

    @ManyToMany(
            cascade = {
                    CascadeType.DETACH,
            },
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "my_company_from_company",
            joinColumns = @JoinColumn(name = "my_company_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<Company> companies;
}
