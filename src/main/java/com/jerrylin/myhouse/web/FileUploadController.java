package com.jerrylin.myhouse.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.utils.Identities;
import org.springside.modules.web.MediaTypes;

import com.jerrylin.myhouse.entity.Image;
import com.jerrylin.myhouse.service.AppConfigService;
import com.jerrylin.myhouse.service.fs.FileService;
import com.jerrylin.myhouse.util.TimeUtils;

@RestController
@RequestMapping(value = "/upload")
public class FileUploadController {
	
	@Autowired
	private AppConfigService appConfigService;
	
	@Autowired
	private FileService fileService;

//	@RequestMapping(method = RequestMethod.POST)
//	@ResponseBody
//	public String upload(@RequestParam(value = "id", defaultValue = "0") long userId,
//			@RequestParam MultipartFile[] files, HttpServletRequest request) {
//		String realPath = request.getSession().getServletContext().getRealPath("/upload");
//		System.out.println(files.length);
//		String originFilename = null;
//		for (MultipartFile file : files) {
//			if (file.isEmpty())
//				return null;
//			originFilename = file.getOriginalFilename();
//			System.out.println("文件原名：" + originFilename);
//			System.out.println("文件名称：" + file.getName());
//			System.out.println("文件长度：" + file.getSize());
//			System.out.println("文件类型：" + file.getContentType());
//			System.out.println("================================");
//			System.out.println(realPath);
//			try {
//				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, originFilename));
//			} catch (IOException e) {
//				e.printStackTrace();
//				return null;
//			}
//		}
//		
//		return "{\"code\":200}";
//	}
	
	@RequestMapping(value = "/houseimg", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> uploadHouseImage(
			@RequestParam(value = "houseId", defaultValue = "-1") long houseId,
			@RequestParam(value = "type", defaultValue = "2") int type,
			MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (houseId <= 0) {
			result.put("code", 403);
			result.put("msg", "必须将照片关联到存在的房屋");
			return result;
		}
		if (type != Image.D2 && type != Image.D3) {
			result.put("code", 403);
			result.put("msg", "无效的图片类型");
			return result;
		}
		String uploadDir = appConfigService.getUploadDir();
		String targetPath = uploadDir;
		
		List<String> resultFiles = new ArrayList<String>();
		for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = it.next();
			
			if (!key.equalsIgnoreCase("thumnail") && !key.equalsIgnoreCase("origin"))
				continue;
			
			MultipartFile orderFile = multipartRequest.getFile(key);
			if (orderFile.getSize() > 0) {
				try {
					String filename = Identities.randomLong() + "." + getFileExtension(orderFile.getOriginalFilename());
					String realname = File.separator + houseId + File.separator + (type == Image.D2 ? "d2" : "d3") + File.separator + filename;
					FileUtils.copyInputStreamToFile(orderFile.getInputStream(), new File(targetPath + realname));
					resultFiles.add(realname);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		result.put("files", resultFiles);
		return result;
	}
	
	@RequestMapping(value = "/timages",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public List<Image> tourImages(
			@RequestParam(value = "houseId", defaultValue = "0") long houseId,
			@RequestParam(value = "type", defaultValue = "2") int type,
			MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
		if (houseId <= 0 || (type != Image.D2 && type != Image.D3)) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			return null;
		}
		MultipartFile file = multipartRequest.getFile("fileupload");
		if (file == null || file.getSize() <= 0) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			return null;
		}
		List<Image> images = fileService.addHouseImage(houseId, type, file);
		if (images == null) {
			response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
			return null;
		}
		return images;
	}
	
	@RequestMapping(value = "/avatar",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public List<String> avatar(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
		MultipartFile file = multipartRequest.getFile("fileupload");
		if (file == null || file.getSize() <= 0) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			return null;
		}
		String result = fileService.addAvatarImage(file);
		if (result == null) {
			response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
			return null;
		}
		List<String> list = new ArrayList<String>();
		list.add(result);
		
		return list;
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public List<String> upload(
			@RequestParam(value = "type", defaultValue = "") String type, 
			MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
		List<String> resultFiles = new ArrayList<String>();
		for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = it.next();
			MultipartFile orderFile = multipartRequest.getFile(key);
			if (orderFile.getSize() <= 0) {
				resultFiles.add(null);
				continue;
			}
			if ("avatar".equalsIgnoreCase(type)) {
				String result = fileService.addAvatarImage(orderFile);
				resultFiles.add(result);
			} else if ("banner".equalsIgnoreCase(type)) {
				String result = fileService.addBannerImage(orderFile);
				resultFiles.add(result);
			} else if ("cover".equalsIgnoreCase(type)) {
				String result = fileService.addCoverImage(orderFile);
				resultFiles.add(result);			
			}
		}
		return resultFiles;
	}
	/**
	 * 获取文件后缀名
	 * 
	 * @param filename
	 * @return
	 */
	private String getFileExtension(String filename) {
		if (StringUtils.isBlank(filename))
			return "";
		int index = filename.lastIndexOf('.');
		if (index < 0)
			return "";
		return filename.substring(index + 1);
	}
}
