package com.jerrylin.myhouse.service.fs;

import java.io.File;

import com.jerrylin.myhouse.entity.Image;
import com.jerrylin.myhouse.util.TimeUtils;

public class UrlConverter {

	/**
	 * 分目录划分的hash值
	 */
	public static final int MAX_DIR_HASH = 100;
	public static final String MUSIC_DIR = "music";
	/**
	 * 上传文件的临时保存目录
	 */
	public static final String TMP_DIR = "tmp";
	/**
	 * 客户头像的文件保存目录
	 */
	public static final String CUSTOMER_AVATR_DIR = "u";
	/**
	 * 房屋图片的文件一级保存目录
	 */
	public static final String TOUR_IMAGE_DIR = "h";
	
	public static final String TOUR_PACKAGE_DIR = "download";
	/**
	 * Banner图片的文件一级保存目录
	 */
	public static final String BANNER_DIR = "b";
	/**
	 * 封面图片的文件一级保存目录
	 */
	public static final String COVER_IMAGE_DIR = "c";

	public static String getMusicPath() {
		StringBuffer sb = new StringBuffer();
		sb.append(File.separator);
		sb.append(MUSIC_DIR);
		sb.append(File.separator);
		
		return sb.toString();
	}
	/**
	 * 获取上传文件的临时保存目录，目录用日期作为划分策略
	 * 
	 * @return
	 */
	public static String getTempPath() {
		StringBuffer sb = new StringBuffer();
		sb.append(File.separator);
		sb.append(TMP_DIR);
		sb.append(File.separator);
		sb.append(TimeUtils.getTodayStr());
		sb.append(File.separator);
		
		return sb.toString();
	}
	
	/**
	 * 获取客户头像的保存目录，目录用值100作为划分目录
	 * 
	 * @param fileId
	 * @return
	 */
	public static String getAvatarPath(long fileId) {
		StringBuffer sb = new StringBuffer();
		sb.append(File.separator);
		sb.append(CUSTOMER_AVATR_DIR);
		sb.append(File.separator);
		sb.append(fileId % 100 + 1);
		sb.append(File.separator);
		
		return sb.toString();
	}
	public static String getTourImageBase(long houseId) {
		StringBuffer sb = new StringBuffer();
		sb.append(File.separator);
		sb.append(TOUR_IMAGE_DIR);
		sb.append(File.separator);
		sb.append(houseId);
		
		return sb.toString();
	}
	/**
	 * 获取房屋图片的保存目录，目录用房屋id作为划分策划
	 * 
	 * @param houseId  房屋id
	 * @param type     图片类型
	 * @param isHidden 是否是隐藏目录
	 * @return
	 */
	public static String getTourImagePath(long houseId, int type, boolean isHidden) {
		StringBuffer sb = new StringBuffer();
		sb.append(getTourImageBase(houseId));
		sb.append(File.separator);
		if (isHidden) {
			sb.append('.');
		}
		if (type == Image.D2) {
			sb.append("d2");
		} else {
			sb.append("d3");
		}
		sb.append(File.separator);
		
		return sb.toString();
	}
	
	/**
	 * 获取房屋图片的打包文件保存目录，目录用房屋id作为划分策略
	 * 
	 * @param houseId
	 * @return
	 */
	public static String getTourPackagePath(long houseId) {
		StringBuffer sb = new StringBuffer();
		sb.append(File.separator);
		sb.append(TOUR_PACKAGE_DIR);
		sb.append(File.separator);
		sb.append(houseId);
		sb.append(File.separator);
		
		return sb.toString();
	}
	
	/**
	 * 获取Banner图片的保存目录，目录用日期作为划分策略
	 * 
	 * @return
	 */
	public static String getBannerPath() {
		StringBuffer sb = new StringBuffer();
		sb.append(File.separator);
		sb.append(BANNER_DIR);
		sb.append(File.separator);
		sb.append(TimeUtils.getTodayStr());
		sb.append(File.separator);
		
		return sb.toString();
	}
	/**
	 * 获取封面图片的保存目录，目录用值100作为划分目录
	 * 
	 * @param fileId
	 * @return
	 */
	public static String getCoverImagePath(long fileId) {
		StringBuffer sb = new StringBuffer();
		sb.append(File.separator);
		sb.append(COVER_IMAGE_DIR);
		sb.append(File.separator);
		sb.append(fileId % 100 + 1);
		sb.append(File.separator);
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println("Hello world!");
		System.out.println(getAvatarPath(12354358783457L));
		System.out.println(getCoverImagePath(12354358783457L));
	}
}
