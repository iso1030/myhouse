package com.jerrylin.myhouse.service.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.utils.Identities;

import com.google.common.collect.Collections2;
import com.jerrylin.myhouse.entity.Banner;
import com.jerrylin.myhouse.entity.Image;
import com.jerrylin.myhouse.service.AppConfigService;
import com.jerrylin.myhouse.service.ServiceException;

@Component
public class FileService {
	
	private static Logger logger = LoggerFactory.getLogger(FileService.class);
	
	@Autowired
	private AppConfigService appConfigService;
	
//	public String addAvatarImage(MultipartFile file) {
//		if (file == null)
//			throw new ServiceException("无效的上传文件");
//
//		String baseDir = appConfigService.getBaseDir();
//
//		long fileId = Identities.randomLong();
//		String filename = fileId + "." + getFileExtension(file.getOriginalFilename());
//		
//		String tempPath = baseDir + UrlConverter.getTempPath();
//		String filePath = baseDir + UrlConverter.getAvatarPath(fileId);
//		
//		try {
//			File tmpFile = new File(tempPath + filename);
//			FileUtils.copyInputStreamToFile(file.getInputStream(), tmpFile);
//			
//			Thumbnails.of(tmpFile)
//					  .sourceRegion(Positions.CENTER, 360, 240)
//					  .size(180, 120)
//					  .keepAspectRatio(false)
//					  .toFile(filePath + filename);
//			
//			return filePath + filename;
//		} catch (IOException e) {
//			logger.warn("图片处理失败", e);
//		}
//		
//		return null;
//	}
	/**
	 * 添加客户头像图片
	 * 
	 * @param file
	 * @return
	 */
	public String addAvatarImage(MultipartFile file) {
		long fileId = Identities.randomLong();
		String filename = fileId + "." + getFileExtension(file.getOriginalFilename());
		String destDir = UrlConverter.getAvatarPath(fileId);
		return this.addImage(180, 180, destDir, filename, file);
	}
	/**
	 * 添加Banner图片
	 * 
	 * @param file
	 * @return
	 */
	public String addBannerImage(MultipartFile file) {
		long fileId = Identities.randomLong();
		String filename = fileId + "." + getFileExtension(file.getOriginalFilename());
		String destDir = UrlConverter.getBannerPath();
		return this.addImage(640, 960, destDir, filename, file);
	}
	/**
	 * 添加封面图片
	 * 
	 * @param file
	 * @return
	 */
	public String addCoverImage(MultipartFile file) {
		long fileId = Identities.randomLong();
		String filename = fileId + "." + getFileExtension(file.getOriginalFilename());
		String destDir = UrlConverter.getCoverImagePath(fileId);
		return this.addImage(180, 120, destDir, filename, file);
	}
	/**
	 * 添加图片
	 * 
	 * @param width    缩略图宽度
	 * @param height   缩略图高度
	 * @param destDir  目标目录
	 * @param filename 文件名
	 * @param file
	 * @return
	 */
	public String addImage(int width, int height, String destDir, String filename, MultipartFile file) {
		if (file == null)
			throw new ServiceException("无效的上传文件");
		
		String baseDir = appConfigService.getBaseDir();
		
		String tempPath = baseDir + UrlConverter.getTempPath();
		String filePath = baseDir + destDir;
		try {
			File tmpFile = new File(tempPath + filename);
			FileUtils.copyInputStreamToFile(file.getInputStream(), tmpFile);

			// 创建目标目录，否则缩略图会挂掉
			File destDirFile = new File(filePath);
			if (!destDirFile.exists() || !destDirFile.isDirectory()) {
				destDirFile.mkdirs();
			}
			Thumbnails.of(tmpFile)
					  .sourceRegion(Positions.CENTER, width * 2, height * 2)
					  .size(width, height)
					  .keepAspectRatio(false)
					  .toFile(filePath + filename);
			
			return destDir + filename;
		} catch (IOException e) {
			logger.warn("图片处理失败", e);
		}
		
		return null;
	}
	
	private String getHouseDirName(long houseId) {
		return "ht" + houseId;
	}
	
	public String getHouseDir(long houseId) {
		String uploadDir = appConfigService.getUploadDir();
		return uploadDir + File.separator + getHouseDirName(houseId);
	}
	
	private String getHouseImageDirName(long houseId, int type, boolean isThumbnail) {
		return getHouseDirName(houseId) + File.separator + (isThumbnail ? "." : "") 
				+ (type == Image.D2 ? "d2" : "d3");
	}
	
	private String getHouseImageDir(long houseId, int type, boolean isThumbnail) {
		String uploadDir = appConfigService.getUploadDir();
		return uploadDir + File.separator + getHouseImageDirName(houseId, type, isThumbnail);
	}
	
	private boolean createDir(String path) {
		return true;
	}
	/**
	 * 获取房屋图片列表
	 * @param houseId 房屋id
	 * @param type    图片类型
	 * @return
	 */
//	public List<Image> getHouseImageFiles(long houseId, int type) {
//		List<Image> list = new ArrayList<Image>();
//		if (houseId <= 0 || (type != Image.D2 && type != Image.D3))
//			return list;
//		
//		File imageDirFile = new File(this.getHouseImageDir(houseId, type, false));
//		// 如果对应的图片文件夹存在
//		if (imageDirFile.exists() && imageDirFile.isDirectory()) {
//			Map<String, String> thumbnails = new HashMap<String, String>();
//			File imageThumbnailDirFile = new File(this.getHouseImageDir(houseId, type, true));
//			if (imageThumbnailDirFile.exists() && imageThumbnailDirFile.isDirectory()) {
//				File[] tmpFiles = imageThumbnailDirFile.listFiles();
//				for (File each : tmpFiles) {
//					thumbnails.put(each.getName(), each.getName());
//				}
//			}
//			String imageDirName = this.getHouseImageDirName(houseId, type, false);
//			String thumbDirName = this.getHouseImageDirName(houseId, type, true);
//			
//			File[] tmpFiles = imageDirFile.listFiles();
//			for (File each : tmpFiles) {
//				String filename = each.getName();
//				Image image = new Image();
//				image.setId(0L);
//				image.setHid(houseId);
//				image.setType(type);
//				image.setName(filename);
//				image.setUrl(File.separator + imageDirName + File.separator + filename);
//				image.setThumbnail(File.separator + thumbDirName + File.separator + thumbnails.get(filename));
//				list.add(image);
//			}
//		}
//		
//		return list;
//	}
	/**
	 * 添加房屋图片到文件系统
	 * @param houseId
	 * @param type
	 * @param file
	 * @return
	 */
	public List<Image> addHouseImage(long houseId, int type, MultipartFile file) {
		if (houseId <= 0 || file == null)
			return  null;
		String baseDir = appConfigService.getBaseDir();
		
		// 创建House原图片文件夹目录
		String baseImageDir = UrlConverter.getTourImagePath(houseId, type, false);
		String localImageDir = baseDir + baseImageDir;
		File localImageFile = new File(localImageDir);
		if (!localImageFile.exists() || !localImageFile.isDirectory()) {
			localImageFile.mkdirs();
		}
		// 创建House缩略图文件夹目录
		String baseThumbDir = UrlConverter.getTourImagePath(houseId, type, true);
		String localThumbDir = baseDir + baseThumbDir;
		File localThumbFile = new File(localThumbDir);
		if (!localThumbFile.exists() || !localThumbFile.isDirectory()) {
			localThumbFile.mkdirs();
		}

		String extension = getFileExtension(file.getOriginalFilename());
		if (extension.equals("zip") || extension.equals("rar")) {
			try {
				// 先保存到临时目录
				String tempPath = baseDir + UrlConverter.getTempPath();
				File tempFile = new File(tempPath + file.getOriginalFilename());
				FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
				
				ZipFile zipFile = new ZipFile(tempFile);
				if (!zipFile.isValidZipFile())
					throw new ZipException("压缩文件已损坏");
				
				List<Image> extractImages = new ArrayList<Image>();
				@SuppressWarnings("unchecked")
				List<FileHeader> headerList = zipFile.getFileHeaders();
				for (FileHeader each : headerList) {
					if (each.isDirectory() || each.getFileName().indexOf(File.separator)>=0)
						continue;
					// 是否重新生成新的名字，否则上传一样的文件，数据库里存在两条重复数据，删除掉其中一条会导致另一条的图片丢失
					String originFilename = each.getFileName();
					String targetFilename = Identities.randomLong() + "." + getFileExtension(originFilename);
					
					zipFile.extractFile(each, localImageDir, null, targetFilename);
					//zipFile.extractFile(each, localImageDir);
					
					Thumbnails.of(localImageDir + targetFilename)
					          .sourceRegion(Positions.CENTER, 600, 400)
					          .size(300, 200)
					          .keepAspectRatio(false)
					          .toFile(localThumbDir + targetFilename);
					Image image = new Image();
					image.setId(0L);
					image.setHid(houseId);
					image.setType(type);
					image.setName(originFilename);
					image.setUrl(baseImageDir + targetFilename);
					image.setThumbnail(baseThumbDir + targetFilename);
					extractImages.add(image);
				}
				return extractImages;
//				zipFile.extractAll(destFilename);
				
//				@SuppressWarnings("unchecked")
//				List<FileHeader> headerList = zipFile.getFileHeaders();
//				List<String> extractFileList = new ArrayList<String>();
//				for (FileHeader each : headerList) {
//					extractFileList.add(destDirPath + File.separator + each.getFileName());
//				}
//				return extractFileList;
			} catch (IOException e) {
				logger.warn("打包文件存储临时文件失败", e);
			}  catch (ZipException e) {
				logger.warn(e.getMessage(), e);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} else {
//			File targetFile = new File(destFilename + File.separator + file.getOriginalFilename());
//			try {
//				FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
//				List<String> files = new ArrayList<String>();
//				files.add(destDirPath + File.separator + file.getOriginalFilename());
//				return files;
//			} catch (IOException e) {
//				logger.warn("房屋上传图片保存失败", e);
//			}
		}
		return null;
	}
	
	public void deleteHouse(long houseId) {
		String baseDir = appConfigService.getBaseDir();
		String houseDir = UrlConverter.getTourImageBase(houseId);
		File houseDirFile = new File(baseDir + houseDir);
		if (houseDirFile.exists() && houseDirFile.isDirectory())
			houseDirFile.delete();
	}
	
	public void deleteTourImage(Image image) {
		if (image == null)
			return;
		String baseDir = appConfigService.getBaseDir();
		if (StringUtils.isNotBlank(image.getUrl())) {
			File localImageFile = new File(baseDir + image.getUrl());
			if (localImageFile.exists())
				localImageFile.delete();
		}
		if (StringUtils.isNotBlank(image.getThumbnail())) {
			File localThumbFile = new File(baseDir + image.getThumbnail());
			if (localThumbFile.exists())
				localThumbFile.delete();
		}
	}
	public void deleteBannerImage(Banner banner) {
		if (banner == null)
			return;
		String baseDir = appConfigService.getBaseDir();
		if (StringUtils.isNotBlank(banner.getUrl())) {
			File localImageFile = new File(baseDir + banner.getUrl());
			if (localImageFile.exists())
				localImageFile.delete();
		}
		if (StringUtils.isNotBlank(banner.getThumbnail())) {
			File localThumbFile = new File(baseDir + banner.getThumbnail());
			if (localThumbFile.exists())
				localThumbFile.delete();
		}
	}
	
	public String packageHouseImage(long houseId, List<Image> images) {
		if (houseId <= 0 || images == null)
			return null;
		
		String baseDir = appConfigService.getBaseDir();
		String targetDir = UrlConverter.getTourPackagePath(houseId);
		String filename = Identities.uuid2() + ".zip";
		
		try {
			File targetDirFile = new File(baseDir + targetDir);
			if (!targetDirFile.exists() || !targetDirFile.isDirectory()) {
				targetDirFile.mkdirs();
			}
			ZipFile zipFile = new ZipFile(baseDir + targetDir + filename);
			for (Image image : images) {
				File imageFile = new File(baseDir + image.getUrl());
				File thumbFile = new File(baseDir + image.getThumbnail());
				if (!imageFile.exists() || !thumbFile.exists())
					continue;
				
				ZipParameters parameters = new ZipParameters();
				parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);			// 压缩方式
				parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);	// 压缩级别
				parameters.setSourceExternalStream(true);
				
				parameters.setFileNameInZip("/"+houseId+"/d"+image.getType()+"/"+image.getName());
				zipFile.addStream(new FileInputStream(imageFile), parameters);
				
				parameters.setFileNameInZip("/"+houseId+"/.d"+image.getType()+"/"+image.getName());
				zipFile.addStream(new FileInputStream(thumbFile), parameters);
			}
		
			return targetDir + filename;
		} catch (FileNotFoundException e) {
			logger.warn("打包文件未找到相应图片", e);
		} catch (ZipException e) {
			logger.warn("打包文件失败", e);
		}
		return null;
	}
	
	public List<String> getMusicList() {
		String baseDir = appConfigService.getBaseDir();
		String musicDir = UrlConverter.getMusicPath();
		File file = new File(baseDir + musicDir);
		if (file.exists() && file.isDirectory()) {
			String[] files = file.list();
			List<String> musics = new ArrayList<String>();
			for (String each : files) {
				musics.add(musicDir + each);
			}
			return musics;
		}
		return Collections.emptyList();
	}

	
	public void deleteHouseImage(long houseId, int type, String filename) {
		String imagePath = this.getHouseImageDir(houseId, type, true);
		String imageName = imagePath + File.separator + filename;
		File imageFile = new File(imageName);
		if (imageFile.exists())
			imageFile.delete();
		
		String thumbPath = this.getHouseImageDir(houseId, type, false);
		String thumbName = thumbPath + File.separator + filename;
		File thumbFile = new File(thumbName);
		if (thumbFile.exists())
			thumbFile.delete();
	}
	
//	public String addBannerImage(MultipartFile file) {
//		if (file == null)
//			return "";
//		
//		String uploadDir = appConfigService.getBannerDir();
//		
//		String filename = Identities.randomLong() + "." + getFileExtension(file.getOriginalFilename());
//		String realname = File.separator + "b" + File.separator + filename;
//		try {
//			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(uploadDir + realname));
//		} catch (IOException e) {
//			logger.warn("上传Banner图片失败", e);
//			return null;
//		}
//		
//		return realname;
//	}
	
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
