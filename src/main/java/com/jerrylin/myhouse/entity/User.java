package com.jerrylin.myhouse.entity;

import java.util.List;

public class User extends UserProfile {

	private List<House> houses;

	public User(UserProfile userProfile) {
		this.setAvatar(userProfile.getAvatar());
		this.setCompany(userProfile.getCompany());
		this.setCreateTime(userProfile.getCreateTime());
		this.setEmail(userProfile.getEmail());
		this.setId(userProfile.getId());
		this.setNickname(userProfile.getNickname());
		this.setRealname(userProfile.getRealname());
		this.setTelephone(userProfile.getTelephone());
	}

	public List<House> getHouses() {
		return houses;
	}

	public void setHouses(List<House> houses) {
		this.houses = houses;
	}
	
}
