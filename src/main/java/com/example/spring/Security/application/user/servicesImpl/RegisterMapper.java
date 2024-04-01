package com.example.spring.Security.application.user.servicesImpl;

import com.example.spring.Security.application.user.dto.UserRegisterDto;
import com.example.spring.Security.application.user.entiry.Roles;
import com.example.spring.Security.application.user.entiry.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Configuration;

@Mapper
@Configuration
public interface RegisterMapper {

//  RegisterMapper INSTANCE = Mappers.getMapper(RegisterMapper.class);
//  @Mapping(source = "email", target = "email")
//  @Mapping(source = "firstName", target = "firstName")
//  @Mapping(source = "lastName", target = "lastName")
//  @Mapping(source = "password", target = "password")
//  User mapToUser(UserRegisterDto request);


  default User mapToUser(UserRegisterDto request) {
    return User
        .builder()
        .email(request.getEmail())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .password(request.getPassword())
        .role(Roles.ROLE_USER)
        .build();
  }
}