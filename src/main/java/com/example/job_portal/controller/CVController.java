package com.example.job_portal.controller;

import com.example.job_portal.dto.CVDTO;
import com.example.job_portal.service.CVService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/cvs")
@Tag(name = "CV", description = "CV Related APIs")
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

}
