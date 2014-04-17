package com.jerrylin.myhouse.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.jerrylin.myhouse.entity.Image;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ImageDaoTest extends SpringTransactionalTestCase {

	@Autowired
	private ImageDao imageDao;
	
	@Test
	public void findByHid() throws Exception {
		Page<Image> images = imageDao.findByHid(1L, new PageRequest(0, 100, Direction.ASC, "id"));
		Assertions.assertThat(images.getContent()).hasSize(2);
	}
}
