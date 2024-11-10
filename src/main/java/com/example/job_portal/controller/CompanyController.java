package com.example.job_portal.controller;

import com.example.job_portal.dto.ApplicantsDTO;
import com.example.job_portal.dto.CompanyDTO;
import com.example.job_portal.dto.JobDTO;
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

    public CompanyController(CompanyService companyService, JobService jobService) {
        this.companyService = companyService;
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompany() {
        List<CompanyDTO> companyDTOs = companyService.findAllCompany();
        return new ResponseEntity<>(companyDTOs, HttpStatus.OK);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long companyId) {
        CompanyDTO companyDTO = companyService.findCompanyById(companyId);
        return new ResponseEntity<>(companyDTO, HttpStatus.OK);
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


        try {
            JobDTO createdJob = jobService.createJob(companyId, jobDTO);
            return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

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
    public ResponseEntity<List<ApplicantsDTO>> getAllApplicantsUnderAJobPost(
            @PathVariable Long companyId,
            @PathVariable Long jobId
    ) {
        List<ApplicantsDTO> applicantsDTOList =  companyService.getAllApplicantsUnderAJobPost(companyId, jobId);
        return new ResponseEntity<>(applicantsDTOList, HttpStatus.OK);
    }


//    @GetMapping("/{companyId}/jobs/{jobId}/apply")
//    public ResponseEntity<Void> applyToJob(
//            @PathVariable Long companyId,
//            @PathVariable Long jobId,
//            @RequestBody Long userId
//    ) {
//        try {
//            jobService.applyToJobByUser(companyId, jobId, userId);
//            return ResponseEntity.noContent().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }


//    @GetMapping("/filter")
//    public ResponseEntity<List<CompanyDTO>> filterFromCompanys(@RequestParam(required = false) String email,
//                                                       @RequestParam(required = false) Double experience,
//                                                       @RequestParam(required = false) String universityName) {
//        try {
//            List<CompanyDTO> companys = companyDAO.filterCompanys(email, experience, universityName);
//            return new ResponseEntity<>(companys, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
