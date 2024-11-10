package com.example.job_portal.controller;

import com.example.job_portal.dto.CVDTO;
import com.example.job_portal.service.CVService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cvs")
public class CVController {
    private final CVService cvService;

    public CVController(CVService cvService) {
        this.cvService = cvService;
    }

    @GetMapping
    public ResponseEntity<List<CVDTO>> getAllCVs() {
        List<CVDTO> cvDTOs =  cvService.getAllCVs();
        return ResponseEntity.ok(cvDTOs);
    }

//    @PostMapping("/upload")
//    public ResponseEntity<CVDTO> uploadCV(
//            @RequestBody CVDTO cvDTO,
//            @RequestParam Long profileId
//    ) {
//        CVDTO cvdto =  cvService.createCV(profileId, cvDTO);
//        return ResponseEntity.ok(cvdto);
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<CVDTO> updateCV(
//            @RequestBody CVDTO cvDTO,
//            @RequestParam Long profileId
//    ) {
//        CVDTO cvdto =  cvService.updateCV(profileId, cvDTO);
//        return ResponseEntity.ok(cvdto);
//    }

}
