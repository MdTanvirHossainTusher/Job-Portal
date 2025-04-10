package com.example.job_portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUniversityDTO {
    private Long universityId;
    private String universityName;
    private String degree;
    private String passingYear;
}