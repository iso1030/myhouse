package com.jerrylin.myhouse.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;

import com.jerrylin.myhouse.entity.Image;
import com.jerrylin.myhouse.entity.UserAccount;
import com.jerrylin.myhouse.entity.UserProfile;
import com.jerrylin.myhouse.service.fs.FileService;
import com.jerrylin.myhouse.service.house.ImageService;

@RestController
@RequestMapping(value = "/api/v1/image")
public class ImageRestController {

	@Autowired
	private ImageService imageService;
	
	@Autowired
	private FileService fileService;
	
	@RequestMapping(value = "/{hid}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<Image> list(@PathVariable("hid") Long userId) {
		List<Image> result = imageService.getHouseImage(userId);
		
		return result;
	}
	
	@RequestMapping(value = "/create1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Image create1(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", defaultValue = "0") long imageId,
			@RequestParam(value = "type", defaultValue = "2") int type,
			@RequestParam(value = "hid", defaultValue = "0") long houseId,
			@RequestParam(value = "url", defaultValue = "") String url,
			@RequestParam(value = "thumbnail", defaultValue = "") String thumbnail) {
		Image image = new Image();
		image.setHid(houseId);
		image.setName("");
		image.setSort(0);
		image.setThumbnail(thumbnail);
		image.setUrl(url);
		image.setType(type);
		
		imageService.saveImage(image);
		return image;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long imageId) {
		Image image = imageService.getImage(imageId);
		if (image == null)
			return;
		imageService.deleteImage(imageId);
		fileService.deleteImage(image.getUrl(), image.getThumbnail());
	}
}
