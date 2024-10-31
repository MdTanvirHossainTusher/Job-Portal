package com.example.job_portal.service.impl;

import com.example.job_portal.entity.MyCompany;
import com.example.job_portal.repository.MyCompanyRepository;
import com.example.job_portal.service.MyCompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyCompanyServiceImpl implements MyCompanyService {

    private final MyCompanyRepository myCompanyRepository;

    public MyCompanyServiceImpl(MyCompanyRepository myCompanyRepository) {
        this.myCompanyRepository = myCompanyRepository;
    }

    @Override
    @Transactional
    public void save(MyCompany myCompany) {
        myCompanyRepository.save(myCompany);
    }
}
