package com.bleizing.pos.interfaces.impl;

import java.io.File;
import java.io.InputStream;
import java.nio.file.FileSystems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

import com.bleizing.pos.annotation.Logged;
import com.bleizing.pos.constant.ErrorConstant;
import com.bleizing.pos.constant.SysParamConstant;
import com.bleizing.pos.constant.VariableConstant;
import com.bleizing.pos.interfaces.StoragePlatform;
import com.bleizing.pos.model.SysParam;
import com.bleizing.pos.util.RedisUtil;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

@Slf4j
@Service
@ConditionalOnExpression("'${storage.platform}'.equalsIgnoreCase('s3')")
public class S3Platform implements StoragePlatform {
	@Autowired
    private S3Client s3Client;
	
	@Value("${aws.bucket.name}")
    private String defaultBucketName;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Logged
	@Override
	public String uploadFile(String subfolder, String objectName, InputStream inputStream, String contentType) throws Exception {
		boolean bucketExist = bucketExist(defaultBucketName);
		if (!bucketExist) {
			log.info("bucket not exists");
			createBucket(defaultBucketName);
		}
		
		try {
			String fileName = createObjectName("pos/" + subfolder, objectName);
			File file = new File(objectName);
			
			s3Client.putObject(request -> request
			    .bucket(defaultBucketName)
			    .key(fileName)
			    .ifNoneMatch("*"), 
			    FileSystems.getDefault().getPath(file.getPath()));
			
			return fileName;
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException(ErrorConstant.UPLOAD_FAILED.getDescription() + e.getMessage());
        }
	}

	@Override
	public boolean bucketExist(String bucketName) throws Exception {
		log.info("bucketExist");
		try {
			s3Client.headBucket(request -> request.bucket(bucketName));
			return true;
		} catch (NoSuchBucketException  e) {
			return false;
		} catch (Exception e) {
        	e.printStackTrace();
			throw new Exception(ErrorConstant.CHECK_BUCKET_EXISTS_INVALID.getDescription() + e.getMessage());
		}
	}

	@Override
	public void createBucket(String bucketName) throws Exception {
		log.info("createBucket");
		try {
			s3Client.createBucket(request -> request.bucket(bucketName));
		} catch (Exception e) {
			throw new Exception(ErrorConstant.CREATE_BUCKET_FAILED.getDescription() + e.getMessage());
		}
	}

	@Override
	public boolean deleteFile(String objectName) throws Exception {
		if (!objectName.contains("/")) {
    		throw new Exception(ErrorConstant.INVALID_FILENAME.getDescription());
    	}
    	
    	try {
    		s3Client.deleteObject(request -> request
    		    .bucket(defaultBucketName)
    		    .key(objectName));
    	} catch (Exception e) {
			throw new Exception(ErrorConstant.DELETE_FAILED.getDescription() + e.getMessage());
		}
    	
    	return true;
	}

	@Override
	public String getFullPath(String objectName) {
		SysParam sysParam = (SysParam) redisUtil.getOps(VariableConstant.SYS_PARAM.getValue(), SysParamConstant.URL_S3.toString());
		return getFullPath(sysParam.getValue(), defaultBucketName, objectName);
	}
}
