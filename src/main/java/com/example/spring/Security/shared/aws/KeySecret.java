package com.example.spring.Security.shared.aws;

import com.example.spring.Security.shared.utils.ConstantPropertiesUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
@Slf4j
@Component
@RequiredArgsConstructor
public class KeySecret {
    private final ConstantPropertiesUtil properties;


    @Value("${aws.region}")
    private String region;

    @Value("${aws.secret.name}")
    private String secretName;

    public String getSecret() {

        try (SecretsManagerClient client = SecretsManagerClient.builder()
                .region(Region.of(properties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(properties.getSecretkey(), properties.getSecretkey())))
                .build()) {

            GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                    .secretId(properties.getSecretName())
                    .build();

            GetSecretValueResponse getSecretValueResponse = client.getSecretValue(getSecretValueRequest);

            // Log or return the retrieved secret
            String secret = getSecretValueResponse.secretString();
            log.info("Retrieved secret: " + secret);
            return secret;

        } catch (Exception e) {
            log.info("Error retrieving secret from AWS Secrets Manager: " + e.getMessage());
            throw new RuntimeException("Error retrieving secret from AWS Secrets Manager", e);
        }
    }
}
