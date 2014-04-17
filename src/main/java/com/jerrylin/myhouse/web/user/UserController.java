package com.jerrylin.myhouse.web.user;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jerrylin.myhouse.entity.User;
import com.jerrylin.myhouse.entity.UserAccount;
import com.jerrylin.myhouse.entity.UserProfile;
import com.jerrylin.myhouse.service.user.UserService;

@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView page(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "10") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Page<User> users = userService.getUserPage(pageNumber, pageSize);
		model.addAttribute("page", users);
		
		return new ModelAndView("/tmpl/user/list");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "userId", defaultValue = "-1") long userId, Model model) {
		if (userId > 0) {
			User user = userService.getUser(userId);
			model.addAttribute("user", user);
		}
		return new ModelAndView("/tmpl/user/edit");
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView addOrUpdate(
			@RequestParam(value = "id", defaultValue = "-1") long id,
			@RequestParam(value = "page.size", defaultValue = "10") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		
		UserAccount account = new UserAccount();
		UserProfile profile = new UserProfile();
		userService.createUser(account, profile);
		
		return new ModelAndView("/tmpl/user/edit");
	}
}
