package com.jerrylin.myhouse.service.banner;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jerrylin.myhouse.entity.Banner;
import com.jerrylin.myhouse.repository.BannerDao;

@Component
@Transactional
public class BannerService {

	@Autowired
	private BannerDao bannerDao;
	
	public Banner getBanner(long id) {
		return bannerDao.findOne(id);
	}
	
	public Banner addBanner(Banner banner) {
		if (banner == null)
			return banner;
		banner.setCreateTime(new Date().getTime());
		bannerDao.save(banner);
		return banner;
	}
	
	public List<Banner> addBanners(List<Banner> banners) {
		if (banners == null)
			return banners;
		for (Banner banner : banners) {
			if (banner.getCreateTime() <= 0)
				banner.setCreateTime(new Date().getTime());
		}
		bannerDao.save(banners);
		return banners;
	}
	
	public List<Banner> getBanners(long time) {
		return bannerDao.findByCreateTimeGreaterThan(time,new Sort(Direction.ASC, "sort"));
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
	}
}
