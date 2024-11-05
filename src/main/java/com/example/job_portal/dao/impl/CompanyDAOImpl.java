package com.example.job_portal.dao.impl;

import com.example.job_portal.dao.CompanyDAO;
import com.example.job_portal.dao.UserDAO;
import com.example.job_portal.entity.Company;
import com.example.job_portal.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

    private final EntityManager entityManager;

    public CompanyDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Company> searchCompanyByCompanyName(String pattern) {
        pattern = pattern.toLowerCase();
        TypedQuery<Company> query = entityManager.createQuery(
                "SELECT c FROM Company c WHERE c.companyName LIKE :pattern", Company.class
        );
        query.setParameter("pattern", "%" + pattern + "%");
        return query.getResultList();
    }

    @Override
    public List<Company> searchCompanyByCompanyType(String pattern) {
        pattern = pattern.toLowerCase();
        TypedQuery<Company> query = entityManager.createQuery(
                "SELECT c FROM Company c WHERE c.companyType LIKE :pattern", Company.class
        );
        query.setParameter("pattern", pattern);
        return query.getResultList();
    }
}
