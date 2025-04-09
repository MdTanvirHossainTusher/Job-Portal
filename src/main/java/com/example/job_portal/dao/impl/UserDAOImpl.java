package com.example.job_portal.dao.impl;

import com.example.job_portal.dao.UserDAO;
import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.entity.User;
import com.example.job_portal.exception.ResourceNotFoundException;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final EntityManager entityManager;

    @Override
    public List<UserDTO> filterUsers(String email, Double experienceFrom, Double experienceTo, String universityName) {

        StringBuilder queryBuilder = new StringBuilder("SELECT DISTINCT u FROM User u JOIN u.profile p");

        if (universityName != null && !universityName.trim().isEmpty()) {
            queryBuilder.append(" JOIN p.universities univ");
        }

        queryBuilder.append(" WHERE 1=1 AND u.isDeleted = false");
        Map<String, Object> parameters = new HashMap<>();

        if (email != null && !email.trim().isEmpty()) {
            queryBuilder.append(" AND u.email LIKE :email");
            parameters.put("email", "%" + email + "%");
        }

        if (experienceFrom != null || experienceTo != null) {
            if (experienceFrom != null && experienceTo != null) {
                queryBuilder.append(" AND u.totalExperience BETWEEN :experienceFrom AND :experienceTo");
                parameters.put("experienceFrom", experienceFrom);
                parameters.put("experienceTo", experienceTo);
            }
            else if (experienceFrom != null) {
                queryBuilder.append(" AND u.totalExperience >= :experienceFrom");
                parameters.put("experienceFrom", experienceFrom);
            }
            else {
                queryBuilder.append(" AND u.totalExperience <= :experienceTo");
                parameters.put("experienceTo", experienceTo);
            }
        }

        if (universityName != null && !universityName.trim().isEmpty()) {
            queryBuilder.append(" AND LOWER(univ.name) LIKE :universityName");
            parameters.put("universityName", "%" + universityName + "%");
        }

        try {
            TypedQuery<User> query = entityManager.createQuery(queryBuilder.toString(), User.class);

            parameters.forEach(query::setParameter);

            return EntityToEntityDTOConverter.convertUsersToUsersDTO(query.getResultList());
        }
        catch (Exception e) {
            throw new ResourceNotFoundException("No user found!");
        }

    }

}
