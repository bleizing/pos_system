package com.bleizing.pos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bleizing.pos.annotation.Logged;
import com.bleizing.pos.dto.CreateStoreRequest;
import com.bleizing.pos.dto.CreateStoreResponse;
import com.bleizing.pos.dto.DeleteStoreRequest;
import com.bleizing.pos.dto.DeleteStoreResponse;
import com.bleizing.pos.dto.GetAllStoreResponse;
import com.bleizing.pos.dto.GetAllStoreWrapper;
import com.bleizing.pos.dto.GetStoreByUserLoggedInResponse;
import com.bleizing.pos.dto.UpdateStoreRequest;
import com.bleizing.pos.dto.UpdateStoreResponse;
import com.bleizing.pos.error.DataExistsException;
import com.bleizing.pos.error.DataNotFoundException;
import com.bleizing.pos.model.Store;
import com.bleizing.pos.repository.StoreRepository;

@Service
public class StoreService {
	@Autowired
	private StoreRepository storeRepository;
	
	@Logged
	public GetStoreByUserLoggedInResponse getStoreByUserLoggedIn(Long id, String code) throws Exception {
		Store store;
		if (id != 0) {
			store = storeRepository.findByIdAndActiveTrue(id).orElseThrow(() -> new DataNotFoundException("Store not found"));
		} else {
			if (Objects.isNull(code) || code.isBlank()) {
				throw new Exception("Code must be filled");
			}
			store = storeRepository.findByCodeAndActiveTrue(code).orElseThrow(() -> new DataNotFoundException("Store not found"));
		}
		
		return GetStoreByUserLoggedInResponse.builder().name(store.getName()).code(store.getCode()).build();
	}
	
	@Logged
	public CreateStoreResponse create(CreateStoreRequest request, Long userId) {
		if (storeRepository.findByCodeAndActiveTrue(request.getCode()).isPresent()) {
			throw new DataExistsException("Store code already exists");
		}
		
		Store store = Store.builder()
				.code(request.getCode())
				.name(request.getName())
				.build();
		store.setCreatedBy(userId);
		store = storeRepository.saveAndFlush(store);
		return CreateStoreResponse.builder().id(store.getId()).build();
	}
	
	@Logged
	public GetAllStoreResponse getAll() {
		List<Store> stores = storeRepository.findByActiveTrue().orElseThrow(() -> new DataNotFoundException("Stores not found"));
		List<GetAllStoreWrapper> wrapper = new ArrayList<>();
		
		stores.stream().forEach(store -> {
			wrapper.add(GetAllStoreWrapper.builder()
			.name(store.getName())
			.code(store.getCode())
			.build());
		});
		
		return GetAllStoreResponse.builder().stores(wrapper).build();
	}
	
	@Logged
	public UpdateStoreResponse update(UpdateStoreRequest request, String code, Long storeId, Long userId) throws Exception {
		Store store;
		if (storeId != 0) {
			store = storeRepository.findByIdAndActiveTrue(storeId).orElseThrow(() -> new DataNotFoundException("Store not found"));
		} else {
			if (Objects.isNull(code) || code.isBlank()) {
				throw new Exception("Code must be filled");
			}
			store = storeRepository.findByCodeAndActiveTrue(code).orElseThrow(() -> new DataNotFoundException("Store not found"));
		}
		store.setName(request.getName());
		store.setUpdatedBy(userId);
		storeRepository.save(store);
		
		return UpdateStoreResponse.builder().success(true).build();
	}
	
	@Logged
	public DeleteStoreResponse delete(DeleteStoreRequest request, Long userId) {
		Store store = storeRepository.findByCodeAndActiveTrue(request.getCode()).orElseThrow(() -> new DataNotFoundException("Store not found"));
		store.setActive(false);
		store.setUpdatedBy(userId);
		storeRepository.save(store);
		
		return DeleteStoreResponse.builder().success(true).build();
	}
}