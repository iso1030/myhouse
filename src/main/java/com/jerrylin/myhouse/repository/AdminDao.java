/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.jerrylin.myhouse.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.jerrylin.myhouse.entity.Admin;

public interface AdminDao extends PagingAndSortingRepository<Admin, Long> {
	Admin findByLoginName(String loginName);
}
