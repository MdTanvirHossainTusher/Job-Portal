package com.example.job_portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUniversityId implements Serializable {
    
    @Column(name = "profile_id")
    private Long profileId;
    
    @Column(name = "university_id")
    private Long universityId;
}