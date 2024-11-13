package com.example.job_portal.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean isDeleted = false;

}
