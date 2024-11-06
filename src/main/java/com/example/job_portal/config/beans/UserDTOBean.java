package com.example.job_portal.config.beans;

import com.example.job_portal.dto.UserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDTOBean {
    @Bean
    public UserDTO getUserDTOBean() {
        return new UserDTO();
    }
}
