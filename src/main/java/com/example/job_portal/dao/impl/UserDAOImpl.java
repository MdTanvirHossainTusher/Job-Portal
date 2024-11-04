package com.example.job_portal.dao.impl;

import com.example.job_portal.dao.UserDAO;
import com.example.job_portal.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private final EntityManager entityManager;

    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> searchUserByEmailPattern(String pattern) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.email LIKE :pattern", User.class
        );
        query.setParameter("pattern", "%" + pattern + "%");
        return query.getResultList();
    }

    @Override
    public List<User> searchUserByYearOfExperience(Double yearOfExperience) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.totalExperience = :year", User.class
        );
        query.setParameter("year", yearOfExperience);
        return query.getResultList();
    }

    @Override
    public List<User> searchUserByUserCreationDate(Date userCreationDate) {
        LocalDate inputDate = userCreationDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE FUNCTION('DATE', u.createdAt) = :creationDate", User.class
        );

        query.setParameter("creationDate", inputDate);
        return query.getResultList();
    }
}
