package com.example.spring.Security.shared.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ConstantPropertiesUtil {

  @Value("${aws.accessKeyId}")
  private String accessKey;

  @Value("${aws.secretKey}")
  private String secretkey;

  @Value("${aws.region}")
  private String region;

  @Value("${aws.accessKeyId}")
  private String accessKeyId;

  @Value("${aws.secret.name}")
  private String secretName;


}
