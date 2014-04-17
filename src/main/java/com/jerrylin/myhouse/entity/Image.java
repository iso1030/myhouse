package com.jerrylin.myhouse.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "mh_image")
public class Image extends IdEntity {
	
	public static final int D2 = 2;
	public static final int D3 = 3;

	/**
	 * 图片类型
	 */
	private int type;
	/**
	 * 图片地址
	 */
	private String url;
	/**
	 * 图片缩略图地址
	 */
	private String thumbnail;
	/**
	 * 图片名称
	 */
	private String name;
	/**
	 * 图片排序值
	 */
	private int sort;
	/**
	 * 图片所属的房屋
	 */
	private long hid;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public long getHid() {
		return this.hid;
	}
	public void setHid(Long hid) {
		this.hid = hid;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
