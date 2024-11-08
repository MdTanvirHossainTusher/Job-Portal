package com.example.job_portal.repository;

import com.example.job_portal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email AND u.isDeleted = false")
    boolean existsByEmail(@Param("email") String email);


    @Query("SELECT u FROM User u WHERE u.isDeleted = false")
    List<User> findAllUser();


    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.id = :id")
    Optional<User> findUserById(@Param("id") Long id);


    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = :id")
    void softDeleteUserById(@Param("id") Long id);

}
