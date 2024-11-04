package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbCommon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedBy
    @Column(name = DbCommon.CREATED_BY, nullable = false, updatable = false)
    private String createBy;

    @LastModifiedBy
    @Column(name = DbCommon.LAST_UPDATED_BY, nullable = false)
    private String lastUpdatedBy;
}
