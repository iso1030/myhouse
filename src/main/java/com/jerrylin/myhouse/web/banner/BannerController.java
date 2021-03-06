package com.jerrylin.myhouse.web.banner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.utils.Identities;
import org.springside.modules.web.MediaTypes;

import com.jerrylin.myhouse.entity.Banner;
import com.jerrylin.myhouse.entity.Image;
import com.jerrylin.myhouse.service.banner.BannerService;
import com.jerrylin.myhouse.service.fs.FileService;

@Controller
@RequestMapping(value="/banner")
public class BannerController {
	
	@Autowired
	private BannerService bannerService;
	
	@Autowired
	private FileService fileService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView page(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "10") int pageSize, 
			Model model, ServletRequest request) {
//		Page<Banner> banners = bannerService.getBannerPage(pageNumber, pageSize);
//		model.addAttribute("page", banners);
		
		List<Banner> banners = bannerService.getBanners(0);
		model.addAttribute("list", banners);
		return new ModelAndView("/banner/list");
	}
	
	@RequestMapping(value = "/delete", produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public void delete(
			@RequestParam(value = "id", defaultValue = "0") long id,
			HttpServletRequest request, HttpServletResponse response) {
		if (id > 0) {
			Banner banner = bannerService.getBanner(id);
			if (banner != null) {
				bannerService.deleteBanner(banner.getId());
				fileService.deleteBannerImage(banner);
			}
		}
	}
	
	@RequestMapping(value = "/addbanners", method= RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public List<Banner> addBanners(
			@RequestBody Banner[] banners, HttpServletRequest request, HttpServletResponse response) {
		if (banners == null || banners.length <= 0)
			return null;
		@SuppressWarnings("unchecked")
		List<Banner> bannerList = CollectionUtils.arrayToList(banners);
		bannerService.addBanners(bannerList);
		return bannerList;
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public Banner upload(
			MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
		MultipartFile file = multipartRequest.getFile("bannerImage");
		if (file == null)
			return null;
		
		String url = fileService.addBannerImage(file);
		Banner banner = new Banner();
		banner.setUrl(url);
		bannerService.addBanner(banner);
		
		return banner;
	}
}
