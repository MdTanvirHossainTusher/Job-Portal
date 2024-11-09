package com.example.job_portal.repository;

import com.example.job_portal.entity.Company;
import com.example.job_portal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Company c WHERE LOWER(c.companyName) = LOWER(:companyName)")
    boolean existsByName(@Param("companyName") String companyName);

    @Query("SELECT c FROM Company c WHERE LOWER(c.companyName) = LOWER(:companyName)")
    Company findCompanyByName(@Param("companyName") String companyName);

    @Query("SELECT c FROM Company c WHERE c.isDeleted = false")
    List<Company> findAllCompany();

    @Query("SELECT c FROM Company c WHERE c.isDeleted = false AND c.id = :id")
    Optional<Company> findCompanyById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Company c SET c.isDeleted = true WHERE c.id = :id")
    void softDeleteCompanyById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Job j SET j.isDeleted = true WHERE j.company.id = :companyId")
    void softDeleteJobsByCompanyId(@Param("companyId") Long companyId);

}
