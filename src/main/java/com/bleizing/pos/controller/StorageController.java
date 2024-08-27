package com.bleizing.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bleizing.pos.annotation.AccessControl;
import com.bleizing.pos.annotation.Authenticated;
import com.bleizing.pos.dto.UploadStorageResponse;
import com.bleizing.pos.service.StorageService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Storage", description = "Storage Controller")
@RestController
@RequestMapping("/storage")
@SecurityRequirement(name = "Authorization")
public class StorageController {

	@Autowired
	private StorageService storageService;
	
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Authenticated
	@AccessControl
	public UploadStorageResponse upload(
			@Parameter(
		            name =  "file",
		            description  = "File",
		            schema = @Schema(type = "file"),
		            required = true) @RequestPart("file") MultipartFile file, 
			@RequestParam(value = "category") String category, HttpServletRequest servletRequest) throws Exception {
		return UploadStorageResponse.builder().filename(storageService.uploadFile(category, file)).build();
	}
}
