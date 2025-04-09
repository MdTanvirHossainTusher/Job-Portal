package com.example.job_portal.dao;

import com.example.job_portal.dto.CompanyDTO;

import java.util.List;

public interface CompanyDAO {
    List<CompanyDTO> filterCompanies(String companyName, String companyLocation, String workingMode);
}
