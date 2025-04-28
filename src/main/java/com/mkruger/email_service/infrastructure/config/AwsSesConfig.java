package com.mkruger.email_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
public class AwsSesConfig {

	@Bean
	public SesClient sesClient(
			@Value("${aws.region:sa-east-1}") String region,
			@Value("${aws.ses.access-key-id}") String accessKeyId,
			@Value("${aws.ses.secret-access-key}") String secretAccessKey
	) {
		return SesClient.builder()
				.region(Region.of(region))
				.credentialsProvider(StaticCredentialsProvider.create(
						AwsBasicCredentials.create(accessKeyId, secretAccessKey)
				))
				.build();
	}
}
