package com.jerrylin.myhouse.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "mh_house")
public class House extends IdEntity {

	/**
	 * 房屋编号
	 */
	private String code;
	/**
	 * 详细地址
	 */
	private String address;
	/**
	 * 价格
	 */
	private long price;
	/**
	 * 面积
	 */
	private long area;
	/**
	 * 几厅几室
	 */
	private String bedrooms;
	/**
	 * 开放时间
	 */
	private Date openTime;
	/**
	 * 上一次的信息更新时间
	 */
	private Date lastUpdateTime;
	/**
	 * 上一次的2d图片上传时间
	 */
	private Date lastD2UploadTime;
	/**
	 * 上一次的3d图片上传时间
	 */
	private Date lastD3UploadTime;
	/**
	 * 背景音乐，网页端使用
	 */
	private String bgMusic;
	/**
	 * 封面图片
	 */
	private String coverImg;
	/**
	 * 打包下载地址
	 */
	private String packageUrl;
	/**
	 * 所属的用户id
	 */
	private long uid;
	/**
	 * 下载链接，由外部计算，不用保存数据库
	 */
	private String downloadUrl;
	/**
	 * 普通照片列表
	 */
	private List<Image> d2images;
	/**
	 * 3D照片列表
	 */
	private List<Image> d3images;
	
	@JsonIgnore
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public long getArea() {
		return area;
	}
	public void setArea(long area) {
		this.area = area;
	}
	public String getBedrooms() {
		return bedrooms;
	}
	public void setBedrooms(String bedrooms) {
		this.bedrooms = bedrooms;
	}
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Date getLastD2UploadTime() {
		return lastD2UploadTime;
	}
	public void setLastD2UploadTime(Date lastD2UploadTime) {
		this.lastD2UploadTime = lastD2UploadTime;
	}
	public Date getLastD3UploadTime() {
		return lastD3UploadTime;
	}
	public void setLastD3UploadTime(Date lastD3UploadTime) {
		this.lastD3UploadTime = lastD3UploadTime;
	}
	public String getBgMusic() {
		return bgMusic;
	}
	public void setBgMusic(String bgMusic) {
		this.bgMusic = bgMusic;
	}
	public String getCoverImg() {
		return coverImg;
	}
	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
	public String getPackageUrl() {
		return packageUrl;
	}
	public void setPackageUrl(String packageUrl) {
		this.packageUrl = packageUrl;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	
	@Transient
	public String getDownloadUrl() {
		return this.downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	@Transient
	public List<Image> getD2images() {
		return d2images;
	}
	public void setD2images(List<Image> d2images) {
		this.d2images = d2images;
	}
	@Transient
	public List<Image> getD3images() {
		return d3images;
	}
	public void setD3images(List<Image> d3images) {
		this.d3images = d3images;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}