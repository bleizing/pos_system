package com.bleizing.pos.interfaces;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.web.multipart.MultipartFile;

import com.bleizing.pos.util.PasswordUtil;

public interface StoragePlatform {
	String uploadFile(String subfolder, String objectName, InputStream inputStream, String contentType) throws Exception;
	
	boolean bucketExist(String bucketName) throws Exception;
	
	void createBucket(String bucketName) throws Exception;
	
	boolean deleteFile(String objectName) throws Exception;
	
	String getFullPath(String objectName);
	
	default String uploadFile(String subfolder, MultipartFile file) throws Exception {
		return uploadFile(subfolder, file.getOriginalFilename(), file.getInputStream(), file.getContentType());
	}
	
	default String createObjectName(String subfolder, String objectName) throws Exception {
		Long currentMillis = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        objectName = objectName.replaceAll("\\s+", "_").toLowerCase();
        objectName = PasswordUtil.hashString(currentMillis + "_" + objectName);
        objectName = subfolder + "/" + objectName;
        return objectName;
	}
	
	default String getFullPath(String baseUrl, String bucketName, String objectName) {
    	StringBuilder sb = new StringBuilder();
    	sb.append(baseUrl);
    	sb.append(bucketName);
    	sb.append("/");
    	sb.append(objectName);
    	return sb.toString();
    }
}
