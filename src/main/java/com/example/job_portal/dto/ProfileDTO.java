package com.example.job_portal.dto;

import com.example.job_portal.entity.University;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    private CVDTO cvdto;
    private UserDTO userDTO;
    private List<SkillDTO> skillDTOS;
    private List<UniversityDTO> universityDTOS;
    private List<JobDTO> jobDTOS;
//    private List<CompanyDTO> companyDTOS;
}
