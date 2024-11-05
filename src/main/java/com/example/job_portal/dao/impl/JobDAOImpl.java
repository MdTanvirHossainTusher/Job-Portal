package com.example.job_portal.dao.impl;

import com.example.job_portal.dao.CompanyDAO;
import com.example.job_portal.entity.Company;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobDAOImpl implements JobDAO {

    private final EntityManager entityManager;

    public JobDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


}
