/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.jerrylin.myhouse.service.account;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.jerrylin.myhouse.entity.Admin;
import com.jerrylin.myhouse.repository.AdminDao;
import com.jerrylin.myhouse.service.ServiceException;
import com.jerrylin.myhouse.service.account.ShiroDbRealm.ShiroUser;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Encodes;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional
public class AdminService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(AdminService.class);

	private AdminDao adminDao;
	private Clock clock = Clock.DEFAULT;

	public List<Admin> getAllAdmin() {
		return (List<Admin>) adminDao.findAll();
	}

	public Admin getAdmin(Long id) {
		return adminDao.findOne(id);
	}

	public Admin findAdminByLoginName(String loginName) {
		return adminDao.findByLoginName(loginName);
	}

	public void addAdmin(Admin admin) {
		entryptPassword(admin);
		admin.setRoles("user");
		admin.setRegisterDate(clock.getCurrentDate());

		adminDao.save(admin);
	}

	public void updateAdmin(Admin admin) {
		if (StringUtils.isNotBlank(admin.getPlainPassword())) {
			entryptPassword(admin);
		}
		adminDao.save(admin);
	}

	public void deleteAdmin(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		adminDao.delete(id);
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(Admin admin) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		admin.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(admin.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		admin.setPassword(Encodes.encodeHex(hashPassword));
	}

	@Autowired
	public void setUserDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}
}
