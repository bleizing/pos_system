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
import io.minio.RemoveObjectArgs;

@Service
public class StorageService {
	@Autowired
    private MinioClient minioClient;
	
	@Value("${minio.bucket.name}")
    private String defaultBucketName;
	
	public String uploadFile(MultipartFile file) throws IOException {
		return uploadFile(defaultBucketName, file);
    }
	
	public String uploadFile(String subfolder, MultipartFile file) throws IOException {
        return uploadFile(subfolder, file.getOriginalFilename(), file.getInputStream(), file.getContentType());
    }
	
    private String uploadFile(String subfolder, String objectName, InputStream inputStream, String contentType) {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(defaultBucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(defaultBucketName).build());
            }
            
            Long currentMillis = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            objectName = objectName.replaceAll("\\s+", "_").toLowerCase();
            objectName = subfolder + "/" + currentMillis + "_" + objectName;
            
            minioClient.putObject(
                PutObjectArgs.builder().bucket(defaultBucketName).object(objectName).stream(
                        inputStream, inputStream.available(), -1)
                        .contentType(contentType)
                        .build());
            
            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("uploadFile Error: " + e.getMessage());
        }
    }
    
    public boolean deletFile(String objectName) throws Exception {
    	if (!objectName.contains("/")) {
    		throw new Exception("Invalid file name");
    	}
    	
    	try {
    		minioClient.removeObject(RemoveObjectArgs.builder().bucket(defaultBucketName).object(objectName).build());
    	} catch (Exception e) {
			throw new Exception("deleteFile Error: " + e.getMessage());
		}
    	return true;
    }
}
