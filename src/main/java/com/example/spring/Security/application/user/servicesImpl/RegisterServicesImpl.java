package com.example.spring.Security.application.user.servicesImpl;

import com.example.spring.Security.application.user.dto.ResponseDto;
import com.example.spring.Security.application.user.dto.UserRegisterDto;
import com.example.spring.Security.application.user.entiry.Roles;
import com.example.spring.Security.application.user.entiry.User;
import com.example.spring.Security.application.user.repository.UserRepository;
import com.example.spring.Security.shared.security.config.JwtService;
import com.example.spring.Security.application.user.services.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterServicesImpl implements RegisterService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RegisterMapper registerMapper;
    private final UserDaoService userDaoService;



    @Override
    public ResponseDto register(UserRegisterDto request) {

        User user = registerMapper.mapToUser(request);
        log.info("+++++++++++mapped_user+ " + user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDaoService.saveUser(user);

        var token = jwtService.generateToken(user);

        return ResponseDto.builder().data(token).message("SUCCESS").status(true).build();
    }


}
