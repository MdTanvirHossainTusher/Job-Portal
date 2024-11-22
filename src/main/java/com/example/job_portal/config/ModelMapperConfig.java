package com.example.job_portal.config;

import com.example.job_portal.dto.UserDTO;
import com.example.job_portal.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
//        return new ModelMapper();
        ModelMapper modelMapper = new ModelMapper();

//        modelMapper.addMappings(new PropertyMap<User, UserDTO>() {
//            @Override
//            protected void configure() {
//                map().setProfileImageUrl(source.getImageUrl());
//            }
//        });

        TypeMap<User, UserDTO> typeMap = modelMapper.createTypeMap(User.class, UserDTO.class);
        typeMap.addMappings(mapper -> mapper.map(User::getImageUrl, UserDTO::setProfileImageUrl));

//        TypeMap<Company, CompanyDTO> companyTypeMap = modelMapper.createTypeMap(Company.class, CompanyDTO.class);
//        companyTypeMap.addMappings(mapper -> mapper.map(Company::getLogoUrl, CompanyDTO::setCompanyLogoUrl));

        return modelMapper;
    }
}
