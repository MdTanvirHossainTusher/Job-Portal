package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbCommon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbCommon.ID)
    private Long id;

    @CreationTimestamp
    @Column(name = DbCommon.CREATED_AT, nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = DbCommon.LAST_UPDATED_AT, nullable = false)
    private ZonedDateTime lastUpdatedAt;

    @Column(name = DbCommon.CREATED_BY, nullable = false, updatable = false)
    private String createBy;

    @Column(name = DbCommon.LAST_UPDATED_BY, nullable = false)
    private String lastUpdatedBy;
}
