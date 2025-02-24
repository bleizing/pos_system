package com.bleizing.pos.interfaces.impl;

import java.io.InputStream;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

import com.bleizing.pos.annotation.Logged;
import com.bleizing.pos.interfaces.StoragePlatform;

@Service
@ConditionalOnExpression("'${storage.platform}'.equalsIgnoreCase('s3')")
public class S3Platform implements StoragePlatform {

	@Logged
	@Override
	public String uploadFile(String subfolder, String objectName, InputStream inputStream, String contentType)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean bucketExist(String bucketName) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createBucket(String bucketName) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteFile(String objectName) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getFullPath(String objectName) {
		// TODO Auto-generated method stub
		return null;
	}

}
