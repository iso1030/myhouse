package com.jerrylin.myhouse.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jerrylin.myhouse.entity.Banner;

public interface BannerDao extends PagingAndSortingRepository<Banner, Long> {

	List<Banner> findByCreateTimeGreaterThan(long time);
}
