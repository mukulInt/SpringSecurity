package com.example.spring.Security.application.user.controller;

import com.example.spring.Security.application.user.dto.LoginDto;
import com.example.spring.Security.application.user.dto.ResponseDto;
import com.example.spring.Security.application.user.dto.UserRegisterDto;
import com.example.spring.Security.application.user.services.LoginService;
import com.example.spring.Security.application.user.services.RegisterService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final RegisterService registerService;
  private final LoginService loginService;


  @PostMapping("/register")
  public ResponseDto registerUser(@RequestBody @Valid UserRegisterDto request) {
    return registerService.register(request);
  }

  @PostMapping("/login")
  public ResponseDto loginUser(@RequestBody @Valid LoginDto loginDto) {
    return loginService.login(loginDto);
  }


  @GetMapping("/admin/get")
  @SecurityRequirement(name = "bearer-key")
  public String getByAdmin() {
    return "Hi admin";
  }


}
