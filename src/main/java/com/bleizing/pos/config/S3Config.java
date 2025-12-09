//package com.bleizing.pos.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.AwsCredentials;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//
//@Configuration
//public class S3Config {
//	@Value("${aws.access.key}")
//	String accessKey;
//
//   @Value("${aws.secret.key}")
//   String secretKey;	
//
//   @Bean
//   public S3Client s3Client() {
//	   return S3Client
//			   .builder()
//			   .region(Region.AP_SOUTHEAST_1)
//			   .credentialsProvider(StaticCredentialsProvider.create(getAwsCredentials()))
//			   .build();
//   }
//   
//   private AwsCredentials getAwsCredentials() {
//	   return AwsBasicCredentials.create(accessKey, secretKey);
//   }
//}
