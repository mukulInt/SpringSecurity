package com.example.spring.Security.shared.utils;

import com.example.spring.Security.application.user.entiry.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtil {
  public User currentLoggedInUser() {
    return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}

