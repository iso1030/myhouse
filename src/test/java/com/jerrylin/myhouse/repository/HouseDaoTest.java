package com.jerrylin.myhouse.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.jerrylin.myhouse.entity.House;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class HouseDaoTest extends SpringTransactionalTestCase {

	@Autowired
	private HouseDao houseDao;
	
	@Test
	public void findHousesByUserId() throws Exception {
		Page<House> houses = houseDao.findByUid(2L, new PageRequest(0, 100, Direction.ASC, "id"));
		Assertions.assertThat(houses.getContent()).hasSize(2);
	}
}
