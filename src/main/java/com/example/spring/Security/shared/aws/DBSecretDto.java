package com.example.spring.Security.shared.aws;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DBSecretDto {
  private String username;
  private String password;
  private String host;
  private String engine;
  private String port;
  private String dbInstanceIdentifier;
}
