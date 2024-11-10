package com.example.job_portal.dto;

import com.example.job_portal.constant.db.DbConstant;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversityDTO {

    private String name;
    private String degree;
    private String passingYear;

}
