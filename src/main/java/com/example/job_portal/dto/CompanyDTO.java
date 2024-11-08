package com.example.job_portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Long id;
    private String companyName;
    private String companyLocation;
    private String companyType;
    private String workingMode;
    private boolean isDeleted = false;
}
