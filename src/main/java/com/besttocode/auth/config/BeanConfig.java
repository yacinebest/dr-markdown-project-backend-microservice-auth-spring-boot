package com.besttocode.auth.config;

import com.besttocode.auth.dtos.UserInfoDTO;
import com.besttocode.auth.models.UserModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        final TypeMap<UserModel, UserInfoDTO> markdownUserModelTypeMap = modelMapper.typeMap(UserModel.class, UserInfoDTO.class);
        markdownUserModelTypeMap.addMappings(mapping -> mapping.skip(UserInfoDTO::setPassword));

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();

        config.applyPermitDefaultValues();

        config.addAllowedMethod(HttpMethod.PUT);
        config.addAllowedMethod(HttpMethod.DELETE);
        config.addAllowedMethod(HttpMethod.OPTIONS);

        configSource.registerCorsConfiguration("/**", config);

        return new CorsFilter(configSource);
    }
}

