package com.example.spring.Security.shared.security.config;

import com.example.spring.Security.application.user.entiry.Roles;
import com.example.spring.Security.shared.security.filter.CustomAccessDeniedHandler;
import com.example.spring.Security.shared.security.filter.JwtAuthenticationEntryPoint;
import com.example.spring.Security.shared.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final CustomAccessDeniedHandler accessDeniedHandler;

  @Value("${settings.cors.origin}")
  private String corsOrigin;


  private static final String[] WHITELISTED_ENDPOINTS = {
      "/user/login",
      "/user/register",
      "/swagger-ui.html",
      "/swagger-ui/**",
      "/v3/api-docs/**",
      "/demo/test"
      // Add more APIs as neede
  };

  private static final String[] USER_ACCESS = {
      "demo/401"
  };
  private static final String[] SUPER_ADMIN_ACCESS = {
      "/bandhan/admin/admins"
  };

  private static final String[] ADMIN_ACCESS = {
      "/user/admin",
      "/user/admin/get"
  };


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf()
        .disable()
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(WHITELISTED_ENDPOINTS).permitAll()
            .requestMatchers(USER_ACCESS).hasAuthority(Roles.ROLE_USER.name())
            .requestMatchers(ADMIN_ACCESS).hasAuthority(Roles.ROLE_ADMIN.name())
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .exceptionHandling(exceptionHandling -> exceptionHandling
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)

        )
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


    return http.build();
  }


  @Bean
  CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList(corsOrigin));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Requestor-Type", "Content-Type", "ApiKey", "authentication-token", "Apikey", "Authentication-Token"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
