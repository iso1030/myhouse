package com.jerrylin.myhouse.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jerrylin.myhouse.entity.House;
import com.jerrylin.myhouse.entity.Image;
import com.jerrylin.myhouse.service.AppConfigService;
import com.jerrylin.myhouse.service.FileService;
import com.jerrylin.myhouse.service.house.HouseService;
import com.jerrylin.myhouse.service.house.ImageService;
import com.jerrylin.myhouse.util.UrlGenerator;

import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Encodes;
import org.springside.modules.utils.Identities;
import org.springside.modules.web.MediaTypes;

@RestController
@RequestMapping(value = "/api/v1/house")
public class HouseRestController {

	private static Logger logger = LoggerFactory.getLogger(HouseRestController.class);

	@Autowired
	private HouseService houseService;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private AppConfigService appConfigService;
	
	@Autowired
	private FileService fileService;

	@Autowired
	private Validator validator;

	@RequestMapping(value = "/{uid}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<House> list(@PathVariable("uid") Long userId) {
		Page<House> result = houseService.getUserHouse(userId, 1, 100);
		
		return result.getContent();
	}

	@RequestMapping(value = "/{uid}/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public House get(@PathVariable("uid") Long uid, @PathVariable("id") Long id) throws UnsupportedEncodingException {
		House house = houseService.getHouse(id);
		if (house == null)
			throw new RestException(HttpStatus.NOT_FOUND, "");
		
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
//		List<Image> d2imageList = fileService.getHouseImageFiles(id, Image.D2);
//		List<Image> d3imageList = fileService.getHouseImageFiles(id, Image.D3);
		house.setD2images(d2images);
		house.setD3images(d3images);
		house.setDownloadUrl(UrlGenerator.getHouseUrl(house.getUid(), house.getId()));
		return house;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public House update(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", defaultValue = "0") long houseId,
			@RequestParam(value = "address", defaultValue = "") String address,
			@RequestParam(value = "price", defaultValue = "0") Long price,
			@RequestParam(value = "area", defaultValue = "0") Long area,
			@RequestParam(value = "bedrooms", defaultValue = "") String bedrooms,
			@RequestParam(value = "openTime", defaultValue = "0") long openTime,
			@RequestParam(value = "bgMusic", defaultValue = "") String bgMusic,
			@RequestParam(value = "coverImg", defaultValue = "") String coverImg,
			@RequestParam(value = "userId", defaultValue = "0") long userId) {
		House house = new House();
		house.setAddress(address);
		house.setArea(area);
		house.setBedrooms(bedrooms);
		house.setBgMusic(bgMusic);
		house.setCoverImg(coverImg);
		house.setId(houseId);
		house.setLastUpdateTime(new Date());
		house.setOpenTime(new Date(openTime));
		house.setPrice(price);
		house.setUid(userId);
		
		houseService.updateHouse(house);
		return house;
	}

	@RequestMapping(value = "/create1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public House create1(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", defaultValue = "0") long houseId,
			@RequestParam(value = "address", defaultValue = "") String address,
			@RequestParam(value = "price", defaultValue = "0") Long price,
			@RequestParam(value = "area", defaultValue = "0") Long area,
			@RequestParam(value = "bedrooms", defaultValue = "") String bedrooms,
			@RequestParam(value = "openTime", defaultValue = "0") long openTime,
			@RequestParam(value = "bgMusic", defaultValue = "") String bgMusic,
			@RequestParam(value = "coverImg", defaultValue = "") String coverImg,
			@RequestParam(value = "userId", defaultValue = "0") long userId) {
		
		House house = new House();
		house.setAddress(address);
		house.setArea(area);
		house.setBedrooms(bedrooms);
		house.setBgMusic(bgMusic);
		house.setCoverImg(coverImg);
		house.setOpenTime(new Date(openTime));
		house.setPrice(price);
		house.setUid(userId);
		
		houseService.createHouse(house);
		return house;
	}
	
	@RequestMapping(value = "/pack2", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> pack2(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", defaultValue = "0") long houseId) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (houseId <= 0) {
			result.put("code", 400);
			result.put("msg", "无效的房屋id");
			return result;
		}
		House house = houseService.getHouse(houseId);
		if (house == null) {
			result.put("code", 404);
			result.put("msg", "房屋信息不存在");
			return result;
		}
		
		String packageDir = appConfigService.getPackageDir();
		String filepath = File.separator + "ht" + houseId;
		File targetDir = new File(packageDir + filepath);
		if (!targetDir.exists() || !targetDir.isDirectory()) {
			targetDir.mkdirs();
		}
		String filename = Identities.uuid2() + ".zip";
		String realname = filepath + File.separator + filename;
		try {
			List<String> files = new ArrayList<String>();
			files.add("/Users/linhui/Documents/workspace/myhouse/src/main/webapp/upload/ht100/d3/01_p (2 - 2).jpg");
			files.add("/Users/linhui/Documents/workspace/myhouse/src/main/webapp/upload/ht100/d3/中文名.jpg");
			
			ZipFile zipFile = new ZipFile(packageDir + realname);
			for (String file : files) {
				File f = new File(file);
				ZipParameters parameters = new ZipParameters();
				parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
				parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
				parameters.setFileNameInZip("/test/a/" + f.getName());
				parameters.setSourceExternalStream(true);

				zipFile.addStream(new FileInputStream(f), parameters);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ZipException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/pack", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> pack(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", defaultValue = "0") long houseId) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (houseId <= 0) {
			result.put("code", 403);
			result.put("msg", "无效的房屋id");
			return result;
		}
		String houseDir = fileService.getHouseDir(houseId);
		File file = new File(houseDir);
		if (!file.exists() || !file.isDirectory()) {
			result.put("code", 404);
			result.put("msg", "房屋还未上传图片");
			return result;
		}
		String packageDir = appConfigService.getPackageDir();
		
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);			// 压缩方式
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);	// 压缩级别
		try {
			String filepath = File.separator + "ht" + String.valueOf(houseId);
			File targetDir = new File(packageDir + filepath);
			if (!targetDir.exists() || !targetDir.isDirectory()) {
				targetDir.mkdirs();
			}
			String filename = Identities.uuid2() + ".zip";
			String realname = filepath + File.separator + filename;
			ZipFile zipFile = new ZipFile(packageDir + realname);
			zipFile.addFolder(file, parameters);
			
			houseService.updateHousePackUrl(houseId, realname);
			result.put("code", 200);
			result.put("url", realname);
			return result;
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public ResponseEntity<?> create(@RequestBody House house, UriComponentsBuilder uriBuilder) {
		System.out.println(house);
		logger.info("create house: ", house);
		return null;
	}

//	@RequestMapping(method = RequestMethod.POST, consumes = MediaTypes.JSON)
//	public ResponseEntity<?> create(@RequestBody House House, UriComponentsBuilder uriBuilder) {
//		// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
//		BeanValidators.validateWithException(validator, House);
//
//		// 保存任务
//		houseService.saveHouse(House);
//
//		// 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
//		Long id = House.getId();
//		URI uri = uriBuilder.path("/api/v1/house/" + id).build().toUri();
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(uri);
//
//		return new ResponseEntity(headers, HttpStatus.CREATED);
//	}
//
//	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON)
//	// 按Restful风格约定，返回204状态码, 无内容. 也可以返回200状态码.
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void update(@RequestBody House House) {
//		// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
//		BeanValidators.validateWithException(validator, House);
//
//		// 保存任务
//		houseService.saveHouse(House);
//	}
//
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		houseService.deleteHouse(id);
	}
}
