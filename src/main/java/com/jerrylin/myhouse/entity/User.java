package com.jerrylin.myhouse.entity;

import java.util.Date;
import java.util.List;

public class User extends UserProfile {

	private int type;
	private String username;
	private Date createTime;
	private List<House> houses;
	
	public User(UserProfile userProfile) {
		this.setAvatar(userProfile.getAvatar());
		this.setEmail(userProfile.getEmail());
		this.setId(userProfile.getId());
		this.setNickname(userProfile.getNickname());
		this.setRealname(userProfile.getRealname());
		this.setTelephone(userProfile.getTelephone());
		this.setCompany(userProfile.getCompany());
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<House> getHouses() {
		return houses;
	}

	public void setHouses(List<House> houses) {
		this.houses = houses;
	}
	
}
