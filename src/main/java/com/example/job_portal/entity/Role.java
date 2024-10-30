package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbRole;
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
@Table(name = DbRole.TABLE_NAME)
public class Role extends AuditInfo {

    @Column(name = DbRole.ROLE)
    private String role;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<User> users;
}
