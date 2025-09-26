package com.example.project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;

@Service
public class S3Service {
  private final S3Client s3;
  private final String bucket;

  public S3Service(@Value("${aws.region}") String region,
                   @Value("${aws.s3.bucket}") String bucket) {
    this.s3 = S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();
    this.bucket = bucket;
  }

  public String upload(String key, InputStream input, long contentLength, String contentType) {
    PutObjectRequest por = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .contentType(contentType)
            .contentLength(contentLength)
            .build();
    s3.putObject(por, RequestBody.fromInputStream(input, contentLength));
    return key;
  }
}
