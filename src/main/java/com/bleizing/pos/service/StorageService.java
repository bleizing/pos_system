package com.bleizing.pos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bleizing.pos.interfaces.StoragePlatform;

@Service
public class StorageService {
//	@Autowired
//    private MinioClient minioClient;
//	
//	@Value("${minio.bucket.name}")
//    private String defaultBucketName;
//	
//	@Autowired
//	private RedisUtil redisUtil;
	
	@Autowired
	private StoragePlatform storagePlatform;
	
//	public String uploadFile(MultipartFile file) throws Exception {
//		return uploadFile(defaultBucketName, file);
//		return storagePlatform.uploadFile(defaultBucketName, file);
//    }
	
	public String uploadFile(String subfolder, MultipartFile file) throws Exception {
        return storagePlatform.uploadFile(subfolder, file.getOriginalFilename(), file.getInputStream(), file.getContentType());
    }
	
	public boolean deleteFile(String objectName) throws Exception {
		return storagePlatform.deleteFile(objectName);
	}
	
	public String getFullPath(String objectName) {
		return storagePlatform.getFullPath(objectName);
	}
	
//    private String uploadFile(String subfolder, String objectName, InputStream inputStream, String contentType) {
//        try {
//            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(defaultBucketName).build());
//            if (!found) {
//                minioClient.makeBucket(MakeBucketArgs.builder().bucket(defaultBucketName).build());
//            }
//            
//            Long currentMillis = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
//            objectName = objectName.replaceAll("\\s+", "_").toLowerCase();
//            objectName = PasswordUtil.hashString(currentMillis + "_" + objectName);
//            objectName = subfolder + "/" + objectName;
//            
//            minioClient.putObject(
//                PutObjectArgs.builder().bucket(defaultBucketName).object(objectName).stream(
//                        inputStream, inputStream.available(), -1)
//                        .contentType(contentType)
//                        .build());
//            
//            return objectName;
//        } catch (Exception e) {
//            throw new RuntimeException(ErrorConstant.UPLOAD_FAILED.getDescription() + e.getMessage());
//        }
//    }
//    
//    public boolean deletFile(String objectName) throws Exception {
//    	if (!objectName.contains("/")) {
//    		throw new Exception(ErrorConstant.INVALID_FILENAME.getDescription());
//    	}
//    	
//    	try {
//    		minioClient.removeObject(RemoveObjectArgs.builder().bucket(defaultBucketName).object(objectName).build());
//    	} catch (Exception e) {
//			throw new Exception(ErrorConstant.DELETE_FAILED.getDescription() + e.getMessage());
//		}
//    	return true;
//    }
//    
//    public String getFullPath(String objectName) {
//    	SysParam sysParam = (SysParam) redisUtil.getOps(VariableConstant.SYS_PARAM.getValue(), SysParamConstant.URL_MINIO.toString());
//    	StringBuilder sb = new StringBuilder();
//    	sb.append(sysParam.getValue());
//    	sb.append(defaultBucketName);
//    	sb.append("/");
//    	sb.append(objectName);
//    	return sb.toString();
//    }
}
