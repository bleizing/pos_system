package com.bleizing.pos.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

@Service
public class StorageService {
	@Autowired
    private MinioClient minioClient;
	
	@Value("${minio.bucket.name}")
    private String defaultBucketName;
	
	public String uploadFile(MultipartFile file) throws IOException {
		return uploadFile(defaultBucketName, file);
    }
	
	public String uploadFile(String bucketName, MultipartFile file) throws IOException {
        return uploadFile(bucketName, file.getOriginalFilename(), file.getInputStream(), file.getContentType());
    }
	
    private String uploadFile(String bucketName, String objectName, InputStream inputStream, String contentType) {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            
            Long currentMillis = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            objectName = currentMillis + "_" + objectName;
            
            minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                        inputStream, inputStream.available(), -1)
                        .contentType(contentType)
                        .build());
            
            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("uploadFile Error: " + e.getMessage());
        }
    }
}
