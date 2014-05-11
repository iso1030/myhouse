package com.jerrylin.myhouse.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jerrylin.myhouse.entity.Image;

public interface ImageDao extends PagingAndSortingRepository<Image, Long>, JpaSpecificationExecutor<Image> {

	List<Image> findByHid(Long houseId);
	
	Page<Image> findByHid(Long houseId, Pageable pageRequest);
	
}
