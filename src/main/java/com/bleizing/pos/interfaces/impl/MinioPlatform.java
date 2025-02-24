package com.bleizing.pos.interfaces.impl;

import java.io.InputStream;

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

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;

@Service
@ConditionalOnExpression("'${storage.platform}'.equalsIgnoreCase('minio')")
public class MinioPlatform implements StoragePlatform {
	@Autowired
    private MinioClient minioClient;
	
	@Value("${minio.bucket.name}")
    private String defaultBucketName;
	
	@Autowired
	private RedisUtil redisUtil;

	@Logged
	@Override
	public String uploadFile(String subfolder, String objectName, InputStream inputStream, String contentType) throws Exception {
		String fileName = "";
		
		boolean bucketExist = bucketExist(defaultBucketName);
		if (!bucketExist) {
			createBucket(defaultBucketName);
		}
		
		try {
			fileName = createObjectName(subfolder, objectName);
			minioClient.putObject(PutObjectArgs.builder()
					.bucket(defaultBucketName)
					.object(fileName)
					.stream(inputStream, inputStream.available(), -1)
					.contentType(contentType)
					.build());

			return fileName;
        } catch (Exception e) {
            throw new RuntimeException(ErrorConstant.UPLOAD_FAILED.getDescription() + e.getMessage());
        }
	}

	@Override
	public boolean bucketExist(String bucketName) throws Exception {
		try {
			return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
		} catch (Exception e) {
			throw new Exception(ErrorConstant.CHECK_BUCKET_EXISTS_INVALID.getDescription() + e.getMessage());
		}
	}

	@Override
	public void createBucket(String bucketName) throws Exception {
		try {
			minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
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
    		minioClient.removeObject(RemoveObjectArgs.builder().bucket(defaultBucketName).object(objectName).build());
    	} catch (Exception e) {
			throw new Exception(ErrorConstant.DELETE_FAILED.getDescription() + e.getMessage());
		}
    	
    	return true;
	}

	@Override
	public String getFullPath(String objectName) {
		SysParam sysParam = (SysParam) redisUtil.getOps(VariableConstant.SYS_PARAM.getValue(), SysParamConstant.URL_MINIO.toString());
		return getFullPath(sysParam.getValue(), defaultBucketName, objectName);
	}
}
