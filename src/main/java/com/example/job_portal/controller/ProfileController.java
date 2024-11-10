package com.example.job_portal.controller;

import com.example.job_portal.dto.CVDTO;
import com.example.job_portal.service.CVService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final CVService cvService;

    public ProfileController(CVService cvService) {
        this.cvService = cvService;
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

}
