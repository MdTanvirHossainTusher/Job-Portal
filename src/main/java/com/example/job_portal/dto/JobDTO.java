package com.example.job_portal.dto;


import com.example.job_portal.constant.db.DbConstant;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {

    private Long id;
    private String jobTitle;
    private String jobDescription;
    private String salary;
    private String jobPosition;
    private String jobLocation;
    private boolean isDeleted = false;

}
