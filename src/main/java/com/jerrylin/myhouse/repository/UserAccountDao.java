package com.jerrylin.myhouse.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jerrylin.myhouse.entity.UserAccount;

public interface UserAccountDao extends
		PagingAndSortingRepository<UserAccount, Long> {

}
