package com.bleizing.pos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bleizing.pos.annotation.Logged;
import com.bleizing.pos.dto.GetStoreByCodeResponse;
import com.bleizing.pos.model.Store;
import com.bleizing.pos.repository.StoreRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StoreService {
	@Autowired
	private StoreRepository storeRepository;
	
	@Logged
	public GetStoreByCodeResponse getStoreByCode(String code) throws Exception {
		Store store = storeRepository.findByCodeAndActiveTrue(code).orElseThrow(() -> new Exception("Not found"));
		return GetStoreByCodeResponse.builder().name(store.getName()).build();
	}
}