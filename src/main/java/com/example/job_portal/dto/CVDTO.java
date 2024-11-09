package com.example.job_portal.dto;

import com.example.job_portal.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CVDTO {
    private Long cvId;
    private String cvFormat;
    private String cvSize;
    private String cvUrl;
}
