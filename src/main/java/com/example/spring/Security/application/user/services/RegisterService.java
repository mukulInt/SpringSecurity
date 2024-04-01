package com.example.spring.Security.application.user.services;

import com.example.spring.Security.application.user.dto.ResponseDto;
import com.example.spring.Security.application.user.dto.UserRegisterDto;

public interface RegisterService {

    public ResponseDto register(UserRegisterDto userRegisterDto);
}
