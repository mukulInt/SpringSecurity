package com.example.spring.Security.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {

    @NotBlank
    @Schema(description = "userName" ,example = "krishna@gmail.com")
    private String userName;

    @NotBlank
    @Schema(description = "password" ,example = "Password@123")
    private String password;
}
