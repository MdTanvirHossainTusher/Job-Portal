package com.example.job_portal.entity;

import com.example.job_portal.constant.db.DbConstant.DbCV;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DbCV.TABLE_NAME)
public class CV extends AuditInfo{

    @Column(name = DbCV.CV_FORMAT)
    private String cvFormat;

    @Column(name = DbCV.CV_SIZE)
    private String cvSize;

    @Column(name = DbCV.CV_URL)
    private String cvUrl;

    @ManyToMany(mappedBy = "cvs", fetch = FetchType.LAZY)
    private List<Job> jobs;
}
