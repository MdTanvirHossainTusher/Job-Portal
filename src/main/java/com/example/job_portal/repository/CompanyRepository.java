package com.example.job_portal.repository;

import com.example.job_portal.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Company c WHERE c.companyName = :companyName")
    boolean existsByName(@Param("companyName") String companyName);

    @Query("SELECT c FROM Company c WHERE LOWER(c.companyName) = LOWER(:companyName)")
    Company findCompanyByName(@Param("companyName") String companyName);
}
