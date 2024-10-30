package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = DbConstant.DbAudit.TABLE_NAME)
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbConstant.DbCommon.ID)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = DbConstant.DbCommon.CREATED_AT)
    private Date createAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = DbConstant.DbCommon.LAST_UPDATED_AT)
    private Date lastUpdatedAt;

    @Column(name = DbConstant.DbCommon.CREATED_BY)
    private String createBy;

    @Column(name = DbConstant.DbCommon.LAST_UPDATED_BY)
    private String lastUpdatedBy;

    public Audit(Date createAt,
                 Date lastUpdatedAt,
                 String createBy,
                 String lastUpdatedBy) {
        this.createAt = createAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.createBy = createBy;
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
