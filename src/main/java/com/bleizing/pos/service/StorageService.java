package com.bleizing.pos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bleizing.pos.interfaces.StoragePlatform;

@Service
public class StorageService {
	@Autowired
	private StoragePlatform storagePlatform;
	
	public String uploadFile(String subfolder, MultipartFile file) throws Exception {
        return storagePlatform.uploadFile(subfolder, file.getOriginalFilename(), file.getInputStream(), file.getContentType());
    }
	
	public boolean deleteFile(String objectName) throws Exception {
		return storagePlatform.deleteFile(objectName);
	}
	
	public String getFullPath(String objectName) {
		return storagePlatform.getFullPath(objectName);
	}
}
