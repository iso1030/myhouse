package com.jerrylin.myhouse.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jerrylin.myhouse.entity.UserProfile;

public interface UserProfileDao extends PagingAndSortingRepository<UserProfile, Long> {

}
