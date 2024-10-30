package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbUser;
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
@Table(
        name = DbUser.TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_user_email",
                        columnNames = {DbUser.USER_EMAIL}
                )
        })
public class User extends AuditInfo {
    @Column(name = DbUser.USER_NAME)
    private String name;

    @Column(name = DbUser.USER_EMAIL, updatable = false, nullable = false)
    private String email;

    @Column(name = DbUser.USER_PASSWORD)
    private String password;

    @Column(name = DbUser.TOTAL_EXPERIENCE)
    private Double totalExperience;

    @ManyToMany(
            cascade = {
                    CascadeType.ALL
            },
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Profile profile;
}
