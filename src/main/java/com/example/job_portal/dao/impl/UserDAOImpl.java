package com.example.job_portal.dao.impl;

import com.example.job_portal.dao.UserDAO;
import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.entity.User;
import com.example.job_portal.exception.UserNotFoundException;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDAOImpl implements UserDAO {

    private final EntityManager entityManager;

    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<UserDTO> filterUsers(String email, Double experience, String universityName) {
//        TypedQuery<User> query = entityManager.createQuery(
//                "SELECT u FROM User u WHERE u.email LIKE :pattern", User.class
//        );
//        query.setParameter("pattern", "%" + email + "%");
//        return EntityToEntityDTOConverter.convertUsersToUsersDTO(query.getResultList());

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

        if (experience != null) {
            queryBuilder.append(" AND u.totalExperience = :experience");
            parameters.put("experience", experience);
        }

        if (universityName != null && !universityName.trim().isEmpty()) {
            queryBuilder.append(" AND univ.name LIKE :universityName");
            parameters.put("universityName", "%" + universityName + "%");
        }

        try {
            TypedQuery<User> query = entityManager.createQuery(queryBuilder.toString(), User.class);

            parameters.forEach(query::setParameter);

            return EntityToEntityDTOConverter.convertUsersToUsersDTO(query.getResultList());
        }
        catch (Exception e) {
            throw new UserNotFoundException("No user found!");
        }

    }

//    @Override
//    public List<UserDTO> searchUserByEmailPattern(String pattern) {
//
//        TypedQuery<User> query = entityManager.createQuery(
//                "SELECT u FROM User u WHERE u.email LIKE :pattern", User.class
//        );
//        query.setParameter("pattern", "%" + pattern + "%");
//
//        return EntityToEntityDTOConverter.convertUsersToUsersDTO(query.getResultList());
//
//    }
//
//    @Override
//    public List<UserDTO> searchUserByYearOfExperience(Double yearOfExperience) {
//
//        TypedQuery<User> query = entityManager.createQuery(
//                "SELECT u FROM User u WHERE u.totalExperience = :year", User.class
//        );
//        query.setParameter("year", yearOfExperience);
//
//        return EntityToEntityDTOConverter.convertUsersToUsersDTO(query.getResultList());
//
//    }
}
