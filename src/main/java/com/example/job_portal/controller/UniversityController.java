package com.example.job_portal.controller;

import com.example.job_portal.dto.request.UniversityCreateRequestDTO;
import com.example.job_portal.dto.response.UniversityCreateResponseDTO;
import com.example.job_portal.entity.api_response.ApiResponse;
import com.example.job_portal.service.UniversityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/universities")
@Tag(name = "University", description = "University Related APIs")
public class UniversityController {

    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping
    public ResponseEntity<List<UniversityCreateResponseDTO>> getAllUniversitiesUnderApp() {
        List<UniversityCreateResponseDTO> universities = universityService.getAllUniversitiesUnderApp();
        return new ResponseEntity<>(universities, HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<UniversityDTO> createUniversity(@Valid @RequestBody UniversityDTO universityDTO) {
//        UniversityDTO university = universityService.createUniversity(universityDTO);
//        return new ResponseEntity<>(university, HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<UniversityCreateResponseDTO> createUniversity(@Valid @RequestBody UniversityCreateRequestDTO universityCreateDTO) {
        UniversityCreateResponseDTO university = universityService.createUniversity(universityCreateDTO);
        return new ResponseEntity<>(university, HttpStatus.CREATED);
    }

    @DeleteMapping("/{universityId}")
    public ResponseEntity<?> deleteUniversity(@PathVariable Long universityId) {
        universityService.deleteUniversity(universityId);
        return new ResponseEntity<>(
                new ApiResponse("University deleted successfully!", true), HttpStatus.OK);
    }
}
