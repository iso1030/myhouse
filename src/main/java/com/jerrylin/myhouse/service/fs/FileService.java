package com.jerrylin.myhouse.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.utils.Identities;

import com.jerrylin.myhouse.entity.Image;

@Component
public class FileService {
	
	private static Logger logger = LoggerFactory.getLogger(FileService.class);
	
	@Autowired
	private AppConfigService appConfigService;
	
	public String getHouseDirName(long houseId) {
		return "ht" + houseId;
	}
	
	public String getHouseDir(long houseId) {
		String uploadDir = appConfigService.getUploadDir();
		return uploadDir + File.separator + getHouseDirName(houseId);
	}
	
	public String getHouseImageDirName(long houseId, int type, boolean isThumbnail) {
		return getHouseDirName(houseId) + File.separator + (isThumbnail ? "." : "") 
				+ (type == Image.D2 ? "d2" : "d3");
	}
	
	public String getHouseImageDir(long houseId, int type, boolean isThumbnail) {
		String uploadDir = appConfigService.getUploadDir();
		return uploadDir + File.separator + getHouseImageDirName(houseId, type, isThumbnail);
	}
	
	public boolean createDir(String path) {
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
		// 创建House原图片文件夹目录
		String imageDirname = this.getHouseImageDirName(houseId, type, false);
		String imageDirpath = this.getHouseImageDir(houseId, type, false);
		File imageDirFile = new File(imageDirpath);
		if (!imageDirFile.exists() || !imageDirFile.isDirectory()) {
			imageDirFile.mkdirs();
		}
		// 创建House缩略图文件夹目录
		String thumbDirname = this.getHouseImageDirName(houseId, type, true);
		String thumbDirpath = this.getHouseImageDir(houseId, type, true);
		File thumbDirFile = new File(thumbDirpath);
		if (!thumbDirFile.exists() || !thumbDirFile.isDirectory()) {
			thumbDirFile.mkdirs();
		}
		
		String extension = getFileExtension(file.getOriginalFilename());
		if (extension.equals("zip") || extension.equals("rar")) {
			try {
				// 先保存到临时目录
				String tmpDir = appConfigService.getTmpDir();
				File tmpFile = new File(tmpDir + File.separator + file.getOriginalFilename());
				FileUtils.copyInputStreamToFile(file.getInputStream(), tmpFile);
				
				ZipFile zipFile = new ZipFile(tmpFile);
				zipFile.setFileNameCharset("GBK");
				if (!zipFile.isValidZipFile())
					throw new ZipException("压缩文件已损坏");
				
				List<Image> extractImages = new ArrayList<Image>();
				@SuppressWarnings("unchecked")
				List<FileHeader> headerList = zipFile.getFileHeaders();
				for (FileHeader each : headerList) {
					if (each.isDirectory() || each.getFileName().indexOf(File.separator)>=0)
						continue;
					zipFile.extractFile(each, imageDirpath);
					
					String targetFilename = each.getFileName();
					String targetImagename = imageDirpath + File.separator + targetFilename;
					String targetThumbname = thumbDirpath + File.separator + targetFilename;
//					System.out.println(targetFilename);
//					System.out.println(targetImagename);
//					System.out.println(targetThumbname);
//					System.out.println(new File(thumbDirpath).exists());
//					System.out.println("===============");
					if (type == Image.D3) {
						Thumbnails.of(targetImagename)
						          .sourceRegion(Positions.CENTER, 360, 240)
						          .size(180, 120)
						          .keepAspectRatio(false)
						          .toFile(targetThumbname);
					} else {
						Thumbnails.of(targetImagename)
				          .size(180, 120)
				          .toFile(targetThumbname);
					}
					Image image = new Image();
					image.setId(0L);
					image.setHid(houseId);
					image.setType(type);
					image.setName(targetFilename);
					image.setUrl(File.separator + imageDirname + File.separator + targetFilename);
					image.setThumbnail(File.separator + thumbDirname + File.separator + targetFilename);
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
	
	public String addBannerImage(MultipartFile file) {
		if (file == null)
			return "";
		
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
