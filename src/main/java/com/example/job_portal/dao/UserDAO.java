package com.example.job_portal.dao;

import com.example.job_portal.entity.User;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public interface UserDAO {
    List<User> searchUserByEmailPattern(String pattern);
    List<User> searchUserByYearOfExperience(Double yearOfExperience);
    List<User> searchUserByUserCreationDate(Date userCreationDate);
}
