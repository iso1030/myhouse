package com.jerrylin.myhouse.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "mh_random_user")
public class RandomUser extends IdEntity {

	/**
	 * 页码，从1开始算
	 */
	private int pageIndex;
	/**
	 * 页的位置，从1开始算
	 */
	private int pagePos;
	/**
	 * 用户ID
	 */
	private long uid;
	
	private UserProfile userProfile;
	
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPagePos() {
		return pagePos;
	}
	public void setPagePos(int pagePos) {
		this.pagePos = pagePos;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	@Transient
	@JsonIgnore
	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
