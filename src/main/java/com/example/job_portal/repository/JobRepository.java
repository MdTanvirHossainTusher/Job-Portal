package com.example.job_portal.repository;

import com.example.job_portal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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


    @Query("SELECT j FROM Job j WHERE j.company.id = :companyId AND j.id = :jobId AND j.isDeleted = false")
    Optional<Job> findJobByIdAndCompanyId(
            @Param("jobId") Long jobId,
            @Param("companyId") Long companyId);

    @Modifying
    @Query("UPDATE Job j SET j.isDeleted = true WHERE j.id = :jobId AND j.company.id = :companyId AND j.isDeleted = false")
    void softDeleteJob(
            @Param("companyId") Long companyId,
            @Param("jobId") Long jobId);

    @Query("SELECT j FROM Job j WHERE j.id = :jobId AND j.isDeleted = false")
    Optional<Job> findJobById(@Param("jobId") Long jobId);

    @Query("SELECT j FROM Job j WHERE j.isDeleted = false")
    List<Job> findAllJobs();
}
