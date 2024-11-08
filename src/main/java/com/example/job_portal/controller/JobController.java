package com.example.job_portal.controller;

import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.api_response.ApiResponse;
import com.example.job_portal.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
//    private final JobService jobService;
//
//    public JobController(JobService jobService) {
//        this.jobService = jobService;
//    }

//    @GetMapping
//    public ResponseEntity<List<JobDTO>> getAllJob() {
//        List<JobDTO> jobDTOs = jobService.findAllJobs();
//        return new ResponseEntity<>(jobDTOs, HttpStatus.OK);
//    }

//    @GetMapping("/{jobId}")
//    public ResponseEntity<JobDTO> getJobById(@PathVariable Long jobId) {
//        JobDTO jobDTO = jobService.findJobById(jobId);
//        return new ResponseEntity<>(jobDTO, HttpStatus.OK);
//    }

//    @PostMapping
//    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO jobDTO) {
//        JobDTO createdJob = jobService.createJob(jobDTO);
//        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
//    }

//    @PutMapping("/{jobId}")
//    public ResponseEntity<JobDTO> updateJob(@RequestBody JobDTO jobDTO, @PathVariable Long jobId) {
//        JobDTO updatedJob = jobService.updateJob(jobId, jobDTO);
//        return new ResponseEntity<>(updatedJob, HttpStatus.OK);
//    }

//    @DeleteMapping("/{jobId}")
//    public ResponseEntity<?> deleteJob(@PathVariable Long jobId) {
//        jobService.deleteJobById(jobId);
//        return new ResponseEntity<>(
//                new ApiResponse("Job deleted successfully", true),
//                HttpStatus.OK);
//    }

//    @GetMapping("/filter")
//    public ResponseEntity<List<JobDTO>> filterFromJobs(@RequestParam(required = false) String email,
//                                                       @RequestParam(required = false) Double experience,
//                                                       @RequestParam(required = false) String universityName) {
//        try {
//            List<JobDTO> jobs = jobDAO.filterJobs(email, experience, universityName);
//            return new ResponseEntity<>(jobs, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @GetMapping("/{jobId}/apply")
//    public ResponseEntity<Void> applyToJob(
//            @PathVariable Long jobId,
//            @RequestBody Long userId
//    ) {
//        try {
//            jobService.applyToJobByUserBy(jobId, userId);
//            return ResponseEntity.noContent().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

//    @PostMapping("/{jobId}/add-role")
//    public ResponseEntity<Void> addNewRoleToJob(
//            @PathVariable("jobId") Long jobId,
//            @RequestParam(required = true) String roleName
//    ) {
//        try {
//            roleService.addRoleToJob(jobId, roleName);
//            return ResponseEntity.noContent().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @DeleteMapping("/{jobId}/delete-role")
//    public ResponseEntity<Void> deleteJobRole(
//            @PathVariable("jobId") Long jobId,
//            @RequestParam(required = true) String roleName
//    ) {
//        try {
//            jobService.deleteJobRole(jobId, roleName);
//            return ResponseEntity.noContent().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}
