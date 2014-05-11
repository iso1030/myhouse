package com.jerrylin.myhouse.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.jerrylin.myhouse.entity.RandomUser;
import com.jerrylin.myhouse.repository.RandomUserDao;

@Component
@Transactional
public class RandomUserService {

	@Autowired
	private RandomUserDao randomUserDao;
	
	public RandomUser addRandomUser(RandomUser user) {
		if (user == null)
			return user;
		randomUserDao.save(user);
		return user;
	}
	
	public void deleteRandomUser(long id) {
		if (id <= 0)
			return;
		randomUserDao.delete(id);
	}
	
	public Page<RandomUser> getRandomUserPage(int pageNumber, int pageSize) {
		Page<RandomUser> page = randomUserDao.findAll(new PageRequest(pageNumber - 1, pageSize));
		return page;
	}
	
	public List<RandomUser> getRandomUserByPageIndex(int pageIndex) {
		List<SearchFilter> filters = new ArrayList<SearchFilter>();
		filters.add(new SearchFilter("pageIndex", Operator.EQ, pageIndex));
		Specification<RandomUser> spec = DynamicSpecifications.bySearchFilter(filters, RandomUser.class);
		
		return randomUserDao.findAll(spec);
	}
}
