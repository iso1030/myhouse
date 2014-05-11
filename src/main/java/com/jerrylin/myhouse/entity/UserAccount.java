package com.jerrylin.myhouse.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="mh_user_account")
public class UserAccount extends IdEntity {

	public static final int NORMAL = 1;
	public static final int MOBILE = 2;
	public static final int EMAIL  = 3;
	public static final int WEIBO  = 4;
	
	/**
	 * 帐号类型，比如普通账号，手机号码，sns等
	 */
	private int type;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 加密密码
	 */
	private String password;
	/**
	 * 原始密码
	 */
	private String plainPassword;
	/**
	 * 密码盐巴
	 */
	private String salt;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@JsonIgnore
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Transient
	@JsonIgnore
	public String getPlainPassword() {
		return plainPassword;
	}
	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}
	@JsonIgnore
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
