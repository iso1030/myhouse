/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.jerrylin.myhouse.data;

import java.util.Date;

import com.jerrylin.myhouse.entity.House;

import org.springside.modules.test.data.RandomData;

/**
 * House相关实体测试数据生成.
 * 
 * @author jerrylin
 */
public class HouseData {

	public static House randomHouse() {
		House house = new House();
		house.setAddress(randomAddress());
		house.setArea(RandomData.randomId());
		house.setBedrooms(randomBedrooms());
		house.setBgMusic("/" + randomBgMusic());
		house.setId(RandomData.randomId());
		house.setOpenTime(new Date().getTime());
		house.setPrice(RandomData.randomId());
		house.setUid(1L);
		return house;
	}
	
	public static String randomAddress() {
		return RandomData.randomName("Address");
	}
	
	public static String randomBedrooms() {
		return RandomData.randomName("Bedrooms");
	}
	
	public static String randomBgMusic() {
		return RandomData.randomName("BgMusic");
	}

}
