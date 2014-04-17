package com.jerrylin.myhouse.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;

import com.jerrylin.myhouse.entity.Banner;
import com.jerrylin.myhouse.service.banner.BannerService;

@RestController
@RequestMapping(value = "/api/v1/banner")
public class BannerRestController {
	
	@Autowired
	private BannerService bannerService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<Banner> list(@RequestParam(value = "t", defaultValue = "0", required = true) Long time) {
		List<Banner> banners = bannerService.getBanners(time);
		
		return banners;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long bannerId) {
		bannerService.deleteBanner(bannerId);
	}
}
