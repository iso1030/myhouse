package com.jerrylin.myhouse.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("appConfigService")
public class AppConfigService {
	
	@Value("${app.upload_dir}")
	private String uploadDir;
	
	@Value("${app.upload_prefix}")
	private String uploadPrefix;
	
	@Value("${app.package_dir}")
	private String packageDir;
	
	@Value("${app.package_prefix}")
	private String packagePrefix;
	
	@Value("${app.deletedir}")
	private String deletedir;
	
	@Value("${jdbc.url}")
	private String jdbcUrl;
	
	@Value("${app.banner_dir}")
	private String bannerDir;
	
	@Value("${app.banner_prefix}")
	private String bannerPrefix;
	
	/**
	 * 获取应用上传目录
	 * @return
	 */
	public String getUploadDir() {
		return this.uploadDir;
	}
	/**
	 * 获取应用上传图片的访问前缀
	 * @return
	 */
	public String getUploadPrefix() {
		return this.uploadPrefix;
	}
	/**
	 * 获取打包下载目录
	 * @return
	 */
	public String getPackageDir() {
		return this.packageDir;
	}
	/**
	 * 获取打包下载目录的访问前缀
	 * @return
	 */
	public String getPackagePrefix() {
		return this.packagePrefix;
	}
	/**
	 * 获取banner上传目录
	 * @return
	 */
	public String getBannerDir() {
		return this.bannerDir;
	}
	/**
	 * 获取banner图片的访问前缀
	 * @return
	 */
	public String getBannerPrefix() {
		return this.bannerPrefix;
	}
	/**
	 * 获取应用文件删除目录，相当于垃圾箱的概念
	 * @return
	 */
	public String getDeleteDir() {
		return this.deletedir;
	}
	/**
	 * just a test
	 * @return
	 */
	public String getJdbcUrl() {
		return this.jdbcUrl;
	}
}
