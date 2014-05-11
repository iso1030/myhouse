package com.jerrylin.myhouse.web.user;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.utils.Identities;
import org.springside.modules.web.MediaTypes;

import com.jerrylin.myhouse.entity.House;
import com.jerrylin.myhouse.entity.User;
import com.jerrylin.myhouse.entity.UserAccount;
import com.jerrylin.myhouse.entity.UserProfile;
import com.jerrylin.myhouse.service.user.UserService;

@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	private Pattern phonePattern = Pattern.compile("^\\d+$", Pattern.CASE_INSENSITIVE);
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView page(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "10") int pageSize,
			@RequestParam(value = "query", defaultValue = "") String query, Model model,
			ServletRequest request) {
//		Page<UserProfile> users = userService.getUserProfilePage(pageNumber, 2);
//		model.addAttribute("page", users);
		
		pageNumber = Math.max(1, pageNumber);
		pageSize = Math.min(10, pageSize);
		
		Page<UserProfile> profiles = null;
		if (StringUtils.isNotBlank(query)) {
			Matcher matcher = phonePattern.matcher(query);
			if (matcher.matches())
				profiles = userService.getUserProfilePageByPhone(pageNumber, pageSize, query);
			else
				profiles = userService.getUserProfilePageByName(pageNumber, pageSize, query);
		} else {
			profiles = userService.getUserProfilePage(pageNumber, pageSize);
		}
		model.addAttribute("page", profiles);
		return new ModelAndView("/user/list");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "userId", defaultValue = "-1") long userId, Model model) {
		if (userId > 0) {
			UserProfile user = userService.getUserProfile(userId);
			model.addAttribute("user", user);
		}
		return new ModelAndView("/user/edit");
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public void delete(
			@RequestParam(value = "id", defaultValue = "0") long id,
			HttpServletRequest request, HttpServletResponse response) {
		if (id > 0) {
			userService.deleteUser(id);
		}
	}
	
	@RequestMapping(value = "/addorupdate", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public User create(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", defaultValue = "0") long userId,
			@RequestParam(value = "nickname", defaultValue = "") String nickname,
			@RequestParam(value = "telephone", defaultValue = "") String telephone,
			@RequestParam(value = "realname", defaultValue = "") String realname,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "avatar", defaultValue = "") String avatar,
			@RequestParam(value = "company", defaultValue = "") String company) {
		if (userId <= 0) {
			UserAccount account = new UserAccount();
			account.setType(UserAccount.NORMAL);
			account.setUsername(String.valueOf(Identities.randomLong()));
			
			UserProfile profile = new UserProfile();
			profile.setAvatar(avatar);
			profile.setEmail(email);
			profile.setNickname(nickname);
			profile.setRealname(realname);
			profile.setTelephone(telephone);
			profile.setCompany(company);
			
			userService.createUser(account, profile);
			return new User(profile);
		} else {
			UserProfile profile = new UserProfile();
			profile.setAvatar(avatar);
			profile.setEmail(email);
			profile.setId(userId);
			profile.setNickname(nickname);
			profile.setRealname(realname);
			profile.setTelephone(telephone);
			profile.setCompany(company);
			
			userService.updateUserProfile(profile);
			return new User(profile);
		}
	}
}
