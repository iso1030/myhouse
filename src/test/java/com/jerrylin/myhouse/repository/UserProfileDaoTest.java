package com.jerrylin.myhouse.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.jerrylin.myhouse.entity.UserProfile;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UserProfileDaoTest extends SpringTransactionalTestCase {

	@Autowired
	private UserProfileDao userProfileDao;
	
	@Test
	public void getAllUserProfile() throws Exception {
//		Page<UserProfile> userProfiles = userProfileDao.findAll(new PageRequest(0, 100, Direction.ASC, "id"));
//		Assertions.assertThat(userProfiles.getTotalElements()).isEqualTo(0);
	}
}
