package com.bleizing.pos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bleizing.pos.dto.GetStoreByCodeRequest;
import com.bleizing.pos.dto.GetStoreByCodeResponse;
import com.bleizing.pos.model.Store;
import com.bleizing.pos.repository.StoreRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StoreService {
	@Autowired
	private StoreRepository storeRepository;
	
	public GetStoreByCodeResponse getStoreByCode(GetStoreByCodeRequest request) throws Exception {
		log.info("req = {}", request);
		
		Store store = storeRepository.findByCode(request.getCode()).orElseThrow(() -> new Exception("Not found"));
		return GetStoreByCodeResponse.builder().name(store.getName()).build();
	}
}