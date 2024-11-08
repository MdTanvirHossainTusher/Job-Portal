package com.example.job_portal.controller;

import com.example.job_portal.dto.CompanyDTO;
import com.example.job_portal.dto.CompanyDTO;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.entity.api_response.ApiResponse;
import com.example.job_portal.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompany() {
        List<CompanyDTO> companyDTOs = companyService.findAllCompany();
        return new ResponseEntity<>(companyDTOs, HttpStatus.OK);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long companyId) {
        CompanyDTO companyDTO = companyService.findCompanyById(companyId);
        return new ResponseEntity<>(companyDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        CompanyDTO createdCompany = companyService.createCompany(companyDTO);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyDTO> updateCompany(@RequestBody CompanyDTO companyDTO, @PathVariable Long companyId) {
        CompanyDTO updatedCompany = companyService.updateCompany(companyId, companyDTO);
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long companyId) {
        companyService.deleteCompanyById(companyId);
        return new ResponseEntity<>(
                new ApiResponse("Company deleted successfully", true),
                HttpStatus.OK);
    }

//    @GetMapping("/filter")
//    public ResponseEntity<List<CompanyDTO>> filterFromCompanys(@RequestParam(required = false) String email,
//                                                       @RequestParam(required = false) Double experience,
//                                                       @RequestParam(required = false) String universityName) {
//        try {
//            List<CompanyDTO> companys = companyDAO.filterCompanys(email, experience, universityName);
//            return new ResponseEntity<>(companys, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @GetMapping("/{companyId}/jobs")
    public ResponseEntity<List<JobDTO>> getAllJobsUnderOneCompany(@PathVariable Long companyId) {
        return new ResponseEntity<>(companyService.getAllJobsUnderOneCompany(companyId), HttpStatus.OK);
    }

//    @PostMapping("/{companyId}/add-role")
//    public ResponseEntity<Void> addNewRoleToCompany(
//            @PathVariable("companyId") Long companyId,
//            @RequestParam(required = true) String roleName
//    ) {
//        try {
//            roleService.addRoleToCompany(companyId, roleName);
//            return ResponseEntity.noContent().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @DeleteMapping("/{companyId}/delete-role")
//    public ResponseEntity<Void> deleteCompanyRole(
//            @PathVariable("companyId") Long companyId,
//            @RequestParam(required = true) String roleName
//    ) {
//        try {
//            companyService.deleteCompanyRole(companyId, roleName);
//            return ResponseEntity.noContent().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}
