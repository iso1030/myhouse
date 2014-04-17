/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.jerrylin.myhouse.service.house;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jerrylin.myhouse.entity.House;
import com.jerrylin.myhouse.entity.Image;
import com.jerrylin.myhouse.repository.HouseDao;
import com.jerrylin.myhouse.repository.ImageDao;

import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

@Component
@Transactional
public class HouseService {

	@Autowired
	private HouseDao houseDao;
	
	@Autowired
	private ImageDao imageDao;

	public House getHouse(Long id) {
		return houseDao.findOne(id);
	}
	
	public void updateHouse(House house) {
		if (house == null || house.getId() <= 0)
			return;
		House oldHouse = houseDao.findOne(house.getId());
		if (oldHouse == null)
			return;
		oldHouse.setAddress(house.getAddress());
		oldHouse.setArea(house.getArea());
		oldHouse.setBedrooms(house.getBedrooms());
		oldHouse.setBgMusic(house.getBgMusic());
//		oldHouse.setCode(house.getCode());
		oldHouse.setCoverImg(house.getCoverImg());
		oldHouse.setLastUpdateTime(new Date());
		oldHouse.setOpenTime(house.getOpenTime());
		oldHouse.setPrice(house.getPrice());
		oldHouse.setUid(house.getUid());
		
		houseDao.save(oldHouse);
	}
	
	public void updateHousePackUrl(Long houseId, String packUrl) {
		if (houseId == null || houseId <= 0)
			return;
		House house = houseDao.findOne(houseId);
		if (house == null)
			return;
		house.setPackageUrl(packUrl);
		houseDao.save(house);
	}

	public void createHouse(House house) {
		if (house == null)
			return;
		house.setCode(UUID.randomUUID().toString());
		house.setLastUpdateTime(new Date());
		house.setLastD2UploadTime(new Date());
		house.setLastD3UploadTime(new Date());
		houseDao.save(house);
	}

	public void deleteHouse(Long id) {
		houseDao.delete(id);
	}

	public List<House> getAllHouse() {
		return (List<House>) houseDao.findAll();
	}
	
	public Page<House> getHouse(int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
		return houseDao.findAll(pageRequest);
	}
	
	public Page<House> getUserHouse(Long userId, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
		return houseDao.findByUid(userId, pageRequest);
	}

	public Page<House> getUserHouse(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<House> spec = buildSpecification(userId, searchParams);

		return houseDao.findAll(spec, pageRequest);
	}
	
	public Image getImage(Long id) {
		return imageDao.findOne(id);
	}
	
	public boolean saveImage(Image entity) {
		imageDao.save(entity);
		// TODO write to file system
		return true;
	}
	
	public boolean deleteImage(long id) {
		imageDao.delete(id);
		// TODO delete from file system
		return true;
	}
	
	public List<Image> getAllImage() {
		return (List<Image>) imageDao.findAll();
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<House> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<House> spec = DynamicSpecifications.bySearchFilter(filters.values(), House.class);
		return spec;
	}
}
