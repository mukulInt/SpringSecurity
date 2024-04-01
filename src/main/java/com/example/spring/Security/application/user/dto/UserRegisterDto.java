package com.example.spring.Security.application.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDto {

  @NotBlank(message = "First name cannot be blank")
  private String firstName;
  @NotBlank
  private String lastName;
  @Email
  private String email;
  @NotBlank
  private String password;

}
