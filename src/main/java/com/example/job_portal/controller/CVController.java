package com.example.job_portal.controller;

import com.example.job_portal.dto.CVDTO;
import com.example.job_portal.service.CVService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cvs")
public class CVController {
    private final CVService cvService;

    public CVController(CVService cvService) {
        this.cvService = cvService;
    }

    @PostMapping("/upload/{profileId}")
    public ResponseEntity<CVDTO> uploadCV(
            @PathVariable Long profileId,
            @RequestBody CVDTO cvDTO
    ) {
        CVDTO cvdto =  cvService.createCV(profileId, cvDTO);
        return ResponseEntity.ok(cvdto);
    }
}
