package com.example.job_portal.controller;

import com.example.job_portal.dto.CVDTO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.service.CVService;
import com.example.job_portal.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final CVService cvService;
    private final ProfileService profileService;

    public ProfileController(CVService cvService, ProfileService profileService) {
        this.cvService = cvService;
        this.profileService = profileService;
    }

    @GetMapping("{profileId}/cv")
    public ResponseEntity<CVDTO> getUserCV(@PathVariable Long profileId) {
        CVDTO cvDTO =  cvService.getUserCV(profileId);
        return ResponseEntity.ok(cvDTO);
    }

    @PostMapping("{profileId}/cv")
    public ResponseEntity<CVDTO> uploadCV(
            @RequestBody CVDTO cvDTO,
            @PathVariable Long profileId
    ) {
        CVDTO cvdto =  cvService.createCV(profileId, cvDTO);
        return ResponseEntity.ok(cvdto);
    }

    @PutMapping("{profileId}/cv")
    public ResponseEntity<CVDTO> updateCV(
            @RequestBody CVDTO cvDTO,
            @PathVariable Long profileId
    ) {
        CVDTO cvdto =  cvService.updateCV(profileId, cvDTO);
        return ResponseEntity.ok(cvdto);
    }

    @GetMapping("/{profileId}/jobs/applied-on")
    public ResponseEntity<List<JobDTO>> getAllJobsUserAppliedOn(
            @PathVariable Long profileId
    ) {
        List<JobDTO> jobs = profileService.getAllJobsUserAppliedOn(profileId);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

}
