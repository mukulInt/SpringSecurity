package com.example.spring.Security.shared.security.filter;

import com.example.spring.Security.shared.security.config.JwtService;
import com.example.spring.Security.shared.utils.AuthenticationUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Custom JWT authentication filter.
 *
 * This filter intercepts incoming requests and performs JWT-based authentication.
 * It extracts the JWT token from the request headers, validates it, and sets
 * the authenticated user in the Spring Security context if the token is valid.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
  private final AuthenticationUtil authenticationUtil;

  /**
   * Method to handle incoming requests and perform JWT-based authentication.
   *
   * @param request The HTTP request.
   * @param response The HTTP response.
   * @param filterChain The filter chain for the request.
   * @throws ServletException If a servlet-specific error occurs.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request, //it is client request
      @NonNull HttpServletResponse response, // and it is response if we want to change, so we can change from here
      //it is the same response which will be consumed by controller
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    // 1. Check if a JWT token exists in the request header
    final String authHeader = request.getHeader("Authorization");

    System.out.println("++++Auth" + authHeader);

    final String jwt;
    final String userEmail;
    // 2. Check if the JWT token is present and has the correct format
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      log.info("Token not found -------------------------");
      filterChain.doFilter(request, response); // Call the next filter in the chain
      // Return because the token does not have "Bearer ", so we will not allow to proceed
      return;
    }

    log.info("Token found -------------------------");

    jwt = authHeader.substring(7); // Extract the token without "Bearer "
    // userEmail = // todo extract the userEmail from Jwt

    userEmail = jwtService.extractUsername(jwt);

    // 3. If the token is valid and the user is not already authenticated, set the user in the Security Context
    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

      log.info("userDetails = " + userDetails);

      if (jwtService.isTokenValid(jwt, userDetails)) {
        log.info("Token is valid -------------------");
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
        authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
        log.info("Successfully set in SecurityContext---------------");
        log.info("-------------" + authenticationUtil.currentLoggedInUser());
      }

    }
    // Continue with the filter chain
    filterChain.doFilter(request, response);
  }
}
