package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class JoinEntityAuditInfo {
    @CreatedDate
    @Column(name = DbConstant.DbCommon.CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = DbConstant.DbCommon.CREATED_BY, nullable = false, updatable = false)
    private String createBy;

    @LastModifiedDate
    @Column(name = DbConstant.DbCommon.LAST_UPDATED_AT, nullable = false)
    private LocalDateTime lastUpdatedAt;

    @LastModifiedBy
    @Column(name = DbConstant.DbCommon.LAST_UPDATED_BY, nullable = false)
    private String lastUpdatedBy;
}
