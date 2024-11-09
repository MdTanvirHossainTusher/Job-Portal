package com.example.job_portal.service;


import com.example.job_portal.dto.CVDTO;
import com.example.job_portal.entity.CV;

public interface
CVService {
    void save(CV cv);

    CVDTO createCV(Long profileId, CVDTO cvDTO);
}
