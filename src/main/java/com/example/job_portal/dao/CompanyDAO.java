package com.example.job_portal.dao;

import com.example.job_portal.dto.CompanyDTO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.Company;
import com.example.job_portal.entity.User;

import java.util.List;

public interface CompanyDAO {

    List<CompanyDTO> filterCompanies(String companyName, String companyLocation, String workingMode);

}
