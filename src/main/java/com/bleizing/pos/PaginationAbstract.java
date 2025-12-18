package com.bleizing.pos;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.bleizing.pos.dto.BasePaginationResponse;

public abstract class PaginationAbstract {
	public <T extends BasePaginationResponse> T pageableResponse(T t, int page, int size, long totalSize, long totalPage) {
		t.setPage(page);
		t.setSize(size);
		t.setTotalSize(totalSize);
		t.setTotalPage(totalPage);
		return t;
	}
	
	public Pageable getPageable(int page, int size, String sortBy, boolean ascending) {
		Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		return PageRequest.of(page, size, sort);
	}
}
