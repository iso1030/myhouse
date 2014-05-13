package com.jerrylin.myhouse.web.house;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jerrylin.myhouse.entity.House;
import com.jerrylin.myhouse.entity.UserProfile;
import com.jerrylin.myhouse.service.house.HouseService;
import com.jerrylin.myhouse.service.user.UserService;

@Controller
public class VTourController {

	@Autowired
	private HouseService houseService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/vtour/{houseId}")
	public ModelAndView vtour(@PathVariable(value = "houseId") long houseId,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		if (houseId <= 0) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		House house = houseService.getHouse(houseId);
		if (house == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		modelMap.put("house", house);
		if (house.getUid() > 0) {
			UserProfile userProfile = userService.getUserProfile(house.getUid());
			if (userProfile != null) {
				modelMap.put("user", userProfile);
			}
		}
		return new ModelAndView("/tour/vtour");
	}

	@RequestMapping(value = "/vtour/slideshow/{houseId}")
	public ModelAndView slideshow(@PathVariable(value = "houseId") long houseId,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		if (houseId <= 0) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		House house = houseService.getHouse(houseId);
		if (house == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		modelMap.put("house", house);
		return new ModelAndView("/tour/vtour/slideshow");
	}

	@RequestMapping(value = "/vtour/panorama/{houseId}")
	public ModelAndView panorama(@PathVariable(value = "houseId") long houseId,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		if (houseId <= 0) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		House house = houseService.getHouse(houseId);
		if (house == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		modelMap.put("house", house);
		return new ModelAndView("/tour/vtour/panorama");
	}

	@RequestMapping(value = "/vtour/moreinfo/{houseId}")
	public ModelAndView moreinfo(@PathVariable(value = "houseId") long houseId,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		if (houseId <= 0) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		House house = houseService.getHouse(houseId);
		if (house == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		modelMap.put("house", house);
		return new ModelAndView("/tour/vtour/moreinfo");
	}

	@RequestMapping(value = "/vtour/videotour/{houseId}")
	public ModelAndView videotour(@PathVariable(value = "houseId") long houseId,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		if (houseId <= 0) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		House house = houseService.getHouse(houseId);
		if (house == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		modelMap.put("house", house);
		return new ModelAndView("/tour/vtour/videotour");
	}
	
	@RequestMapping(value = "/vtour/3d/{houseId}")
	public ModelAndView vtour3d(@PathVariable(value = "houseId") long houseId,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		if (houseId <= 0) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		House house = houseService.getHouse(houseId);
		if (house == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		modelMap.put("house", house);
		if (house.getUid() > 0) {
			UserProfile userProfile = userService.getUserProfile(house.getUid());
			if (userProfile != null) {
				modelMap.put("user", userProfile);
			}
		}
		return new ModelAndView("/tmpl/house/vtour3d");
	}
	
	@RequestMapping(value = "/vtour/info/{houseId}")
	public ModelAndView vtourInfo(@PathVariable(value = "houseId") long houseId,
			HttpServletRequest requet, HttpServletResponse response, ModelMap modelMap) {
		if (houseId <= 0) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		House house = houseService.getHouse(houseId);
		if (house == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		modelMap.put("house", house);
		if (house.getUid() > 0) {
			UserProfile userProfile = userService.getUserProfile(house.getUid());
			if (userProfile != null) {
				modelMap.put("user", userProfile);
			}
		}
		return new ModelAndView("/tmpl/house/vtourinfo");
	}
}
