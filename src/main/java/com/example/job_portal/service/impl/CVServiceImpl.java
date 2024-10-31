package com.example.job_portal.service.impl;

import com.example.job_portal.entity.CV;
import com.example.job_portal.repository.CVRepository;
import com.example.job_portal.service.CVService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CVServiceImpl implements CVService {

    private final CVRepository cvRepository;

    public CVServiceImpl(CVRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    @Override
    @Transactional
    public void save(CV cv) {
        cvRepository.save(cv);
    }
}
