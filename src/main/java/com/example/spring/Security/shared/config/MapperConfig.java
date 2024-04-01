package com.example.spring.Security.shared.config;

import com.example.spring.Security.application.user.servicesImpl.RegisterMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.mapstruct.factory.Mappers;

@Configuration
public class MapperConfig {

  @Bean
  public RegisterMapper registerMapper() {
    return Mappers.getMapper(RegisterMapper.class);
  }
}