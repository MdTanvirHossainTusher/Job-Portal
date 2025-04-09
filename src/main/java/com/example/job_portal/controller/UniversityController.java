package com.example.job_portal.controller;

import com.example.job_portal.dto.UniversityDTO;
import com.example.job_portal.entity.api_response.ApiResponse;
import com.example.job_portal.service.UniversityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/universities")
public class UniversityController {

    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping
    public ResponseEntity<List<UniversityDTO>> getAllUniversitiesUnderApp() {
        List<UniversityDTO> universities = universityService.getAllUniversitiesUnderApp();
        return new ResponseEntity<>(universities, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UniversityDTO> createUniversity(@RequestBody UniversityDTO universityDTO) {
        UniversityDTO university = universityService.createUniversity(universityDTO);
        return new ResponseEntity<>(university, HttpStatus.CREATED);
    }

    @DeleteMapping("/{universityId}")
    public ResponseEntity<?> deleteUniversity(@PathVariable Long universityId) {
        universityService.deleteUniversity(universityId);
        return new ResponseEntity<>(
                new ApiResponse("University deleted successfully!", true), HttpStatus.OK);
    }
}
