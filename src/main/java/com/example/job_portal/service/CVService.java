package com.example.job_portal.service;


import com.example.job_portal.dto.CVDTO;
import com.example.job_portal.entity.CV;

import java.util.List;

public interface
CVService {
    void save(CV cv);

    CVDTO createCV(Long profileId, CVDTO cvDTO);

//    CVDTO updateCV(Long cvId, Long profileId, CVDTO cvDTO);
    CVDTO updateCV(Long profileId, CVDTO cvDTO);

    List<CVDTO> getAllCVs();

    CVDTO getUserCV(Long profileId);
}
