package com.jerrylin.myhouse.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

	@RequestMapping(value = "/test",method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<UserAccount> listCount() {
		return userService.getUserAccountList();
	}
	/**
	 * 分页查询用户列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param telephone
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<User> list(
			@RequestParam(value = "page", defaultValue = "1", required = true) int pageNumber,
			@RequestParam(value = "size", defaultValue = "10", required = true) int pageSize,
			@RequestParam(value = "phone", defaultValue = "") String telephone,
			@RequestParam(value = "realname", defaultValue = "") String realname) {
		Page<User> profiles = this.page(pageNumber, pageSize, telephone, realname);
		
		return profiles == null ? new ArrayList<User>() : profiles.getContent();
	}
	/**
	 * 分页查询用户列表，返回分页信息
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param telephone
	 * @return
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Page<User> page(
			@RequestParam(value = "page", defaultValue = "1", required = true) int pageNumber,
			@RequestParam(value = "size", defaultValue = "10", required = true) int pageSize,
			@RequestParam(value = "phone", defaultValue = "") String telephone,
			@RequestParam(value = "realname", defaultValue = "") String realname) {
		pageNumber = Math.max(1, pageNumber);
		pageSize = Math.min(10, pageSize);
		
		Page<UserProfile> profiles = null;
		if (StringUtils.isNotBlank(telephone)) {
			profiles = userService.getUserProfilePageByPhone(pageNumber, pageSize, telephone);
		} else if (StringUtils.isNotBlank(realname)) {
			profiles = userService.getUserProfilePageByName(pageNumber, pageSize, realname);
		} else {
			profiles = userService.getUserProfilePage(pageNumber, pageSize);
		}
		if (profiles != null) {
			List<UserProfile> profileList = profiles.getContent();
			List<User> users = new ArrayList<User>();
			for (UserProfile each : profileList) {
				users.add(new User(each));
			}
			Page<User> page = new PageImpl<User>(users, new PageRequest(pageNumber - 1, pageSize), profiles.getTotalElements());
			return page;
		}
		return null;
	}
	/**
	 * 随便看看
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/random", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<User> random(HttpServletRequest request, HttpServletResponse response)  {
		List<User> users = new ArrayList<User>();
		
		long count = userService.getCount();
		if (count == 0)
			return users;
		
		if (count <= 5) {
			Page<UserProfile> profilePage = userService.getUserProfilePage(1, 5);
			List<UserProfile> profileList = profilePage.getContent();
			for (UserProfile each : profileList) {
				users.add(new User(each));
			}
			return users;
		}
		
		List<Long> list = new ArrayList<Long>();
		for (int i = 1; i <= count; ++i)
			list.add(new Long(i));
		Collections.shuffle(list);
		
		list = list.subList(0, 5);
		for (int i = 0, l = list.size(); i < l; ++i) {
			Page<UserProfile> profilePage = userService.getUserProfilePage(list.get(i).intValue(), 1);
			List<UserProfile> profileList = profilePage.getContent();
			for (UserProfile each : profileList) {
				users.add(new User(each));
			}
		}
		return users;
	}
	/**
	 * 查询用户具体信息
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/{uid}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public User getUserWithHouse(@PathVariable("uid") long userId) {
		UserProfile userProfile = userService.getUserProfile(userId);
		if (userProfile == null) {
			throw new RestException(HttpStatus.NOT_FOUND, "");
		}
		User user = new User(userProfile);
		Page<House> houses = houseService.getUserHouse(userId, 1, 1000);
		user.setHouses(houses.getContent());
		
		return user;
	}
	
//	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
//	public UserProfile update(HttpServletRequest request, HttpServletResponse response,
//			@RequestParam(value = "id", defaultValue = "0") long userId,
//			@RequestParam(value = "nickname", defaultValue = "") String nickname,
//			@RequestParam(value = "telephone", defaultValue = "") String telephone,
//			@RequestParam(value = "realname", defaultValue = "") String realname,
//			@RequestParam(value = "email", defaultValue = "") String email,
//			@RequestParam(value = "avatar", defaultValue = "") String avatar,
//			@RequestParam(value = "company", defaultValue = "") String company) {
//		UserProfile profile = new UserProfile();
//		profile.setAvatar(avatar);
//		profile.setEmail(email);
//		profile.setId(userId);
//		profile.setNickname(nickname);
//		profile.setRealname(realname);
//		profile.setTelephone(telephone);
//		profile.setCompany(company);
//		
//		userService.updateUserProfile(profile);
//		return profile;
//	}
//	
//	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
//	public User create(HttpServletRequest request, HttpServletResponse response,
//			@RequestParam(value = "id", defaultValue = "0") long userId,
//			@RequestParam(value = "nickname", defaultValue = "") String nickname,
//			@RequestParam(value = "telephone", defaultValue = "") String telephone,
//			@RequestParam(value = "realname", defaultValue = "") String realname,
//			@RequestParam(value = "email", defaultValue = "") String email,
//			@RequestParam(value = "avatar", defaultValue = "") String avatar,
//			@RequestParam(value = "company", defaultValue = "") String company) {
//		UserAccount account = new UserAccount();
//		account.setType(UserAccount.NORMAL);
//		account.setUsername(String.valueOf(Identities.randomLong()));
//		
//		UserProfile profile = new UserProfile();
//		profile.setAvatar(avatar);
//		profile.setEmail(email);
//		profile.setNickname(nickname);
//		profile.setRealname(realname);
//		profile.setTelephone(telephone);
//		profile.setCompany(company);
//		
//		userService.createUser(account, profile);
//		
//		return new User(profile);
//	}
	
}
