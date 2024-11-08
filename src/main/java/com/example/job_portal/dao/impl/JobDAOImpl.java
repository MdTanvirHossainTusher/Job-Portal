package com.example.job_portal.dao.impl;

import com.example.job_portal.dao.JobDAO;
import com.example.job_portal.entity.Job;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class JobDAOImpl implements JobDAO {

    private final EntityManager entityManager;

    public JobDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Job> filterJobByJobLocation(String jobLocation) {
        TypedQuery<Job> query = entityManager.createQuery(
                "SELECT j FROM Job j WHERE LOWER(j.jobLocation) LIKE :data", Job.class
        );
        query.setParameter("data", "%" + jobLocation + "%");
        return query.getResultList();
    }

    @Override
    public List<Job> filterJobByJobPosition(String jobPosition) {
        TypedQuery<Job> query = entityManager.createQuery(
                "SELECT j FROM Job j WHERE LOWER(j.jobPosition) LIKE :data", Job.class
        );
        query.setParameter("data", "%" + jobPosition + "%");
        return query.getResultList();
    }
}
