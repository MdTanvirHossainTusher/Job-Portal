package com.example.job_portal.dao.impl;

import com.example.job_portal.dao.JobDAO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.entity.Job;
import com.example.job_portal.entity.User;
import com.example.job_portal.exception.JobNotFoundException;
import com.example.job_portal.exception.UserNotFoundException;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JobDAOImpl implements JobDAO {

    private final EntityManager entityManager;

    public JobDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<JobDTO> filterJobs(String jobPosition, String jobLocation) {
        StringBuilder queryBuilder = new StringBuilder("SELECT j FROM Job j");
        Map<String, Object> parameters = new HashMap<>();

        boolean hasFilters = false;

        if (jobPosition != null && !jobPosition.trim().isEmpty()) {
            queryBuilder.append(hasFilters ? " AND" : " WHERE");
            queryBuilder.append(" LOWER(j.jobPosition) LIKE :jobPosition");
            parameters.put("jobPosition", "%" + jobPosition.toLowerCase() + "%");
            hasFilters = true;
        }

        if (jobLocation != null && !jobLocation.trim().isEmpty()) {
            queryBuilder.append(hasFilters ? " AND" : " WHERE");
            queryBuilder.append(" LOWER(j.jobLocation) LIKE :jobLocation");
            parameters.put("jobLocation", "%" + jobLocation.toLowerCase() + "%");
            hasFilters = true;
        }

        queryBuilder.append(hasFilters ? " AND" : " WHERE");
        queryBuilder.append(" j.isDeleted = false");

        try {
            TypedQuery<Job> query = entityManager.createQuery(queryBuilder.toString(), Job.class);

            if (!parameters.isEmpty()) {
                parameters.forEach(query::setParameter);
            }

            List<Job> jobs = query.getResultList();


            if (jobs.isEmpty()) {
                return new ArrayList<>();
            }

            return EntityToEntityDTOConverter.convertJobsToJobsDTO(jobs);

        } catch (Exception e) {
            throw new JobNotFoundException("Error occurred while searching for jobs");
        }
    }

}
