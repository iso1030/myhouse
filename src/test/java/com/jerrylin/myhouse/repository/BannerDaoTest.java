package com.jerrylin.myhouse.repository;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.jerrylin.myhouse.entity.Banner;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class BannerDaoTest extends SpringTransactionalTestCase {

	@Autowired
	private BannerDao bannerDao;
	
	@Test
	public void findBannerByCreateTime() throws Exception {
//		List<Banner> banners = bannerDao.findByCreateTimeGreaterThan(new Date().getTime(), new Sort(Direction.ASC));
//		Assertions.assertThat(banners).isEmpty();
//		
//		banners = (List<Banner>) bannerDao.findAll();
//		Assertions.assertThat(banners).hasSize(1);
	}
}
