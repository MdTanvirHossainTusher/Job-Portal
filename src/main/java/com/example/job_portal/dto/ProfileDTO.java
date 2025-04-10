package com.example.job_portal.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private CVDTO cvdto;
    @JsonUnwrapped
    private UserDTO userDTO;
    private List<SkillDTO> skillDTOS;
//    private List<UniversityDTO> universityDTOS;
    private List<CompanyDTO> companyDTOS;
    private List<ProfileUniversityDTO> profileUniversityDTOS;
}
