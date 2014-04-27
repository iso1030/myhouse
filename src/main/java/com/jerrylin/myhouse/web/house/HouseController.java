/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.jerrylin.myhouse.web.house;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jerrylin.myhouse.entity.House;
import com.jerrylin.myhouse.entity.Image;
import com.jerrylin.myhouse.service.AppConfigService;
import com.jerrylin.myhouse.service.FileService;
import com.jerrylin.myhouse.service.account.ShiroDbRealm.ShiroUser;
import com.jerrylin.myhouse.service.house.HouseService;
import com.jerrylin.myhouse.service.house.ImageService;

import org.springside.modules.web.MediaTypes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;

/**
 * House管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /house/
 * Create page : GET /house/create
 * Create action : POST /house/create
 * Update page : GET /house/update/{id}
 * Update action : POST /house/update
 * Delete action : GET /house/delete/{id}
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/house")
public class HouseController {

	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private HouseService houseService;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private AppConfigService appConfigService;

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
		return new ModelAndView("/tmpl/house/krpano");
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
		return new ModelAndView("/tmpl/house/slides");
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
				fileService.deleteHouseImage(image.getHid(), image.getType(), image.getName());
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
			@RequestParam(value = "userId", defaultValue = "-1") long userId,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		if (userId > 0) {
			Page<House> houses = houseService.getUserHouse(userId, pageNumber, pageSize);
			model.addAttribute("page", houses);
		} else {
			Page<House> houses = houseService.getHouse(pageNumber, pageSize);
			model.addAttribute("page", houses);
		}

		return new ModelAndView("/tmpl/house/list");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "houseId", defaultValue = "-1") long houseId,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
//		if (houseId <= 0) {
//			response.setStatus(HttpStatus.NOT_FOUND.value());
//			return null;
//		}
		House house = houseService.getHouse(houseId);
		if (house != null) {

			modelMap.put("house", house);
			
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
		return new ModelAndView("/tmpl/house/edit");
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid House newHouse, RedirectAttributes redirectAttributes) {
//		newHouse.setUid(getCurrentUserId());

		houseService.createHouse(newHouse);
		redirectAttributes.addFlashAttribute("message", "创建任务成功");
		return "redirect:/task/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("task", houseService.getHouse(id));
		model.addAttribute("action", "update");
		return "task/taskForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("task") House task, RedirectAttributes redirectAttributes) {
		houseService.createHouse(task);
		redirectAttributes.addFlashAttribute("message", "更新任务成功");
		return "redirect:/task/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		houseService.deleteHouse(id);
		redirectAttributes.addFlashAttribute("message", "删除任务成功");
		return "redirect:/task/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出House对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getHouse(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("task", houseService.getHouse(id));
		}
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
}
