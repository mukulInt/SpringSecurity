package com.example.spring.Security.application.user.servicesImpl;

import com.example.spring.Security.application.user.entiry.User;
import com.example.spring.Security.application.user.repository.UserRepository;
import com.example.spring.Security.application.user.dto.LoginDto;
import com.example.spring.Security.application.user.dto.ResponseDto;
import com.example.spring.Security.shared.exception.ServiceException;
import com.example.spring.Security.shared.security.config.JwtService;
import com.example.spring.Security.application.user.services.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServicesImpl implements LoginService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final JwtService jwtService;

  @Override
  public ResponseDto login(LoginDto loginDto) {
    log.info("Attempting login for user: {}", loginDto.getUserName());

    try {
      // Perform authentication
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginDto.getUserName(),
              loginDto.getPassword()
          )
      );

      // Authentication successful, set the SecurityContext
      SecurityContextHolder.getContext().setAuthentication(authentication);

      // Retrieve user details from the repository
      User user = userRepository.findByEmail(loginDto.getUserName())
          .orElseThrow(() -> new ServiceException("User not found", null, HttpStatus.UNAUTHORIZED));

      // Generate JWT token
      String jwtToken = jwtService.generateToken(user);

      log.info("User {} logged in successfully", loginDto.getUserName());
      return ResponseDto.builder().status(true).data(jwtToken).message("SUCCESS").build();
    } catch (Exception e) {
      // Authentication failed
      log.error("Authentication failed for user: {}", loginDto.getUserName(), e);
      throw new ServiceException(e.getMessage(), e, HttpStatus.UNAUTHORIZED);
    }
  }
}



