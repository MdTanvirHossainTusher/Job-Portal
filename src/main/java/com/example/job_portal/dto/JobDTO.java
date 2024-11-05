package com.example.job_portal.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private String jobTitle;
    private String jobDescription;
    private String salary;
    private String jobPosition;
    private String jobLocation;
    private boolean isJobFraudulent;
}
