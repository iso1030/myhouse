/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.jerrylin.myhouse.data;

import com.jerrylin.myhouse.entity.Admin;
import org.springside.modules.test.data.RandomData;

public class UserData {

	public static Admin randomNewUser() {
		Admin user = new Admin();
		user.setLoginName(RandomData.randomName("user"));
		user.setName(RandomData.randomName("User"));
		user.setPlainPassword(RandomData.randomName("password"));

		return user;
	}
}
