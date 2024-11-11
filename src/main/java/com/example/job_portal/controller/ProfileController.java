package com.example.job_portal.controller;

import com.example.job_portal.dto.CVDTO;
import com.example.job_portal.dto.JobApplicationDTO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.dto.SkillDTO;
import com.example.job_portal.entity.api_response.ApiResponse;
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
    public ResponseEntity<List<JobApplicationDTO>> getAllJobsUserAppliedOn(
            @PathVariable Long profileId
    ) {
        List<JobApplicationDTO> jobs = profileService.getAllJobsUserAppliedOn(profileId);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/{profileId}/skills")
    public ResponseEntity<List<SkillDTO>> getAllSkillsOfUser(
            @PathVariable Long profileId
    ) {
        List<SkillDTO> skills = profileService.getAllSkillsUnderProfile(profileId);
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @PostMapping("/{profileId}/skills/{skillId}")
    public ResponseEntity<?> addSkillToUserProfile(
            @PathVariable Long profileId,
            @PathVariable Long skillId
    ) {
        profileService.addSkillToUserProfile(profileId, skillId);
        return new ResponseEntity<>(
                new ApiResponse("Skill added successfully!", true), HttpStatus.OK);
    }



//    @GetMapping("/{profileId}/skills/{skillId}")
//    public ResponseEntity<SkillDTO> getAllSkillsOfUser(
//            @PathVariable Long profileId,
//            @PathVariable Long skillId
//    ) {
//        List<SkillDTO> skills = profileService.getSki(profileId);
//        return new ResponseEntity<>(skills, HttpStatus.OK);
//    }

}
