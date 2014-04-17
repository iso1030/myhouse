package com.jerrylin.myhouse.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

import com.jerrylin.myhouse.entity.House;
import com.jerrylin.myhouse.entity.User;
import com.jerrylin.myhouse.entity.UserAccount;
import com.jerrylin.myhouse.entity.UserProfile;
import com.jerrylin.myhouse.service.house.HouseService;
import com.jerrylin.myhouse.service.user.UserService;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserRestController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private HouseService houseService;
	
	@Autowired
	private Validator validator;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<User> list(
			@RequestParam(value = "page", defaultValue = "1", required = true) int pageNumber, 
			@RequestParam(value = "size", defaultValue = "100", required = true) int pageSize) {
		if (pageNumber <= 0)
			pageNumber = 1;
		if (pageSize > 100)
			pageSize = 100;
		
		List<User> users = userService.getUserList(pageNumber, pageSize);
		return users;
	}
	
	@RequestMapping(value = "/{uid}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public User getUserWithHouse(@PathVariable("uid") Long userId) {
		User user = userService.getUser(userId);
		if (user == null) {
			throw new RestException(HttpStatus.NOT_FOUND, "");
		}
		
		Page<House> houses = houseService.getUserHouse(userId, 1, 1000);
		user.setHouses(houses.getContent());
		
		return user;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public UserProfile update(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", defaultValue = "0") long userId,
			@RequestParam(value = "nickname", defaultValue = "") String nickname,
			@RequestParam(value = "telephone", defaultValue = "") String telephone,
			@RequestParam(value = "realname", defaultValue = "") String realname,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "avatar", defaultValue = "") String avatar,
			@RequestParam(value = "company", defaultValue = "") String company) {
		UserProfile profile = new UserProfile();
		profile.setAvatar(avatar);
		profile.setEmail(email);
		profile.setId(userId);
		profile.setNickname(nickname);
		profile.setRealname(realname);
		profile.setTelephone(telephone);
		profile.setCompany(company);
		
		userService.updateUserProfile(profile);
		return profile;
	}
	
	@RequestMapping(value = "/create1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> create1(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", defaultValue = "0") long userId,
			@RequestParam(value = "nickname", defaultValue = "") String nickname,
			@RequestParam(value = "telephone", defaultValue = "") String telephone,
			@RequestParam(value = "realname", defaultValue = "") String realname,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "avatar", defaultValue = "") String avatar,
			@RequestParam(value = "company", defaultValue = "") String company) {
		UserAccount account = new UserAccount();
		account.setPlainPassword("1234");
		account.setType(UserAccount.NORMAL);
		account.setUsername(UUID.randomUUID().toString());
		
		UserProfile profile = new UserProfile();
		profile.setAvatar(avatar);
		profile.setEmail(email);
		profile.setNickname(nickname);
		profile.setRealname(realname);
		profile.setTelephone(telephone);
		profile.setCompany(company);
		
		userService.createUser(account, profile);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id", profile.getId());
		return result;
	}
	
	@RequestMapping(value = "/random", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<User> random(HttpServletRequest request, HttpServletResponse response)  {
		List<User> users = new ArrayList<User>();
		
		long count = userService.getCount();
		if (count == 0)
			return users;
		
		if (count <= 5) {
			return userService.getUserList(1, 5);
		}
		
		List<Long> list = new ArrayList<Long>();
		for (int i = 1; i <= count; ++i)
			list.add(new Long(i));
		Collections.shuffle(list);
		
		list = list.subList(0, 5);
		for (int i = 0, l = list.size(); i < l; ++i) {
			List<User> user = userService.getUserList(list.get(i).intValue(), 1);
			users.addAll(user);
		}
		return users;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public ResponseEntity<?> create(@RequestBody UserProfile profile, UriComponentsBuilder uriBuilder) {
		// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, profile);
		
		UUID uuid = UUID.randomUUID();
		UserAccount account = new UserAccount();
		account.setPlainPassword("test1234");
		account.setType(UserAccount.NORMAL);
		account.setUsername(uuid.toString());
		
		userService.createUser(account, profile);
		
		// 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		Long id = account.getId();
		URI uri = uriBuilder.path("/api/v1/user/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long userId) {
		userService.deleteUser(userId);
	}
}
