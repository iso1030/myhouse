package com.jerrylin.myhouse.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.utils.Identities;

@Component
public class FileService {
	
	private static Logger logger = LoggerFactory.getLogger(FileService.class);
	
	@Autowired
	private AppConfigService appConfigService;
	
	public String addBannerImage(MultipartFile file) {
		String uploadDir = appConfigService.getBannerDir();
		
		String filename = Identities.randomLong() + "." + getFileExtension(file.getOriginalFilename());
		String realname = File.separator + "b" + File.separator + filename;
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(uploadDir + realname));
		} catch (IOException e) {
			logger.warn("上传Banner图片失败", e);
			return null;
		}
		
		return realname;
	}
	
	/**
	 * 删除文件系统的banner图片
	 * 
	 * @param url
	 */
	public void deleteBannerImage(String url) {
		if (StringUtils.isBlank(url))
			return;
		
		String uploadDir = appConfigService.getBannerDir();
		File file = new File(uploadDir + url);
		if (file.exists())
			file.delete();
	}

	/**
	 * 删除文件系统里的上传图片，避免被同时打包
	 * 
	 * @param url
	 * @param thumbnail
	 */
	public void deleteImage(String url, String thumbnail) {
		String uploadDir = appConfigService.getUploadDir();
		
		if (StringUtils.isNotBlank(url)) {
	 		File originFile = new File(uploadDir + url);
			originFile.delete();
		}
		
		if (StringUtils.isNotBlank(thumbnail)) {
			File thumbnailFile = new File(uploadDir + thumbnail);
			thumbnailFile.delete();
		}
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
