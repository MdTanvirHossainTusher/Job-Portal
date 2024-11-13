package com.example.job_portal.controller;

import com.example.job_portal.dao.JobDAO;
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
        jobService.applyToJobByUser(jobId, userId);
        return new ResponseEntity<>(
                new ApiResponse("You have applied to the job successfully!", true),
                HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterJobs(@RequestParam(required = false) String jobPosition,
                                                         @RequestParam(required = false) String jobLocation) {
        try {
            List<JobDTO> jobs = jobDAO.filterJobs(jobPosition, jobLocation);
            HttpStatus status = !jobs.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(
                    !jobs.isEmpty() ?
                            jobs :
                            new ApiResponse("No jobs found!", false), status);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
