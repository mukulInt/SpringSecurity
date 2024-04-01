package com.example.spring.Security.shared.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.example.spring.Security.shared.exception.ServiceException;
import com.example.spring.Security.shared.utils.ConstantPropertiesUtil;
import com.example.spring.Security.shared.utils.Parser;
import com.google.gson.Gson;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Slf4j
@Primary
@Component
@Configuration
@RequiredArgsConstructor
public class DBSecret {
  private final Parser parser;
  private final ConstantPropertiesUtil properties;

  @Bean
  @Primary
  public DataSource dataSource() {
    DBSecretDto secrets = getSecret();
    DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
    dataSourceBuilder
        .driverClassName("org.postgresql.Driver")
        .url("jdbc:postgresql://" + secrets.getHost() + ":" + secrets.getPort() + "/security6?hibernate.dialect=org.hibernate.dialect.PostgreSQL95Dialect")
        .username(secrets.getUsername())
        .password(secrets.getPassword())
        .type(HikariDataSource.class); // Specify the DataSource type if needed
    return dataSourceBuilder.build();
  }


  public DBSecretDto getSecret() {
    String secretName = "dev/db";
    AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
        .withRegion(properties.getRegion())
        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretkey())))
        .build();

    GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
        .withSecretId(secretName);
    GetSecretValueResult getSecretValueResult = null;
    try {
      getSecretValueResult = client.getSecretValue(getSecretValueRequest);
    } catch (Exception e) {
      throw new ServiceException("UNABLE_TO_PROCESS_ERR", e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    if (getSecretValueResult.getSecretString() != null) {
      log.info("SECRETS FORM AWS SECRETS MANAGER " + getSecretValueResult);
      return parser.parseResponseToType(getSecretValueResult.getSecretString(), DBSecretDto.class);
    }
    return null;
  }

}


//  @Bean
//  public DataSource dataSource() {
//    DBSecretDto secrets = getSecret();
//    DataSource build = DataSourceBuilder
//        .create()
//        .driverClassName("org.postgresql.Driver")
//        .url("jdbc:postgresql://" + secrets.getHost() + ":" + secrets.getPort() + "/security6")
//        .username(secrets.getUsername())
//        .password(secrets.getPassword())
//        .build();
//
//
//    System.out.println("---------------build =" + build);
//
//
//    return build;
//  }