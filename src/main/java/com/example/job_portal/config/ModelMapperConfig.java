package com.example.job_portal.config;

import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<User, UserDTO> typeMap = modelMapper.createTypeMap(User.class, UserDTO.class);
        typeMap.addMappings(mapper -> mapper.map(User::getImageUrl, UserDTO::setProfileImageUrl));
        return modelMapper;
    }
}
