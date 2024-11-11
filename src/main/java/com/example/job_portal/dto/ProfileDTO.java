package com.example.job_portal.dto;

import com.example.job_portal.entity.University;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    @JsonUnwrapped
    private CVDTO cvdto;

    @JsonUnwrapped
    private UserDTO userDTO;

    @JsonUnwrapped
    private List<SkillDTO> skillDTOS;

    @JsonUnwrapped
    private List<UniversityDTO> universityDTOS;
//    private List<JobDTO> jobDTOS;
//    private List<JobApplicationDTO> applicationDTOS;
//    private List<CompanyDTO> companyDTOS;
}
