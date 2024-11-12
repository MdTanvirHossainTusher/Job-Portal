package com.example.job_portal.dao.impl;

import com.example.job_portal.dao.CompanyDAO;
import com.example.job_portal.dto.CompanyDTO;
import com.example.job_portal.entity.Company;
import com.example.job_portal.exception.CompanyNotFoundException;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

    private final EntityManager entityManager;

    public CompanyDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CompanyDTO> filterCompanies(String companyName, String companyLocation, String workingMode) {
        StringBuilder queryBuilder = new StringBuilder("SELECT c FROM Company c");
        Map<String, Object> parameters = new HashMap<>();

        boolean hasFilters = false;

        if (companyName != null && !companyName.trim().isEmpty()) {
            queryBuilder.append(hasFilters ? " AND" : " WHERE");
            queryBuilder.append(" LOWER(c.companyName) LIKE :companyName");
            parameters.put("companyName", "%" + companyName.toLowerCase() + "%");
            hasFilters = true;
        }

        if (companyLocation != null && !companyLocation.trim().isEmpty()) {
            queryBuilder.append(hasFilters ? " AND" : " WHERE");
            queryBuilder.append(" LOWER(c.companyLocation) LIKE :companyLocation");
            parameters.put("companyLocation", "%" + companyLocation.toLowerCase() + "%");
            hasFilters = true;
        }

        if (workingMode != null && !workingMode.trim().isEmpty()) {
            queryBuilder.append(hasFilters ? " AND" : " WHERE");
            queryBuilder.append(" LOWER(c.workingMode) LIKE :workingMode");
            parameters.put("workingMode", "%" + workingMode.toLowerCase() + "%");
            hasFilters = true;
        }

        queryBuilder.append(hasFilters ? " AND" : " WHERE");
        queryBuilder.append(" c.isDeleted = false");

        try {
            TypedQuery<Company> query = entityManager.createQuery(queryBuilder.toString(), Company.class);

            if (!parameters.isEmpty()) {
                parameters.forEach(query::setParameter);
            }

            List<Company> companies = query.getResultList();

            if (companies.isEmpty()) {
                return new ArrayList<>();
            }

            return EntityToEntityDTOConverter.convertCompaniesToCompaniesDTO(companies);

        } catch (Exception e) {
            throw new CompanyNotFoundException("Error occurred while searching for company");
        }
    }

}
