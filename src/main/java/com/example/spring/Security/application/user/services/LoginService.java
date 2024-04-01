package com.example.spring.Security.application.user.services;

import com.example.spring.Security.application.user.dto.LoginDto;
import com.example.spring.Security.application.user.dto.ResponseDto;

public interface LoginService {

    public ResponseDto login(LoginDto loginDto);
}
