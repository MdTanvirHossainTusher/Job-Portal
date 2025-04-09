package com.example.job_portal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long id;
    private String roleName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean isDeleted = false;
}
