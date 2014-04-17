package com.jerrylin.myhouse.service.banner;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jerrylin.myhouse.entity.Banner;
import com.jerrylin.myhouse.repository.BannerDao;
import com.jerrylin.myhouse.service.FileService;

@Component
@Transactional
public class BannerService {

	@Autowired
	private BannerDao bannerDao;
	
	@Autowired
	private FileService fileService;
	
	public Banner addBanner(Banner banner) {
		if (banner == null)
			return banner;
		banner.setCreateTime(new Date());
		bannerDao.save(banner);
		return banner;
	}
	
	public List<Banner> getBanners(long time) {
		return bannerDao.findByCreateTimeGreaterThan(new Date(time));
	}
	
	public Page<Banner> getBannerPage(int pageNumber, int pageSize) {
		Page<Banner> page = bannerDao.findAll(new PageRequest(pageNumber - 1,  pageSize));
		
		return page;
	}
	
	public void deleteBanner(Long bannerId) {
		Banner banner = bannerDao.findOne(bannerId);
		if (banner == null)
			return;
		bannerDao.delete(banner);
		// delete image file
		fileService.deleteBannerImage(banner.getUrl());
	}
}
