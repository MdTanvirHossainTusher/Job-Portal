package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = DbRole.TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_user_role",
                        columnNames = {DbRole.ROLE}
                )
        })
public class Role extends AuditInfo {
    @Column(name = DbRole.ROLE)
    @NotBlank @NotNull @NotEmpty
    private String role;

    @Column(name = DbRole.IS_ROLE_DELETED)
    private boolean isDeleted = false;

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
            name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

}
