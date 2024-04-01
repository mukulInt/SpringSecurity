package com.example.spring.Security.shared.security.filter;

import com.example.spring.Security.shared.utils.AuthenticationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

/**
 * Custom authentication entry point for JWT-based authentication.
 *
 * This class handles authentication errors that occur during JWT-based authentication.
 * When authentication fails due to missing or invalid tokens, this entry point is invoked
 * to send a 401 Unauthorized response to the client.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  /**
   * Invoked when authentication fails due to missing or invalid tokens.
   * Sends a 401 Unauthorized response to the client.
   *
   * @param request The HTTP request.
   * @param response The HTTP response.
   * @param authException The authentication exception that occurred.
   * @throws IOException If an I/O error occurs while handling the response.
   * @throws ServletException If a servlet-specific error occurs while handling the response.
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    System.out.println("32---------" + Arrays.toString(authException.getStackTrace()));
    System.out.println("32---------" + authException.getMessage());
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }
}