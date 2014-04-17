package com.jerrylin.myhouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jerrylin.myhouse.entity.Image;

public interface ImageDao extends PagingAndSortingRepository<Image, Long>, JpaSpecificationExecutor<Image> {

	Page<Image> findByHid(Long houseId, Pageable pageRequest);
	
}
