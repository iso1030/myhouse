/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.jerrylin.myhouse.web.house;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jerrylin.myhouse.entity.Banner;
import com.jerrylin.myhouse.entity.House;
import com.jerrylin.myhouse.entity.Image;
import com.jerrylin.myhouse.entity.User;
import com.jerrylin.myhouse.entity.UserProfile;
import com.jerrylin.myhouse.service.AppConfigService;
import com.jerrylin.myhouse.service.account.ShiroDbRealm.ShiroUser;
import com.jerrylin.myhouse.service.fs.FileService;
import com.jerrylin.myhouse.service.house.HouseService;
import com.jerrylin.myhouse.service.house.ImageService;
import com.jerrylin.myhouse.service.user.UserService;

import org.springside.modules.utils.Identities;
import org.springside.modules.web.MediaTypes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/house")
public class HouseController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private HouseService houseService;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private FileService fileService;
	
	@RequestMapping(value = "/xml/krpano/{houseId}")
	public ModelAndView krpanoXml(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@PathVariable(value = "houseId") long houseId) {
		if (houseId > 0) {
			House house = houseService.getHouse(houseId);
			modelMap.put("house", house);
			List<Image> images = imageService.getHouseImage(house.getId());
			List<Image> d3images = new ArrayList<Image>();
			
			for (Image image : images) {
				if (image.getType() == Image.D2) {
				} else if (image.getType() == Image.D3) {
					d3images.add(image);
				}
			}
			modelMap.put("d3images", d3images);
		}
		response.setHeader("Content-Type", MediaTypes.APPLICATION_XML_UTF_8);
		return new ModelAndView("/tour/krpano");
	}
	
	@RequestMapping(value = "/xml/slides/{houseId}")
	public ModelAndView slideXml(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable(value = "houseId") long houseId) {
		if (houseId > 0) {
			House house = houseService.getHouse(houseId);
			model.addAttribute("house", house);
			List<Image> images = imageService.getHouseImage(house.getId());
			List<Image> d2images = new ArrayList<Image>();
			
			for (Image image : images) {
				if (image.getType() == Image.D2) {
					d2images.add(image);
				} else if (image.getType() == Image.D3) {
				}
			}
			model.addAttribute("d2images", d2images);
		}
		response.setHeader("Content-Type", MediaTypes.APPLICATION_XML_UTF_8);
		return new ModelAndView("/tour/slides");
	}
	
	@RequestMapping(value = "/addimages", method= RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public List<Image> addImages(
			@RequestParam(value = "houseId", defaultValue = "0") long houseId,
			@RequestBody Image[] images, HttpServletRequest request, HttpServletResponse response) {
		if (houseId <= 0) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			return null;
		}
		if (images == null || images.length <= 0)
			return null;
		@SuppressWarnings("unchecked")
		List<Image> imageList = CollectionUtils.arrayToList(images);
		for (Image image : imageList) {
			image.setHid(houseId);
		}
		imageService.addImages(imageList);
		return imageList;
	}
	
	@RequestMapping(value = "/uploadimages", method= RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public List<Image> uploadImages(
			@RequestParam(value = "id", defaultValue = "0") long houseId,
			@RequestParam(value = "type", defaultValue = "2") int type,
			MultipartHttpServletRequest request, HttpServletResponse response) {
		MultipartFile file = request.getFile("fileupload");
		if (file == null)
			return null;
		
		List<Image> images = fileService.addHouseImage(houseId, type, file);
		if (images != null && images.size() > 0)
			imageService.addImages(images);
		return images;
	}
	
	@RequestMapping(value = "/deleteimage", produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public void deleteImage(
			@RequestParam(value = "imageId", defaultValue = "0") long imageId,
			@RequestParam(value = "houseId", defaultValue = "0") long houseId,
			@RequestParam(value = "type", defaultValue = "2") int type,
			@RequestParam(value = "file", defaultValue = "") String filename,
			HttpServletRequest request, HttpServletResponse response) {
		if (imageId > 0) {
			Image image = imageService.getImage(imageId);
			if (image != null) {
				imageService.deleteImage(image.getId());
				fileService.deleteTourImage(image);
			}
		}
		if (houseId <= 0 || (type != Image.D2 && type != Image.D3) || StringUtils.isBlank(filename))
			return;
		this.fileService.deleteHouseImage(houseId, type, filename);
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ModelAndView download(HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(200);
		return null;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView page(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "10") int pageSize,
			@RequestParam(value = "userId", defaultValue = "-1") long userId,
			@RequestParam(value = "query", defaultValue = "") String query, Model model,
			ServletRequest request) {
		Page<House> houses = null;
		if (userId > 0) {
			houses = houseService.getUserHouse(userId, pageNumber, pageSize);
		} else {
			houses = houseService.getHouse(pageNumber, pageSize);
		}
		if (houses != null && houses.getContent() != null) {
			Set<Long> idSet = new HashSet<Long>();
			for (House house : houses.getContent()) {
				if (house.getUid() > 0)
					idSet.add(house.getUid());
			}
			if (idSet.size() > 0) {
				List<UserProfile> users = userService.getUserProfileListByIds(idSet);
				Map<Long, UserProfile> temp = new HashMap<Long, UserProfile>();
				for (UserProfile user : users) {
					temp.put(user.getId(), user);
				}
				
				for (House house : houses.getContent()) {
					if (temp.get(house.getUid()) != null)
						house.setUserProfile(temp.get(house.getUid()));
				}
			}
			model.addAttribute("page", houses);
		}

		return new ModelAndView("/tour/list");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@RequestParam(value = "userId", defaultValue = "-1") long userId,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		House house = new House();
		house.setUid(userId);
		house.setAddress("");
		
		houseService.createHouse(house);
		modelMap.put("house", house);
		return new ModelAndView("/tour/edit");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(
			@RequestParam(value = "houseId", defaultValue = "-1") long houseId,
			@RequestParam(value = "m", defaultValue = "") String subModule,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		if (houseId <= 0) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		House house = houseService.getHouse(houseId);
		if (house == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		modelMap.put("host", request.getRemoteHost());
		modelMap.put("house", house);
		modelMap.put("submodule", subModule);
		
		if ("photos".equals(subModule) || "panoramas".equals(subModule)) {
			List<Image> images = imageService.getHouseImage(house.getId());
			List<Image> d2images = new ArrayList<Image>();
			List<Image> d3images = new ArrayList<Image>();
			
			for (Image image : images) {
				if (image.getType() == Image.D2) {
					d2images.add(image);
				} else if (image.getType() == Image.D3) {
					d3images.add(image);
				}
			}
//			List<Image> d2imageList = fileService.getHouseImageFiles(houseId, Image.D2);
//			List<Image> d3imageList = fileService.getHouseImageFiles(houseId, Image.D3);
			modelMap.put("d2images", d2images);
			modelMap.put("d3images", d3images);
		}
		return new ModelAndView("/tour/edit");
	}
	
	
	@RequestMapping(value = "/addorupdate", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public House addOrUpdate(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", defaultValue = "0") long houseId,
			@RequestParam(value = "address", defaultValue = "") String address,
			@RequestParam(value = "price", defaultValue = "0") Long price,
			@RequestParam(value = "area", defaultValue = "0") Long area,
			@RequestParam(value = "bedrooms", defaultValue = "") String bedrooms,
			@RequestParam(value = "photographer", defaultValue = "") String photographer,
			@RequestParam(value = "youtube", defaultValue = "") String youtube,
			@RequestParam(value = "openTime", defaultValue = "0") long openTime,
			@RequestParam(value = "bgMusic", defaultValue = "") String bgMusic,
			@RequestParam(value = "coverImg", defaultValue = "") String coverImg,
			@RequestParam(value = "userId", defaultValue = "0") long userId) {
		House house = new House();
		house.setAddress(address);
		house.setArea(area);
		house.setBedrooms(bedrooms);
//		house.setBgMusic(bgMusic);
//		house.setCode(code);
		house.setCoverImg(coverImg);
		house.setId(houseId);
		house.setLastUpdateTime(new Date().getTime());
		house.setOpenTime(openTime);
		house.setPhotographer(photographer);
		house.setPrice(price);
		house.setUid(userId);
//		house.setYoutube(youtube);
		
		houseService.updateHouse(house);
		return house;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public void delete(
			@RequestParam(value = "id", defaultValue = "0") long id,
			HttpServletRequest request, HttpServletResponse response) {
		if (id > 0) {
			House house = houseService.getHouse(id);
			if (house != null) {
				houseService.deleteHouse(house.getId());
				fileService.deleteHouse(house.getId());
			}
		}
	}
	
	@RequestMapping(value = "/package", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public Map<String, String> pack(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", defaultValue = "0") long houseId) {
		if (houseId <= 0) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			return null;
		}
		
		House house = houseService.getHouse(houseId);
		if (house == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		List<Image> images = imageService.getHouseImage(houseId);
		String result = fileService.packageHouseImage(houseId, images);
		if (result == null) {
			response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
			return null;
		}
		houseService.updateHousePackUrl(houseId, result);
		Map<String, String> map = new HashMap<String, String>();
		map.put("url", result);
		return map;
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	public Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
}
