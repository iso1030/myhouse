package com.jerrylin.myhouse.service.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Encodes;

import com.google.common.collect.Lists;
import com.jerrylin.myhouse.entity.UserAccount;
import com.jerrylin.myhouse.entity.UserProfile;
import com.jerrylin.myhouse.repository.UserAccountDao;
import com.jerrylin.myhouse.repository.UserProfileDao;
import com.jerrylin.myhouse.service.ServiceException;

@Component
@Transactional
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public static final String HASH_CHARSET = "utf-8";
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	@Autowired
	private UserProfileDao userProfileDao;

	@Autowired
	private UserAccountDao userAccountDao;
	
	private Clock clock = Clock.DEFAULT;
	
	@PersistenceContext
	private EntityManager em;


//	/**
//	 * 分页查询用户信息
//	 * 
//	 * @param pageNumber
//	 * @param pageSize
//	 * @return
//	 */
//	public Page<User> getUserPage(int pageNumber, int pageSize) {
//		Page<UserProfile> page = userProfileDao.findAll(new PageRequest(pageNumber - 1, pageSize));
//		
//		List<UserProfile> userProfiles = page.getContent();
//		List<Long> ids = new ArrayList<Long>();
//		for (UserProfile userProfile : userProfiles) {
//			ids.add(userProfile.getId());
//		}
//		// 不知道这里有没有针对批量查询做优化的，如果没有的话，其实可以直接在上面循环直接做掉
//		Iterable<UserAccount> userAccounts = userAccountDao.findAll(ids);
//		Map<Long, UserAccount> temp = new HashMap<Long, UserAccount>();
//		for (UserAccount userAccount : userAccounts) {
//			temp.put(userAccount.getId(), userAccount);
//		}
//
//		List<User> userList = new ArrayList<User>();
//		for (UserProfile userProfile : userProfiles) {
//			User user = new User(userProfile);
//			UserAccount account = temp.get(userProfile.getId());
//			if (account != null) {
////				user.setCreateTime(account.getCreateTime());
//				user.setType(account.getType());
//			}
//			userList.add(user);
//		}
//		Page<User> userPage = new PageImpl<User>(userList, new PageRequest(pageNumber - 1, pageSize), page.getTotalElements());
//		
//		return userPage;
//	}
	
	public void test() {
		List list = em.createNativeQuery("select id from mh_user_profile").getResultList();
		for (Object o : list) {
			System.out.println(o);
		}
	}

	public long getCount() {
		return userProfileDao.count();
	}
	
	public long getCountByFilter(final List<Long> filterIds) {
//		List<SearchFilter> filters = new ArrayList<SearchFilter>();
//		filters.add(new SearchFilter("telephone", Operator., phone));
		Specification<UserProfile> spec = new Specification<UserProfile>() {
			@Override
			public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = Lists.newArrayList();
				predicates.add(builder.not(root.get("id").in(filterIds)));
				// 将所有条件用 and 联合起来
				if (!predicates.isEmpty()) {
					return builder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return builder.conjunction();
			}
		};
		return userProfileDao.count(spec);
	}
	/**
	 * 查询单个用户基本信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserProfile getUserProfile(Long userId) {
		UserProfile userProfile = userProfileDao.findOne(userId);

		return userProfile;
	}
	/**
	 * 翻页获取用户Profile列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<UserProfile> getUserProfilePage(int pageNumber, int pageSize) {
		Page<UserProfile> page = userProfileDao.findAll(new PageRequest(pageNumber - 1, pageSize));
		return page;
	}
	public Page<UserProfile> getUserProfilePage(int pageNumber, int pageSize, final List<Long> filterIds) {
		Specification<UserProfile> spec = new Specification<UserProfile>() {
			@Override
			public Predicate toPredicate(Root<UserProfile> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = Lists.newArrayList();
				predicates.add(builder.not(root.get("id").in(filterIds)));
				// 将所有条件用 and 联合起来
				if (!predicates.isEmpty()) {
					return builder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return builder.conjunction();
			}
		};
		Page<UserProfile> page = userProfileDao.findAll(spec, new PageRequest(pageNumber - 1, pageSize));
		return page;
	}
	/**
	 * 翻页获取用户Profile列表，根据电话号码查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param phone
	 * @return
	 */
	public Page<UserProfile> getUserProfilePageByPhone(int pageNumber, int pageSize, String phone) {
		List<SearchFilter> filters = new ArrayList<SearchFilter>();
		filters.add(new SearchFilter("telephone", Operator.EQ, phone));
		Specification<UserProfile> spec = DynamicSpecifications.bySearchFilter(filters, UserProfile.class);
		
		Page<UserProfile> page = userProfileDao.findAll(spec, new PageRequest(pageNumber - 1, pageSize));
		
		return page;
	}
	/**
	 * 翻页获取用户Profile列表，根据realname查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param realname
	 * @return
	 */
	public Page<UserProfile> getUserProfilePageByName(int pageNumber, int pageSize, String realname) {
		List<SearchFilter> filters = new ArrayList<SearchFilter>();
		filters.add(new SearchFilter("realname", Operator.LIKE, realname));
		Specification<UserProfile> spec = DynamicSpecifications.bySearchFilter(filters, UserProfile.class);
		
		Page<UserProfile> page = userProfileDao.findAll(spec, new PageRequest(pageNumber - 1, pageSize));
		
		return page;
	}
	/**
	 * 查询单个用户Account信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserAccount getUserAccount(Long userId) {
		return userAccountDao.findOne(userId);
	}
	/**
	 * 查询用户Account列表
	 * 
	 * @return
	 */
	public List<UserAccount> getUserAccountList() {
		return (List<UserAccount>) userAccountDao.findAll();
	}
	public List<UserProfile> getUserProfileListByIds(List<Long> ids) {
		return (List<UserProfile>) userProfileDao.findAll(ids); 
	}
	public List<UserProfile> getUserProfileListByIds(Set<Long> ids) {
		return (List<UserProfile>) userProfileDao.findAll(ids);
	}
	/**
	 * 创建新用户
	 * 
	 * @param account
	 */
	public boolean createUser(UserAccount account, UserProfile profile) {
		if (account == null || profile == null)
			throw new ServiceException("无效的用户信息");
		
		if (StringUtils.isBlank(account.getPlainPassword())) {
			account.setPlainPassword(account.getUsername());
		}
		encryptPassword(account);

		userAccountDao.save(account);

		profile.setId(account.getId());
		profile.setCreateTime(clock.getCurrentTimeInMillis());
		userProfileDao.save(profile);
		
		return true;
	}

	/**
	 * 删除用户
	 * 
	 * @param userId
	 */
	public void deleteUser(Long userId) {
		userAccountDao.delete(userId);
		userProfileDao.delete(userId);
//		this.deleteUserDir(userId);
	}

	/**
	 * 更新用户Profile信息
	 * 
	 * @param profle
	 */
	public void updateUserProfile(UserProfile profile) {
		if (profile.getId() <= 0)
			return;
		UserProfile oldProfile = userProfileDao.findOne(profile.getId());
		if (oldProfile == null)
			return;
		oldProfile.setAvatar(profile.getAvatar());
		oldProfile.setCompany(profile.getCompany());
		oldProfile.setEmail(profile.getEmail());
		oldProfile.setNickname(profile.getNickname());
		oldProfile.setRealname(profile.getRealname());
		oldProfile.setTelephone(profile.getTelephone());
		userProfileDao.save(oldProfile);
	}
	/**
	 * 更新用户Account信息
	 * 
	 * @param account
	 */
	public void updateUserAccount(UserAccount account) {
		if (StringUtils.isNotBlank(account.getPlainPassword())) {
			encryptPassword(account);
		}
		userAccountDao.save(account);
	}

	/**
	 * 密码加密
	 * 
	 * @param account
	 */
	protected void encryptPassword(UserAccount account)  {
		try {
			byte[] salt = Digests.generateSalt(SALT_SIZE);
			account.setSalt(Encodes.encodeHex(salt));

			byte[] hashPassword = Digests.sha1(
					account.getPlainPassword().getBytes(HASH_CHARSET), salt,
					HASH_INTERATIONS);
			account.setPassword(Encodes.encodeHex(hashPassword));
		} catch (UnsupportedEncodingException e) {
			logger.warn("Unsupported Encode Exception for plain password {}", account.getPlainPassword());
			throw new ServiceException("密码加密错误");
		}
	}
	
}
