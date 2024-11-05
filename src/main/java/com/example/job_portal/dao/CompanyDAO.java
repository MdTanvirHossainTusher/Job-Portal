package com.example.job_portal.dao;

import com.example.job_portal.entity.Company;
import com.example.job_portal.entity.User;

import java.util.List;

public interface CompanyDAO {
    List<Company> searchCompanyByCompanyName(String pattern);
    List<Company> searchCompanyByCompanyType(String companyType);
}
