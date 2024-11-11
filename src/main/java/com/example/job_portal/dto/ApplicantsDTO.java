package com.example.job_portal.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantsDTO {

//    private CompanyDTO companyDTO;
//    private ProfileDTO profileDTO;

    @JsonUnwrapped
    private UserDTO userDTO;

    @JsonUnwrapped
    private CVDTO cvdto;

}
