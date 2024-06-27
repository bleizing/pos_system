package com.bleizing.pos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bleizing.pos.annotation.Logged;
import com.bleizing.pos.dto.GetStoreByCodeResponse;
import com.bleizing.pos.error.DataNotFoundException;
import com.bleizing.pos.error.ErrorList;
import com.bleizing.pos.model.Store;
import com.bleizing.pos.repository.StoreRepository;

@Service
public class StoreService {
	@Autowired
	private StoreRepository storeRepository;
	
	@Logged
	public GetStoreByCodeResponse getStoreByIdAndCode(Long id, String code) {
		Store store;
		if (id != 0) {
			store = storeRepository.findByIdAndCodeAndActiveTrue(id, code).orElseThrow(() -> new DataNotFoundException(ErrorList.DATA_NOT_FOUND.getDescription()));
		} else {
			store = storeRepository.findByCodeAndActiveTrue(code).orElseThrow(() -> new DataNotFoundException(ErrorList.DATA_NOT_FOUND.getDescription()));
		}
		
		return GetStoreByCodeResponse.builder().name(store.getName()).build();
	}
}