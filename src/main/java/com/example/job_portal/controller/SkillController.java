package com.example.job_portal.controller;

import com.example.job_portal.dto.SkillDTO;
import com.example.job_portal.entity.api_response.ApiResponse;
import com.example.job_portal.service.SkillService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/skills")
@Tag(name = "Skill", description = "Skill Related APIs")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public ResponseEntity<List<SkillDTO>> getAllSkillsUnderApp() {
        List<SkillDTO> skills = skillService.getAllSkillsUnderApp();
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SkillDTO> createSkill(@Valid @RequestBody SkillDTO skillDTO) {
        SkillDTO skill = skillService.createSkill(skillDTO);
        return new ResponseEntity<>(skill, HttpStatus.CREATED);
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<?> deleteSkill(@PathVariable Long skillId) {
        skillService.deleteSkill(skillId);
        return new ResponseEntity<>(
                new ApiResponse("Skill deleted successfully!", true), HttpStatus.OK);
    }
}
