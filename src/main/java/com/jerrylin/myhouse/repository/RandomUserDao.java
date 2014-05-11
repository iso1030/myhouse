package com.jerrylin.myhouse.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jerrylin.myhouse.entity.RandomUser;

public interface RandomUserDao extends PagingAndSortingRepository<RandomUser, Long>, JpaSpecificationExecutor<RandomUser> {

}
