package com.example.job_portal.dao;

import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.Job;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface JobDAO {
//    List<Job> filterJobByJobLocation(String jobLocation);
//    List<Job> filterJobByJobPosition(String jobPosition);
//    List<Job> filterJobByJobPosition(String jobPosition);

    List<JobDTO> filterJobs(String jobPosition, String jobLocation);

}
