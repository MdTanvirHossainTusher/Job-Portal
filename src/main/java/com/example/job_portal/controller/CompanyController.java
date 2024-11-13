package com.example.job_portal.controller;

import com.example.job_portal.dao.CompanyDAO;
import com.example.job_portal.dto.*;
import com.example.job_portal.entity.api_response.ApiResponse;
import com.example.job_portal.service.CompanyService;
import com.example.job_portal.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final JobService jobService;
    private final CompanyDAO companyDAO;

    public CompanyController(CompanyService companyService, JobService jobService, CompanyDAO companyDAO) {
        this.companyService = companyService;
        this.jobService = jobService;
        this.companyDAO = companyDAO;
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompany() {
        List<CompanyDTO> companyDTOs = companyService.findAllCompany();
        return new ResponseEntity<>(companyDTOs, HttpStatus.OK);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<?> getCompanyById(@PathVariable Long companyId) {
        CompanyDTO companyDTO = companyService.findCompanyById(companyId);
        return new ResponseEntity<>(
                companyDTO != null  ? companyDTO : new ApiResponse("Company doesn't exists!", false),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        CompanyDTO createdCompany = companyService.createCompany(companyDTO);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyDTO> updateCompany(@RequestBody CompanyDTO companyDTO, @PathVariable Long companyId) {
        CompanyDTO updatedCompany = companyService.updateCompany(companyId, companyDTO);
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long companyId) {
        companyService.deleteCompanyById(companyId);
        return new ResponseEntity<>(
                new ApiResponse("Company deleted successfully", true),
                HttpStatus.OK);
    }

    @GetMapping("/{companyId}/jobs")
    public ResponseEntity<List<JobDTO>> getAllJobsUnderOneCompany(
            @PathVariable Long companyId
    ) {
        return new ResponseEntity<>(companyService.getAllJobsUnderOneCompany(companyId), HttpStatus.OK);
    }

    @GetMapping("/{companyId}/jobs/{jobId}")
    public ResponseEntity<JobDTO> getJobById(
            @PathVariable Long companyId,
            @PathVariable Long jobId) {
        JobDTO jobDTO = jobService.findJobById(companyId, jobId);
        return new ResponseEntity<>(jobDTO, HttpStatus.OK);
    }

    @PostMapping("/{companyId}/jobs")
    public ResponseEntity<JobDTO> createJob(
            @PathVariable Long companyId,
            @RequestBody JobDTO jobDTO) {
        JobDTO createdJob = jobService.createJob(companyId, jobDTO);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    @PutMapping("/{companyId}/jobs/{jobId}")
    public ResponseEntity<JobDTO> updateJob(
            @RequestBody JobDTO jobDTO,
            @PathVariable Long companyId,
            @PathVariable Long jobId) {
        JobDTO updatedJob = jobService.updateJob(companyId, jobId, jobDTO);
        return new ResponseEntity<>(updatedJob, HttpStatus.OK);
    }

    @DeleteMapping("/{companyId}/jobs/{jobId}")
    public ResponseEntity<?> deleteJob(
            @PathVariable Long companyId,
            @PathVariable Long jobId
    ) {
        jobService.deleteJobById(companyId, jobId);
        return new ResponseEntity<>(
                new ApiResponse("Job deleted successfully", true),
                HttpStatus.OK);
    }


    @GetMapping("/{companyId}/jobs/{jobId}/applicants")
    public ResponseEntity<List<ApplicantsDTO>> getAllApplicantsInfoUnderAJobPost(
            @PathVariable Long companyId,
            @PathVariable Long jobId
    ) {
        List<ApplicantsDTO> applicantsDTOList =  companyService.getAllApplicantsInfoUnderAJobPost(companyId, jobId);
        return new ResponseEntity<>(applicantsDTOList, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterCompanies(
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String companyLocation,
            @RequestParam(required = false) String workingMode
    ) {
        try {
            List<CompanyDTO> companies = companyDAO.filterCompanies(companyName, companyLocation, workingMode);
            HttpStatus status = !companies.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(
                    !companies.isEmpty() ?
                            companies :
                            new ApiResponse("No company found!", false), status);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
