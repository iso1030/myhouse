package com.jerrylin.myhouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jerrylin.myhouse.entity.House;

public interface HouseDao extends PagingAndSortingRepository<House, Long>, JpaSpecificationExecutor<House>  {

	Page<House> findByUid(Long userId, Pageable pageRequest);
	
	@Modifying
	@Query("delete from House house where house.uid = ?1")
	void deleteByUid(Long uid);
}
