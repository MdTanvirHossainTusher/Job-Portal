package com.example.job_portal.repository;

import com.example.job_portal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT CASE WHEN COUNT(j) > 0 THEN true ELSE false END " +
            "FROM Job j WHERE j.jobTitle = :jobTitle " +
            "AND j.jobDescription = :jobDescription " +
            "AND j.salary = :salary " +
            "AND j.jobPosition = :jobPosition " +
            "AND j.jobLocation = :jobLocation")
    boolean existsSimilarJob(@Param("jobTitle") String jobTitle,
                             @Param("jobDescription") String jobDescription,
                             @Param("salary") String salary,
                             @Param("jobPosition") String jobPosition,
                             @Param("jobLocation") String jobLocation);
}
