package com.jerrylin.myhouse.web.user;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.MediaTypes;

import com.jerrylin.myhouse.entity.House;
import com.jerrylin.myhouse.entity.RandomUser;
import com.jerrylin.myhouse.entity.UserProfile;
import com.jerrylin.myhouse.service.user.RandomUserService;
import com.jerrylin.myhouse.service.user.UserService;

@Controller
@RequestMapping(value="/random")
public class RandomUserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RandomUserService randomUserService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView page(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "10") int pageSize,
			@RequestParam(value = "query", defaultValue = "") String query, Model model,
			ServletRequest request) {
		pageNumber = Math.max(1, pageNumber);
		pageSize = Math.min(10, pageSize);
		
		Page<RandomUser> page = null;
		if (!NumberUtils.isNumber(query)) {
			page = randomUserService.getRandomUserPage(pageNumber, pageSize);
		} else {
			List<RandomUser> list = randomUserService.getRandomUserByPageIndex(Integer.valueOf(query));
			if (list.size() > 0) 
				page = new PageImpl<RandomUser>(list, new PageRequest(1, list.size() - 1), list.size());
		}
		if (page != null && page.getContent() != null) {
			Set<Long> idSet = new HashSet<Long>();
			for (RandomUser randomUser : page.getContent()) {
				if (randomUser.getUid() > 0)
					idSet.add(randomUser.getUid());
			}
			if (idSet.size() > 0) {
				List<UserProfile> users = userService.getUserProfileListByIds(idSet);
				Map<Long, UserProfile> temp = new HashMap<Long, UserProfile>();
				for (UserProfile user : users) {
					temp.put(user.getId(), user);
				}
				
				for (RandomUser randomUser : page.getContent()) {
					if (temp.get(randomUser.getUid()) != null)
						randomUser.setUserProfile(temp.get(randomUser.getUid()));
				}
			}
		}
		model.addAttribute("page", page);
		return new ModelAndView("/user/random");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public RandomUser add(
			@RequestParam(value = "index", defaultValue = "1") int index,
			@RequestParam(value = "pos", defaultValue = "1") int pos,
			@RequestParam(value = "userId", defaultValue = "0") long userId, Model model,
			ServletRequest request) {
		if (userId <= 0)
			return null;
		UserProfile userProfile = userService.getUserProfile(userId);
		if (userProfile == null)
			return null;
		RandomUser randomUser = new RandomUser();
		randomUser.setPageIndex(index);
		randomUser.setPagePos(pos);
		randomUser.setUid(userProfile.getId());
		
		randomUserService.addRandomUser(randomUser);
		return randomUser;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public void delete(@RequestParam("id") long id) {
		randomUserService.deleteRandomUser(id);
	}
	
}
