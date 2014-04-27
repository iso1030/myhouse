package com.jerrylin.myhouse.service.house;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jerrylin.myhouse.entity.Image;
import com.jerrylin.myhouse.repository.ImageDao;

@Component
@Transactional
public class ImageService {

	@Autowired
	private ImageDao imageDao;
	

	public Image getImage(Long id) {
		return imageDao.findOne(id);
	}
	
	public void addImages(List<Image> images) {
		imageDao.save(images);
	}
	
	public boolean saveImage(Image entity) {
		imageDao.save(entity);
		// TODO write to file system
		return true;
	}
	
	public boolean deleteImage(long id) {
		imageDao.delete(id);
		// TODO delete from file system
		return true;
	}
	
	public List<Image> getAllImage() {
		return (List<Image>) imageDao.findAll();
	}
	
	public List<Image> getHouseImage(Long houseId) {
		Page<Image> images = imageDao.findByHid(houseId, new PageRequest(0, 100, new Sort(Direction.DESC, "id")));
		return images.getContent();
	}
	
	public Page<Image> getHouseImage(Long houseId, int pageNumber, int pageSize) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
		return imageDao.findByHid(houseId, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
}
