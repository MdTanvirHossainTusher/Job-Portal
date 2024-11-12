package com.example.job_portal.controller;

import com.example.job_portal.dao.JobDAO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.entity.api_response.ApiResponse;
import com.example.job_portal.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    private final JobService jobService;
    private final JobDAO jobDAO;

    public JobController(JobService jobService, JobDAO jobDAO) {
        this.jobService = jobService;
        this.jobDAO = jobDAO;
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<JobDTO> jobs = jobService.findAllJobs();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @PostMapping("/{jobId}/apply")
    public ResponseEntity<?> applyToJob(
            @PathVariable Long jobId,
            @RequestParam(required = true) Long userId
            ) {
        try {
            jobService.applyToJobByUser(jobId, userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<JobDTO>> filterJobs(@RequestParam(required = false) String jobPosition,
                                                         @RequestParam(required = false) String jobLocation) {
        try {
            List<JobDTO> jobs = jobDAO.filterJobs(jobPosition, jobLocation);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
